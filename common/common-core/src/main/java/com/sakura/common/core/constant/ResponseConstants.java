package com.sakura.common.core.constant;

import lombok.Getter;

/**
 * @author Mr.Zhou
 */
public enum ResponseConstants {
    /**
     * SUCCESS
     */
    SUCCESS(200,"请求发送成功"),
    /**
     * FAIL
     */
    FAIL(400,"客户端错误"),
    /**
     * INTERNAL_SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(500,"服务端错误"),
    /**
     * NOT_FOUND
     */
    NOT_FOUND(404,"网页未找到"),
    /**
     * token无效，401
     * UNAUTHORIZED
     */
    UNAUTHORIZED(401,"身份认证错误"),
    /**
     * token有效，但是没有权限
     */
    FORBIDDEN(403,"没有权限");

    ResponseConstants(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    @Getter
    private Integer code;

    @Getter
    private String msg;
}
