package com.perisic.luka.repository.di;

import com.perisic.luka.repository.repos.abstraction.AuthRepository;
import com.perisic.luka.repository.repos.implementation.AuthRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Luka Perisic on 14.6.2019..
 */
@Module
public abstract class BaseRepositoryModule {

    @Singleton
    @Binds
    abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);

}