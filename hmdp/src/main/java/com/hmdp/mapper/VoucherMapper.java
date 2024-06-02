package com.hmdp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hmdp.entity.Voucher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface VoucherMapper extends BaseMapper<Voucher> {
    /**
     * 根据商铺id查询优惠券
     * @param shopId
     * @return
     */
    List<Voucher> queryVoucherOfShop(@Param("shopId") Long shopId);
}
