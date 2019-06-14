package com.perisic.luka.base.di.helper;

import javax.inject.Inject;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class TokenModelProvider {

    private String token;
    private String refreshToken;

    @Inject
    TokenModelProvider() {
        this.token = "";
        this.refreshToken = "";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}