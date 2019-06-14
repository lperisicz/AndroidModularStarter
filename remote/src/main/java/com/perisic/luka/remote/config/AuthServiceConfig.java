package com.perisic.luka.remote.config;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public interface AuthServiceConfig {

    String ROUTE_AUTH = "auth";
    String ROUTE_LOGIN = ROUTE_AUTH + "/login";
    String ROUTE_REGISTER = ROUTE_AUTH + "/register";
    String ROUTE_CHECK_EMAIL = ROUTE_AUTH + "/checkEmail";
    String ROUTE_REFRESH_TOKEN = ROUTE_AUTH + "/refreshToken";

}