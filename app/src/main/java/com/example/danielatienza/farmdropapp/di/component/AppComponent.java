package com.example.danielatienza.farmdropapp.di.component;

import com.example.danielatienza.farmdropapp.App;
import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.di.module.ApiModule;
import com.example.danielatienza.farmdropapp.di.module.AppModule;
import com.example.danielatienza.farmdropapp.di.module.DatabaseModule;
import com.example.danielatienza.farmdropapp.di.module.ProducersRepositoryModule;
import com.example.danielatienza.farmdropapp.di.module.TransitionModule;
import com.example.danielatienza.farmdropapp.network.ProducersInterface;
import com.example.danielatienza.farmdropapp.utils.manager.CallManager;
import com.example.danielatienza.farmdropapp.utils.manager.ParserManager;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import javax.inject.Singleton;

import dagger.Component;
import greendao.DaoSession;
import retrofit2.Retrofit;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        TransitionModule.class,
        DatabaseModule.class,
        ProducersRepositoryModule.class
})
public interface AppComponent {

    void inject(App app);

    App provideApp();
    CallManager provideCallManager();
    Retrofit provideRetrofit();
    ProducersInterface provideProducersInterface();
    ProducersManager provideProducerskManager();
    ParserManager provideParserManager();
    DaoSession provideDaoSession();
    ProducersRepository provideProducersRepository();

}