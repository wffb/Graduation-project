package org.example.bsproject.common.exception;

import lombok.Data;

/**
 * 继承RuntimeException的自定义错误类
 */

@Data
public class WebSockertException extends  RuntimeException{
    private  Long errorCode;
    private  String message;

    public WebSockertException(Long code,String msg){
        this.errorCode=code; this.message=msg;
    }

    public WebSockertException(String msg){
        this.errorCode=(long)-1; this.message=msg;
    }
}
