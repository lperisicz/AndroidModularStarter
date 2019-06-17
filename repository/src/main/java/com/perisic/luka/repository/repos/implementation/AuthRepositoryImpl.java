package com.perisic.luka.repository.repos.implementation;

import androidx.lifecycle.LiveData;

import com.perisic.luka.base.di.helper.OnTokenChangeListener;
import com.perisic.luka.base.di.helper.TokenModelProvider;
import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.LoginRequest;
import com.perisic.luka.remote.data.response.LoginResponse;
import com.perisic.luka.remote.services.AuthService;
import com.perisic.luka.repository.repos.abstraction.AuthRepository;

import javax.inject.Inject;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class AuthRepositoryImpl implements AuthRepository {

    private AuthService authService;
    private OnTokenChangeListener onTokenChangeListener;
    private TokenModelProvider tokenModelProvider;

    @Inject
    AuthRepositoryImpl(AuthService authService, OnTokenChangeListener onTokenChangeListener, TokenModelProvider tokenModelProvider) {
        this.authService = authService;
        this.onTokenChangeListener = onTokenChangeListener;
        this.tokenModelProvider = tokenModelProvider;
    }

    @Override
    public LiveData<BaseResponse<LoginResponse>> login(LoginRequest loginRequest) {
        return executeCall(
                authService.login(loginRequest),
                loginResponse -> {
                    onTokenChangeListener.onTokenChange(loginResponse.getToken(), loginResponse.getRefreshToken());
                    tokenModelProvider.setToken(loginResponse.getToken());
                    tokenModelProvider.setRefreshToken(loginResponse.getRefreshToken());
                }
        );
    }

}