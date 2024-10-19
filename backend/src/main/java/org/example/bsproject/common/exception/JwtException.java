package org.example.bsproject.common.exception;

import lombok.Data;

@Data
public class JwtException extends  RuntimeException{
    private  Long errorCode;
    private  String message;

    public JwtException(Long code,String msg){
        this.errorCode=code; this.message=msg;
    }

    public JwtException(String msg){
        this.errorCode=(long)-1; this.message=msg;
    }
}