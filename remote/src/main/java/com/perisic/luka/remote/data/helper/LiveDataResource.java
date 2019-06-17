package com.perisic.luka.remote.data.helper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * Created by Luka Perisic on 17.6.2019..
 */
public class LiveDataResource<T extends BaseResponse> extends MediatorLiveData<T> {

    private Observer<? super BaseData.Status> onStatusChanged;

    public LiveDataResource(Observer<? super BaseData.Status> onStatusChanged) {
        this.onStatusChanged = onStatusChanged;
    }

    public <S extends BaseResponse> void addLiveDataSource(@NonNull LiveData<S> source, @NonNull Observer<? super S> onChanged) {
        super.addSource(source, s -> {
            this.onStatusChanged.onChanged(s.getStatus());
            onChanged.onChanged(s);
        });
    }

}