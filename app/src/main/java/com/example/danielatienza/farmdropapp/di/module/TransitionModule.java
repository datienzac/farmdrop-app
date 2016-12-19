package com.example.danielatienza.farmdropapp.di.module;

import com.example.danielatienza.farmdropapp.utils.manager.CallManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by danielatienza on 16/12/2016.
 */
@Module
public class TransitionModule {

    @Singleton
    @Provides
    public CallManager provideCallManager() {
        return new CallManager();
    }

}
