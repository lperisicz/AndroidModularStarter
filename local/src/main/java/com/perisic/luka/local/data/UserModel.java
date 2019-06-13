package com.perisic.luka.local.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Luka Perisic on 12.6.2019..
 */
@Entity(tableName = "UserData")
public class UserModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "email")
    private String email;

    public UserModel(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    //region GETTERS

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    //endregion

    //region SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //endregion
}
