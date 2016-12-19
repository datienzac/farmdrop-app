package com.example.danielatienza.farmdropapp.di.module;


import android.support.v4.util.Pair;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.example.danielatienza.farmdropapp.App;
import com.example.danielatienza.farmdropapp.BuildConfig;
import com.example.danielatienza.farmdropapp.database.ProducersRepository;
import com.example.danielatienza.farmdropapp.network.ProducersInterface;
import com.example.danielatienza.farmdropapp.utils.manager.ParserManager;
import com.example.danielatienza.farmdropapp.utils.manager.ProducersManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.net.sip.SipErrorCode.TIME_OUT;

/**
 * Created by danielatienza on 14/12/2016.
 */
@Module
public class ApiModule {

    private static final String PREFIX = "https://";
    private static final String URL = "fd-v5-api-release.herokuapp.com/2/";
    private static final String BASE_URL = PREFIX.concat(URL);


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.connectTimeout(0, TimeUnit.MILLISECONDS).build();
        return client;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit
                .Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    public ProducersInterface provideProducersInterface(Retrofit retrofit) {
        return retrofit.create(ProducersInterface.class);
    }

    @Singleton
    @Provides
    public ProducersManager provideProducersManager(
            ProducersInterface producersInterface,
            ParserManager parserManager,
            ProducersRepository producersRepository) {
        return new ProducersManager(producersInterface,
                parserManager,
                producersRepository);
    }

    @Singleton
    @Provides
    public ParserManager provideParserModule() {
        return new ParserManager();
    }
}