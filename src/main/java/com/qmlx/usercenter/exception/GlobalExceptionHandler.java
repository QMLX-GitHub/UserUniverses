package com.qmlx.usercenter.exception;

import com.qmlx.usercenter.common.BaseResponce;
import com.qmlx.usercenter.common.ErrorCode;
import com.qmlx.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 自定义全局异常捕获器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponce businessException(BusinessException e){
        log.error("businessException:"+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponce runtimeException(RuntimeException e){
        log.info("runtimeexception:"+e.getMessage(),e);
        return ResultUtils.error(ErrorCode.STSTEM_ERROR,e.getMessage());
    }

}
