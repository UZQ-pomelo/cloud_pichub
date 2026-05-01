package com.pichub.cloud_pichub.common;

import com.pichub.cloud_pichub.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 * @param <T> 数据类型泛型
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private T data;

    private String message;

    /**
     * 全参构造方法
     * @param code 状态码
     * @param data 响应数据
     * @param message 响应消息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造方法（无消息）
     * @param code 状态码
     * @param data 响应数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 使用错误码构造响应
     * @param errorCode 错误码枚举
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
