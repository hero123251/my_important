package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 自定义异常类
 *      为什么要继承RuntimeException而不是Exception?
 *          因为继承Exception是编译异常，在出现异常的方法内抛出自定义异常，还是会出现编译异常
 *       还得往外抛。
 *          所以选择继承RuntimeException。
 *
 */
public class CustomException extends RuntimeException {

    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
