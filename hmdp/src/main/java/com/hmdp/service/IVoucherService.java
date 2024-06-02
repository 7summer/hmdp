package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.entity.Voucher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IVoucherService extends IService<Voucher> {
    /**
     * 根据商铺id查询优惠券
     * @param shopId
     * @return
     */
    Result queryVoucherOfShop(Long shopId);
    /**
     * 添加秒杀券
     * @param voucher
     */
    void addSeckillVoucher(Voucher voucher);
}
