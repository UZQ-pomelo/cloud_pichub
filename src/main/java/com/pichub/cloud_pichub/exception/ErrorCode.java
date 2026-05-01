package com.pichub.cloud_pichub.exception;

import lombok.Getter;

/**
 * 错误码枚举类
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "操作成功"),
    SYSTEM_ERROR(50000, "系统内部错误"),
    OPERATION_ERROR(50001,"操作失败"),
    PARAMS_ERROR(40000, "参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "资源不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问");

    //状态码
    private final int code;
    //信息
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
