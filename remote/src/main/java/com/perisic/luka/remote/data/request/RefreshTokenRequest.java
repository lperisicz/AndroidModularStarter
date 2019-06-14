package com.perisic.luka.remote.data.request;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class RefreshTokenRequest {

    private String refreshToken;

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}