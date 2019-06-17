package com.perisic.luka.remote.di;

import com.perisic.luka.base.di.qualifiers.Authentication;
import com.perisic.luka.remote.services.AuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Module
public abstract class BaseServiceModule {

    @Provides
    @Singleton
    static AuthService providesAuthService(@Authentication Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

}