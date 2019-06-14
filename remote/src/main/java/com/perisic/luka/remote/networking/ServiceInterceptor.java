package com.perisic.luka.remote.networking;

import androidx.annotation.NonNull;

import com.perisic.luka.base.di.helper.TokenModelProvider;
import com.perisic.luka.remote.config.Config;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Singleton
public class ServiceInterceptor implements Interceptor {

    private TokenModelProvider tokenModelProvider;

    @Inject
    ServiceInterceptor(TokenModelProvider tokenModelProvider) {
        this.tokenModelProvider = tokenModelProvider;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        if (request.header(Config.NO_JWT_AUTH) == null) {
            if (tokenModelProvider == null) {
                throw new RuntimeException(Config.NO_SESSION_TOKEN);
            } else {
                requestBuilder.addHeader(Config.AUTHORIZATION, Config.AUTH_BEARER + tokenModelProvider.getToken());
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}