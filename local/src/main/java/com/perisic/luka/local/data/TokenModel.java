package com.perisic.luka.local.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Entity(tableName = "TokenData")
public class TokenModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id = 1;

    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "refreshToken")
    private String refreshToken;

    public TokenModel(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}