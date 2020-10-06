package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 异常统一捕获类
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    //记录日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义map，配置异常类型所对应的错误代码
    //ImmutableMap：谷歌提供的一个工具类里的一个类，不是JDK提供的
    //     他是不可修改的，
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPITIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    //捕获指定的 customException 类型 异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {
        //记录日志
        LOGGER.error("catch exception:{}", customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    //捕获不可知异常类型 异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        //打印异常的堆栈信息
        exception.printStackTrace();
        //记录日志
        LOGGER.error("catch exception:{}", exception.getMessage());
        if (EXCEPITIONS == null) {
            EXCEPITIONS = builder.build();//EXCEPTIONS构建成功
        }
        //从EXCEPTION中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPITIONS.get(exception.getClass());

        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            //返回99999错误代码
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }

    }

    static {
        //定义了异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

}