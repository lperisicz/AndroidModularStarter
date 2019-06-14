package com.perisic.luka.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.perisic.luka.local.data.UserModel;

/**
 * Created by Luka Perisic on 12.6.2019..
 */
@Dao
public interface UserModelDao extends BaseModelDao<UserModel> {

    @Query("SELECT * FROM UserData")
    UserModel getUser();

    @Query("DELETE FROM UserData")
    void deleteUser();

}