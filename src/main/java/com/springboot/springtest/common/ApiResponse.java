package com.springboot.springtest.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * api返回数据封装类
 * Created by wcj on 16/12/7.
 */
@Data
@NoArgsConstructor
public class ApiResponse implements Serializable {
    //状态
    private String status;
    //返回码
    private int code;
    //返回消息
    private String message;
    //返回数据
    private Object data;

    private ApiResponse(String status, int code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static ApiResponse success() {
        return new ApiResponse("success", 0, "", "");
    }

    public static ApiResponse success(String msg, Object data) {
        return new ApiResponse("success", 0, msg, data);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse("success", 0, "", data);
    }

    public static ApiResponse error() {
        return new ApiResponse("error", 1, "", "");
    }

    public static ApiResponse error(String msg) {
        return new ApiResponse("error", 1, msg, "");
    }

    public static ApiResponse error(String msg, Object data) {
        return new ApiResponse("error", 1, msg, data);
    }

    public static ApiResponse error(int code, String msg) {
        return new ApiResponse("error", code, msg, "");
    }

    public static ApiResponse exception() {
        return new ApiResponse("exception", 2, "系统异常", "");
    }

    public static ApiResponse exception(String eMessage) {
        return new ApiResponse("exception", 2, eMessage, "");
    }
}
