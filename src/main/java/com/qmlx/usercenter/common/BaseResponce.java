package com.qmlx.usercenter.common;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseResponce <T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponce(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponce(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

}
