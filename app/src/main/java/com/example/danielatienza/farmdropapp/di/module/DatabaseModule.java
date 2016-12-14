package com.example.danielatienza.farmdropapp.di.module;

import android.database.sqlite.SQLiteDatabase;

import com.example.danielatienza.farmdropapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class DatabaseModule {

    private static final String DATABASE_NAME = "GALLERY_DB";

    @Singleton
    @Provides
    public DaoSession provideDaoSession(App app) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}
