package com.perisic.luka.local;

import androidx.room.RoomDatabase;

import com.perisic.luka.local.dao.UserModelDao;

/**
 * Created by Luka Perisic on 12.6.2019..
 */
public abstract class BaseLocalDatabase extends RoomDatabase {

    public abstract UserModelDao userModelDao();

}