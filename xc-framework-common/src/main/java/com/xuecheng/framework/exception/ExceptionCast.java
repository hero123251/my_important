package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 这个类是封装的是：在controller、service、dao层内抛出的异常对象
 *
 */
public class ExceptionCast {
    public static CustomException cast(ResultCode resultCode){
        return new CustomException(resultCode);
    }
}
