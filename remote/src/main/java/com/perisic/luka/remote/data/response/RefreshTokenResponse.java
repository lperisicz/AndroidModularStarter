package com.perisic.luka.remote.data.response;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class RefreshTokenResponse {

    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}