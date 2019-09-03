package com.perisic.luka.remote.services;

import com.perisic.luka.remote.config.Config;
import com.perisic.luka.remote.data.helper.BaseResponse;
import com.perisic.luka.remote.data.request.LoginRequest;
import com.perisic.luka.remote.data.request.RefreshTokenRequest;
import com.perisic.luka.remote.data.request.RegisterRequest;
import com.perisic.luka.remote.data.response.LoginResponse;
import com.perisic.luka.remote.data.response.RefreshTokenResponse;
import com.perisic.luka.remote.data.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.perisic.luka.remote.config.AuthServiceConfig.ROUTE_LOGIN;
import static com.perisic.luka.remote.config.AuthServiceConfig.ROUTE_REFRESH_TOKEN;
import static com.perisic.luka.remote.config.AuthServiceConfig.ROUTE_REGISTER;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public interface AuthService {

    @Headers(Config.NO_JWT_AUTH_HEADER)
    @POST(ROUTE_REFRESH_TOKEN)
    Call<BaseResponse<RefreshTokenResponse>> refreshToken(@Body RefreshTokenRequest refreshTokenRequest);

    @Headers(Config.NO_JWT_AUTH_HEADER)
    @POST(ROUTE_LOGIN)
    Call<BaseResponse<LoginResponse>> login(@Body LoginRequest request);

    @Headers(Config.NO_JWT_AUTH_HEADER)
    @POST(ROUTE_REGISTER)
    Call<BaseResponse<RegisterResponse>> register(@Body RegisterRequest request);
//
//    @Headers(Config.NO_JWT_AUTH_HEADER)
//    @POST(ROUTE_CHECK_EMAIL)
//    Call<BaseResponse<CheckEmailResponse>> checkEmail(@Body CheckEmailRequest request);

}