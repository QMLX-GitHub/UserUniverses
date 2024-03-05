package com.qmlx.usercenter.common;

/**
 * 自定义错误码,注意枚举的每一个变量后面呢用都好隔开，并且，不支持setter方法
 */

public enum ErrorCode {
    SUCCESS(0,"OK",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN (40100,"没有登录",""),
    NOTE_AUTH(40101,"无权限",""),
    STSTEM_ERROR(50000,"系统内部异常","");


    private final int code;
    private final String message;//描述错误信息
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
