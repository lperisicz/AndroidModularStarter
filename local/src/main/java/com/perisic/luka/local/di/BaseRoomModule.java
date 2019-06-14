package com.perisic.luka.local.di;

import android.content.Context;

import androidx.room.Room;

import com.perisic.luka.base.di.qualifiers.ApplicationContext;
import com.perisic.luka.base.di.qualifiers.DatabaseClass;
import com.perisic.luka.base.di.qualifiers.DatabaseName;
import com.perisic.luka.local.BaseLocalDatabase;
import com.perisic.luka.local.dao.TokenModelDao;
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

    @Provides
    @Singleton
    static TokenModelDao provideTokenModelDao(BaseLocalDatabase baseLocalDatabase) {
        return baseLocalDatabase.tokenModelDao();
    }

    @Provides
    @Singleton
    static BaseLocalDatabase provideLocalDatabase(
            @ApplicationContext Context applicationContext,
            @DatabaseName String databaseName,
            @DatabaseClass Class<? extends BaseLocalDatabase> databaseClass
    ) {
        return Room.databaseBuilder(applicationContext, databaseClass, databaseName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

}