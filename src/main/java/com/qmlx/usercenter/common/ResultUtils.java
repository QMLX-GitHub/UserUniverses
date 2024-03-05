package com.qmlx.usercenter.common;

public class ResultUtils {

    public static <T> BaseResponce<T> success(T data){
        return new BaseResponce<>(0,data,"ok");
    }

}
