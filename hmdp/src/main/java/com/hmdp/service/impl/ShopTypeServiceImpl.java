package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.hmdp.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询类型列表
     * @return
     */
    @Override
    public List<ShopType> queryTypeList() {
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();
        String shopeTypeListKey = RedisConstants.CACHE_SHOP_TYPE_LIST_KEY;

        // 查询redis缓存
        List<String> shopTypeJsonList = operations.range(shopeTypeListKey, 0L, -1L);
        if (shopTypeJsonList != null && !shopTypeJsonList.isEmpty()) {
            List<ShopType> shopTypeList = shopTypeJsonList.stream().map(shopTypeJson -> {
                ShopType shopType = JSONUtil.toBean(shopTypeJson, ShopType.class);
                return shopType;
            }).collect(Collectors.toList());
            // 设置过期时间
            stringRedisTemplate.expire(shopeTypeListKey, RedisConstants.CACHE_SHOP_TYPE_List_TTL, TimeUnit.MINUTES);

            return shopTypeList;
        }

        // 从数据库中查询
        List<ShopType> typeList = this.query().orderByAsc("sort").list();
        if (typeList == null || typeList.isEmpty()) {
            return new ArrayList<>();
        }

        // 缓存到redis
        List<String> typeJsonList = typeList.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
        operations.rightPushAll(shopeTypeListKey, typeJsonList);
        stringRedisTemplate.expire(shopeTypeListKey, RedisConstants.CACHE_SHOP_TYPE_List_TTL, TimeUnit.MINUTES);

        return typeList;
    }
}
