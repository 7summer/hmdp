package com.hmdp.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.constant.SystemConstants;
import com.hmdp.dto.Result;
import com.hmdp.dto.ShopDescDTO;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.hmdp.utils.CacheUtil;
import com.hmdp.constant.RedisConstants;
import com.hmdp.common.RedisData;
import com.hmdp.common.ReturnObjectStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ShopMapper shopMapper;

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 根据id查询店铺信息
     * @param id
     * @return
     */
    @Override
    public ShopDescDTO queryShopById(Long id) {
        // 解决缓存穿透
        ShopDescDTO shopDescDTO = cacheUtil.queryWithPassThrough(
                RedisConstants.CACHE_SHOP_KEY, id,
                ShopDescDTO.class, id2 -> {
                    Shop shop = shopMapper.selectById(id);
                    if (shop == null) {
                        return null;
                    }
                    ShopDescDTO dto = new ShopDescDTO();
                    BeanUtils.copyProperties(shop, dto);
                    return dto;
                }, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);

        return shopDescDTO;
    }

    /**
     * 解决缓存击穿（互斥锁）
     * @param id
     * @return
     */
    @Override
    public ShopDescDTO queryShopByIdWithMutex(Long id) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String shopKey = RedisConstants.CACHE_SHOP_KEY + id;

        // 查看redis数据库是否存在数据
        String shopJson = operations.get(shopKey);
        if (shopJson != null && shopJson.length() > 0) {
            // 如果有缓存，更新过期时间
            stringRedisTemplate.expire(shopKey, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
            return JSONUtil.toBean(shopJson, ShopDescDTO.class);
        }
        if (shopJson != null) {
            stringRedisTemplate.expire(shopKey, RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }

        ShopDescDTO shopDescDTO = null;
        try {
            // 获取互斥锁失败，重试等待
            if (!tryLock(RedisConstants.LOCK_SHOP_KEY + id)) {
                Thread.sleep(50);
                return queryShopByIdWithMutex(id);
            }

            // redis数据库没缓存，从mysql数据库中查询数据
            Shop shop = shopMapper.selectById(id);
            if (shop == null) {
                // 防止缓存穿透
                operations.set(shopKey, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            shopDescDTO = new ShopDescDTO();
            BeanUtils.copyProperties(shop, shopDescDTO);

            // 模拟延迟
            Thread.sleep(200);

            // 将shop数据缓存到redis
            // 设置过期时间
            String shopDescJson = JSONUtil.toJsonStr(shopDescDTO);
            operations.set(shopKey, shopDescJson, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放锁
            unLock(RedisConstants.LOCK_SHOP_KEY + id);
        }

        return shopDescDTO;
    }

    /**
     * 解决缓存击穿（互斥锁+逻辑删除）
     * @param id
     * @return
     */
    @Override
    public ShopDescDTO queryShopByIdWithLogicExpire(Long id) {
        ShopDescDTO shopDescDTO = cacheUtil.queryWithLogicalExpire(
                RedisConstants.CACHE_SHOP_KEY, id,
                ShopDescDTO.class, id2 -> {
                    Shop shop = shopMapper.selectById(id);
                    if (shop == null) {
                        return null;
                    }
                    ShopDescDTO dto = new ShopDescDTO();
                    BeanUtils.copyProperties(shop, dto);
                    return dto;
                }, RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);

        return shopDescDTO;
    }

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateShop(Shop shop) {
        // 更新店铺信息
        boolean result = this.updateById(shop);
        if (result) {
            String shopKey = RedisConstants.CACHE_SHOP_KEY + shop.getId();
            // 删除相应店铺信息的缓存
            stringRedisTemplate.delete(shopKey);
        }

        return result;
    }

    /**
     * 根据商铺类型分页查询商铺信息
     * @param typeId 商铺类型
     * @param current 页码
     * @param x 坐标
     * @param y 坐标
     * @return 商铺列表
     */
    @Override
    public Result queryShopByType(Long typeId, Integer current, Double x, Double y) {
        // 如果x或y为空，直接返回商铺列表
        if (x == null || y == null) {
            // 根据类型分页查询
            Page<Shop> page = this.query()
                    .eq("type_id", typeId)
                    .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
            // 返回数据
            return Result.ok(page.getRecords());
        }

        GeoOperations<String, String> operations = stringRedisTemplate.opsForGeo();
        String key = RedisConstants.SHOPTYPE_GEO_KEY + typeId;

        Integer start = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        Integer end = current * SystemConstants.DEFAULT_PAGE_SIZE;
        // 根据GEOSEARCH商铺id
        GeoResults<RedisGeoCommands.GeoLocation<String>> searchResult = operations.
                search(key, GeoReference.fromCoordinate(new Point(x, y)),
                        // 方圆5公里内的商铺
                        new Distance(5000),
                        // 返回结果带上距离
                        // 限制返回结果的个数[0 - end]
                        RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().limit(end).includeDistance());
        if (searchResult == null) {
            return Result.ok(Collections.EMPTY_LIST);
        }

        // 根据current限制返回结果的个数[start - end]
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> searchResultList = searchResult.getContent()
                .stream().skip(start).collect(Collectors.toList());
        if (searchResultList == null || searchResultList.isEmpty()) {
            return Result.ok(Collections.EMPTY_LIST);
        }

        // 存储 商铺id->距离
        Map<Long, Distance> idDistanceMap = new HashMap<>();
        // 按距离远近存储商铺id
        List<Long> idList = new ArrayList<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> geoLocationGeoResult : searchResultList) {
            // 将返回结果映射成商铺id
            String idStr = geoLocationGeoResult.getContent().getName();
            Long id = Long.valueOf(idStr);
            // 距离
            Distance distance = geoLocationGeoResult.getDistance();

            idList.add(id);
            idDistanceMap.put(id, distance);
        }

        // 根据前面的返回结果，利用商铺id查询
        String idSetStr = StringUtils.join(idList, ",");
        List<Shop> shopList = this.query()
                .in("id", idList)
                .last("ORDER BY FIELD(id," + idSetStr + ")").list();
        // 每个shop对象需要带上distance
        shopList.forEach(shop -> {
            Long shopId = shop.getId();
            Distance distance = idDistanceMap.get(shopId);
            shop.setDistance(distance.getValue());
        });

        return Result.ok(shopList);
    }

    /**
     * 获取锁
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", RedisConstants.LOCK_SHOP_TTL, TimeUnit.MINUTES);
        return result != null && result;
    }

    /**
     * 释放锁
     * @param key
     */
    private void unLock(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 将编号为id的商品重建到redi（逻辑删除）
     * @param operations
     * @param id
     */
    private void rebuiltShopDescDTO(ValueOperations<String, String> operations, Long id) throws InterruptedException {
        Thread.sleep(1000);

        String shopKey = RedisConstants.CACHE_SHOP_KEY + id;

        Shop shop = shopMapper.selectById(id);
        if (shop == null) {
            // 防止缓存穿透
            operations.set(shopKey, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
        }

        ShopDescDTO shopDescDTO = new ShopDescDTO();
        BeanUtils.copyProperties(shop, shopDescDTO);

        RedisData redisData = new RedisData();
        redisData.setData(shopDescDTO);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(20));

        String redisDataJson = JSONUtil.toJsonStr(redisData);
        operations.set(shopKey, redisDataJson);
    }

    /**
     * 通过key从redis数据库中获取数据
     * @param operations
     * @param shopKey
     * @return
     */
    private ReturnObjectStatus getShopdescdtoByKey(ValueOperations<String, String> operations, String shopKey) {
        ReturnObjectStatus returnObjectStatus = new ReturnObjectStatus();

        // 查看redis数据库是否存在数据
        String redisDataJson = operations.get(shopKey);
        if (redisDataJson == null || redisDataJson.length() == 0) {
            returnObjectStatus.setData(null);
            returnObjectStatus.setStatus(true);

            return returnObjectStatus;
        }

        // 检查数据的过期时间
        RedisData redisData = JSONUtil.toBean(redisDataJson, RedisData.class);
        LocalDateTime localDateTime = redisData.getExpireTime();
        JSONObject data = (JSONObject) redisData.getData();
        ShopDescDTO shopDescDTO = JSONUtil.toBean(data, ShopDescDTO.class);

        // 时间未过期，直接返回
        // 时间过期，不直接返回
        returnObjectStatus.setData(shopDescDTO);
        returnObjectStatus.setStatus(localDateTime.isAfter(LocalDateTime.now()));

        return returnObjectStatus;
    }
}
