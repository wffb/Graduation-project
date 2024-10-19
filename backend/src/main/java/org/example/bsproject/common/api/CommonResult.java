package org.example.bsproject.common.api;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult <T> implements Serializable {

    public interface CommonResultView{};

    //情况代码
    @JsonView(CommonResultView.class)
    private  long code;

    //情况消息
    @JsonView(CommonResultView.class)
    private  String message;

    //具体数据
    @JsonView(CommonResultView.class)
    private T data;

    private  CommonResult(long code, String message, T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

    /**
     * 成功返回
     * @param message
     * @param data
     */
    public  static <T>  CommonResult<T> success(String message,T data){
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), message,data);
    }

    public  static <T>  CommonResult<T> success(T data){
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, null);
    }
    /**
     * 失败返回结果
     * @param data
     */
    public static <T> CommonResult<T> failed(T data,String msg) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(),msg,data);
    }


    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(ResultCode code) {
        return new CommonResult<T>(code.getCode(), code.getMessage(), null);
    }
    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
