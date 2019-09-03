package com.perisic.luka.remote.data.response;

/**
 * Created by Luka Perisic on 3.9.2019..
 */
public class RegisterResponse {

    private String token;
    private String refreshToken;

    public RegisterResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
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
