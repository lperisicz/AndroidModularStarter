package com.perisic.luka.remote.data.helper;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class BaseResponse<T> extends BaseData{

    private T data;
    private String message;
    private String code;

    public BaseResponse(T data, String message, String code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public BaseResponse(Status status) {
        super(status);
    }

    public BaseResponse(Status status, String message) {
        super(status);
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