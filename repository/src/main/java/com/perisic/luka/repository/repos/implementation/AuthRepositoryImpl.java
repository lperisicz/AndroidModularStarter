package com.perisic.luka.repository.repos.implementation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.LoginRequest;
import com.perisic.luka.remote.data.response.LoginResponse;
import com.perisic.luka.remote.services.AuthService;
import com.perisic.luka.repository.repos.abstraction.AuthRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class AuthRepositoryImpl implements AuthRepository {

    private AuthService authService;

    @Inject
    public AuthRepositoryImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public LiveData<BaseResponse<LoginResponse>> login(LoginRequest loginRequest) {
        return executeCall(authService.login(loginRequest));
    }

    private static <T> LiveData<BaseResponse<T>> executeCall(Call<BaseResponse<T>> call) {
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

}