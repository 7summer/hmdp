package com.hmdp.constant;

public class RedisConstants {
    /**
     * 登录验证码缓存
     */
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 1L;
    /**
     * token令牌缓存
     */
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;

    /**
     * 空值
     */
    public static final Long CACHE_NULL_TTL = 2L;

    /**
     * 店铺类型列表缓存
     */
    public static final String CACHE_SHOP_TYPE_LIST_KEY = "cache:shop:type:list";
    public static final Long CACHE_SHOP_TYPE_List_TTL = 30L;

    /**
     * 店铺详情信息缓存
     */
    public static final String CACHE_SHOP_KEY = "cache:shop:";
    public static final Long CACHE_SHOP_TTL = 30L;

    /**
     * 秒杀券数据缓存
     */
    public static final String CACHE_SECKILL_KEY = "cache:seckill:";

    /**
     * 已购买优惠券的用户列表
     */
    public static final String BOUGH_SECKILL_USER_Set_KEY = "bough:seckill:user:list:";

    /**
     * 查询商店详情锁
     */
    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 1L;

    /**
     * 分布式锁
     */
    public static final String DISTRIBUTED_LOCK_KEY = "distributed:lock:";
    public static final Long DISTRIBUTED_LOCK_TTL = 1L;

    /**
     * 订单编号全局id生成key
     */
    public static final String INCR_VOUCHER_ORDER_ID_KEY = "incr:VoucherOrder:";

    /**
     * 创建订单的消息队列
     */
    public static final String VOUCHER_ORDER_MESSAGE_LIST_KEY = "voucher_order_message_list";

    /**
     * 已点赞笔记的用户列表
     */
    public static final String BLOG_LIKED_KEY = "blog:liked:";

    /**
     * 给用户投喂关注博主的笔记
     */
    public static final String FEED_KEY = "feed:";
    public static final Long FEED_TTL = 60L * 24 * 3;

    /**
     * 不同商铺类型下商铺的地理信息
     */
    public static final String SHOPTYPE_GEO_KEY = "shoptype:geo:";

    /**
     * 签到key
     */
    public static final String USER_SIGN_KEY = "sign:";
}
