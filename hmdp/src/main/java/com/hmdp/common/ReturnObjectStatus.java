package com.hmdp.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据状态
 */
@Data
public class ReturnObjectStatus implements Serializable {
    /**
     * 数据
     */
    private Object data;
    /**
     * 是否需要重建缓存
     */
    private Boolean status;
}
