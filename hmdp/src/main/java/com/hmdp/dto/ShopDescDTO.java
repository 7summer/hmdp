package com.hmdp.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopDescDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 商铺名称
     */
    private String name;

    /**
     * 商铺类型的id
     */
    private Long typeId;

    /**
     * 商铺图片，多个图片以','隔开
     */
    private String images;

    /**
     * 商圈，例如陆家嘴
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private Double x;

    /**
     * 维度
     */
    private Double y;

    /**
     * 均价，取整数
     */
    private Long avgPrice;

    /**
     * 销量
     */
    private Integer sold;

    /**
     * 评论数量
     */
    private Integer comments;

    /**
     * 评分，1~5分，乘10保存，避免小数
     */
    private Integer score;

    /**
     * 营业时间，例如 10:00-22:00
     */
    private String openHours;
}
