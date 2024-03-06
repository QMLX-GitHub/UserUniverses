package com.qmlx.usercenter.common;

public class ResultUtils {

    public static <T> BaseResponce<T> success(T data){
        return new BaseResponce<>(0,data,"ok");
    }

    public static <T> BaseResponce<T> error(T data){
        return new BaseResponce<>(1,data,"error");
    }
    public static <T> BaseResponce<T> error(T data,String message){
        return new BaseResponce<>(1,data,"error",message);
    }

}
