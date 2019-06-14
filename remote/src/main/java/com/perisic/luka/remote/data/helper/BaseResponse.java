package com.perisic.luka.remote.data.helper;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class BaseResponse<T> {

    private T data;
    private String message;
    private String code;

    public BaseResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse(T data, String code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}