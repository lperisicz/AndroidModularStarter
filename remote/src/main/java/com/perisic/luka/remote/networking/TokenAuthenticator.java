package com.perisic.luka.remote.networking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.perisic.luka.base.di.helper.OnTokenChangeListener;
import com.perisic.luka.base.di.helper.TokenModelProvider;
import com.perisic.luka.remote.config.Config;
import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.RefreshTokenRequest;
import com.perisic.luka.remote.data.response.RefreshTokenResponse;
import com.perisic.luka.remote.services.AuthService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Singleton
public class TokenAuthenticator implements Authenticator {

    private static final int MAX_NUM_OF_RETRIES = 3;
    private final AuthService authService;
    private final TokenModelProvider tokenModelProvider;
    private final OnTokenChangeListener onTokenChangeListener;
    private int retryCount = 0;

    @Inject
    TokenAuthenticator(AuthService authService, TokenModelProvider tokenModelProvider, OnTokenChangeListener onTokenChangeListener) {
        this.authService = authService;
        this.tokenModelProvider = tokenModelProvider;
        this.onTokenChangeListener = onTokenChangeListener;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        if (response.code() == 401 && retryCount < MAX_NUM_OF_RETRIES) {
            synchronized (tokenModelProvider) {
                retryCount++;
                BaseResponse<RefreshTokenResponse> refreshTokenResponse = authService.refreshToken(
                        new RefreshTokenRequest(
                                tokenModelProvider.getRefreshToken()
                        )
                ).execute().body();
                if (refreshTokenResponse != null && refreshTokenResponse.getData() != null) {
                    retryCount = 0;
                    tokenModelProvider.setToken(refreshTokenResponse.getData().getToken());
                    tokenModelProvider.setRefreshToken(refreshTokenResponse.getData().getRefreshToken());
                    onTokenChangeListener.onTokenChange(refreshTokenResponse.getData().getToken(), refreshTokenResponse.getData().getRefreshToken());
                    return response.request().newBuilder()
                            .header(Config.AUTHORIZATION, Config.AUTH_BEARER + refreshTokenResponse.getData().getToken()).build();
                }
            }
        }
        throw new IOException(Config.SESSION_TOKEN_EXPIRED);
    }
}