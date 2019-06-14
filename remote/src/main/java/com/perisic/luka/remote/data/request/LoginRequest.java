package com.perisic.luka.remote.data.request;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
