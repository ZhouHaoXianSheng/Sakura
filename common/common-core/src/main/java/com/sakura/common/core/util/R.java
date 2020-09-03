package com.sakura.common.core.util;

import com.sakura.common.core.constant.ResponseConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Mr.Zhou
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
//使用该方法则以下的setter方法返回值的是this而不是void
@Accessors(chain = true)
public class R<T> implements Serializable {

    //设置为1L,表示该类的版本，每修改一次需要修改该版本的值
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    @Getter
    @Setter
    private int code;

    /**
     * 信息
     */
    @Getter
    @Setter
    private String msg;

    /**
     * 数据
     */
    @Getter
    @Setter
    private T data;

    /**
     * 下面的方法是用于构造简单的统一返回结果：
     * 由于最常用的请求结果是成功和失败，所以下面列举了最简单的请求构造方法
     * 如果是其他错误（比如4xx），使用全参构造器
     */

    //泛型方法格式  泛型方法声明<T>   返回类型<T>  方法名
    public static <T> R<T> ok() {
        //返回方法可以使用HttpStatus.OK  此处用CommonConstants是为了练习enum
        return restResult(null, ResponseConstants.SUCCESS.getCode(), null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, ResponseConstants.SUCCESS.getCode(), null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, ResponseConstants.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, ResponseConstants.INTERNAL_SERVER_ERROR.getCode(), null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ResponseConstants.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, ResponseConstants.INTERNAL_SERVER_ERROR.getCode(), null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, ResponseConstants.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
