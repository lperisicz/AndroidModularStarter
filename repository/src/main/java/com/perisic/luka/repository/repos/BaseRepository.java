package com.perisic.luka.repository.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.perisic.luka.remote.data.helper.BaseData;
import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.helper.NetworkLiveDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luka Perisic on 17.6.2019..
 */
public interface BaseRepository {
    //TODO
    default <T> LiveData<BaseResponse<T>> executeCall(Call<BaseResponse<T>> call) {
        MutableLiveData<BaseResponse<T>> apiResponse = new MutableLiveData<>();
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
                if (response.body() != null) {
                    apiResponse.setValue(response.body());
                } else if (response.errorBody() != null) {
                    apiResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<T>> call, @NonNull Throwable t) {

            }
        });
        return apiResponse;
    }

    default <T> LiveData<BaseResponse<T>> executeCall(Call<BaseResponse<T>> call, @NonNull Observer<T> callback) {
        NetworkLiveDataSource<T> apiResponse = new NetworkLiveDataSource<>();
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
                if (response.body() != null) {
                    if (response.body().getData() != null) {
                        callback.onChanged(response.body().getData());
                    }
                    apiResponse.setValue(response.body(), BaseData.Status.DONE);
                } else if (response.errorBody() != null) {
                    apiResponse.setValue(response.body(), BaseData.Status.ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<T>> call, @NonNull Throwable t) {
                apiResponse.setValue(new BaseResponse<>(BaseData.Status.ERROR));
            }
        });
        return apiResponse.startWithLoading();
    }

}