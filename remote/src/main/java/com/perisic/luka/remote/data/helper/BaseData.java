package com.perisic.luka.remote.data.helper;

/**
 * Created by Luka Perisic on 17.6.2019..
 */
public class BaseData {

    private Status status = Status.LOADING;

    public BaseData() {
    }

    public BaseData(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        LOADING, DONE, ERROR
    }

}
