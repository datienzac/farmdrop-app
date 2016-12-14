package com.example.danielatienza.farmdropapp;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.example.danielatienza.farmdropapp.di.component.AppComponent;
import com.example.danielatienza.farmdropapp.di.module.AppModule;

import io.fabric.sdk.android.Fabric;

/**
 * Created by danielatienza on 14/12/2016.
 */
public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        defineComponent();
    }

    private void defineComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}