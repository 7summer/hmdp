package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.dto.ShopDescDTO;
import com.hmdp.entity.Shop;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IShopService extends IService<Shop> {
    /**
     * 根据id查询店铺信息
     * @param id
     * @return
     */
    ShopDescDTO queryShopById(Long id);

    /**
     * 解决缓存击穿（互斥锁）
     * @param id
     * @return
     */
    ShopDescDTO queryShopByIdWithMutex(Long id);

    /**
     * 解决缓存击穿（互斥锁+逻辑删除）
     * @param id
     * @return
     */
    ShopDescDTO queryShopByIdWithLogicExpire(Long id);

    /**
     * 更新商铺信息
     * @param shop 商铺数据
     * @return 无
     */
    boolean updateShop(Shop shop);

    /**
     * 根据商铺类型分页查询商铺信息
     * @param typeId 商铺类型
     * @param current 页码
     * @param x 坐标
     * @param y 坐标
     * @return 商铺列表
     */
    Result queryShopByType(Long typeId, Integer current, Double x, Double y);
}
