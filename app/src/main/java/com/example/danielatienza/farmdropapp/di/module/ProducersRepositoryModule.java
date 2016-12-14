package com.example.danielatienza.farmdropapp.di.module;

import com.example.danielatienza.farmdropapp.database.ProducersRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoSession;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class ProducersRepositoryModule {

    @Singleton
    @Provides
    public ProducersRepository provideImageRepository(DaoSession daoSession) {
        return new ProducersRepository(daoSession);
    }
}
