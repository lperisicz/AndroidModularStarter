package com.perisic.luka.local.di;

import com.perisic.luka.local.BaseLocalDatabase;
import com.perisic.luka.local.dao.UserModelDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Luka Perisic on 13.6.2019..
 */
@Module
public abstract class BaseRoomModule {


    @Provides
    @Singleton
    static UserModelDao provideUserModelDao(BaseLocalDatabase baseLocalDatabase) {
        return baseLocalDatabase.userModelDao();
    }

}