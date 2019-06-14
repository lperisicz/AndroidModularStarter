package com.perisic.luka.local.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
public interface BaseModelDao<T> {

    @Insert(onConflict = REPLACE)
    void insert(T model);

    @Update
    void update(T model);

    @Delete
    void delete(T model);
}