package com.perisic.luka.local.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.perisic.luka.local.data.UserModel;

/**
 * Created by Luka Perisic on 12.6.2019..
 */
@Dao
public abstract class UserModelDao implements BaseModelDao<UserModel> {

    @Query("SELECT * FROM UserData")
    public abstract UserModel getUser();

    @Query("DELETE FROM UserData")
    public abstract void deleteUser();

    @Transaction
    public void saveUser(UserModel userModel) {
        deleteUser();
        insert(userModel);
    }

}