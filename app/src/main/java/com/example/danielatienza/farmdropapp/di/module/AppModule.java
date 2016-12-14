package com.example.danielatienza.farmdropapp.di.module;

import com.example.danielatienza.farmdropapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Singleton
    @Provides
    public App provideApp() {
        return mApp;
    }
}