package com.perisic.luka.repository.repos;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.perisic.luka.remote.data.helper.BaseResponse;

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

    default <T> LiveData<BaseResponse<T>> executeCall(Call<BaseResponse<T>> call, @NonNull ResponseCallback<T> callback) {
        MutableLiveData<BaseResponse<T>> apiResponse = new MutableLiveData<>();
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
                if (response.body() != null) {
                    if (response.body().getData() != null) {
                        callback.onBodyResponse(response.body().getData());
                    }
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

    interface ResponseCallback<T> {

        void onBodyResponse(@NonNull T response);

    }

}