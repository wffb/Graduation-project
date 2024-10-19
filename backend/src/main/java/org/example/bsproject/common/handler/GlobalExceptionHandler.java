package org.example.bsproject.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.bsproject.common.exception.JwtException;
import org.example.bsproject.common.exception.WebSockertException;
import org.example.bsproject.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//该类接受控制器全局错误
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private  static  final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //接受对应类型报错
    @ExceptionHandler(WebSockertException.class)
    @ResponseBody
    public CommonResult<Object> webSockertExceptionHandler(WebSockertException e){

        System.out.println("错误捕获");

        String msg= "[websocket消息]:"+e.getMessage();
        logger.error(msg);


        return  CommonResult.failed(msg,null);
    }

    //通用错误配置
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResult<Object> RuntimeExceptionHandler(RuntimeException e) throws RuntimeException{

        System.out.println("错误捕获");

        logger.error(e.getMessage());


        return CommonResult.failed(e.getMessage(), null);

    }
}
