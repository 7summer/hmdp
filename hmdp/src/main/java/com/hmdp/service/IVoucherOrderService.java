package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.entity.VoucherOrder;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IVoucherOrderService extends IService<VoucherOrder> {
    /**
     * 秒杀下单
     * @param voucherId
     * @return
     */
    Result seckillVoucher(Long voucherId);
    /**
     * 创建订单
     * @param voucherOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    boolean createVoucherOrder(VoucherOrder voucherOrder);
}
