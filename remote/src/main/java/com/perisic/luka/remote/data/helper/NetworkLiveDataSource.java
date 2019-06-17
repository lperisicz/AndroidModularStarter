package com.perisic.luka.remote.data.helper;

import androidx.lifecycle.LiveData;

/**
 * Created by Luka Perisic on 17.6.2019..
 */
public class NetworkLiveDataSource<T> extends LiveData<BaseResponse<T>> {

    @Override
    public void setValue(BaseResponse<T> value) {
        super.setValue(value);
    }

    public void setValue(BaseResponse<T> value, BaseData.Status status) {
        value.setStatus(status);
        super.setValue(value);
    }

    public NetworkLiveDataSource<T> startWithLoading() {
        super.setValue(new BaseResponse<>(BaseData.Status.LOADING));
        return this;
    }
}