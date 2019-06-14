package com.perisic.luka.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.perisic.luka.local.data.TokenModel;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Dao
public interface TokenModelDao extends BaseModelDao<TokenModel> {

    @Query("SELECT * FROM TokenData")
    TokenModel getTokenModel();

    @Query("DELETE FROM TokenData")
    void deleteTokenData();

}