package com.springboot.springtest.common;

/**
 * dubbo自定义异常，需要继承RuntimeException，可查看dubbo源码他对运行时异常直接抛出
 * 接口和实现都要抛这个异常
 */
public class BusinessException extends RuntimeException {
    private int errorCode;

    public BusinessException() {
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
