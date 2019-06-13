package com.perisic.luka.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.perisic.luka.local.data.UserModel;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Luka Perisic on 12.6.2019..
 */
@Dao
public interface UserModelDao {

    @Query("SELECT * FROM UserData")
    UserModel getUser();

    @Insert(onConflict = REPLACE)
    void insertUser(UserModel userModel);

    @Query("DELETE FROM UserData")
    void deleteUser();

}