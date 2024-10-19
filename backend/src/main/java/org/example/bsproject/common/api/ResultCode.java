package org.example.bsproject.common.api;

import lombok.Getter;

@Getter
public enum ResultCode {
    /** 固定枚举必须写在头部
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private Long code;
    private String message;

    /**
     * 枚举的构建函数应当私有来确保不出现额外的类型
     */
    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

//    public long getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
}
