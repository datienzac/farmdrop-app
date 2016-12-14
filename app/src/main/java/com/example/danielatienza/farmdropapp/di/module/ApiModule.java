package com.example.danielatienza.farmdropapp.di.module;


import android.support.v4.util.Pair;
import android.util.Base64;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.example.danielatienza.farmdropapp.App;
import com.example.danielatienza.farmdropapp.BuildConfig;
import com.example.danielatienza.farmdropapp.network.ProducersInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

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

public class ApiModule {

    private static final String UTF_8 = "UTF-8";

    private static final String PREFIX = "https://";
    private static final String URL = "api.shutterstock.com/";
    private static final String VERSION = "v2/";
    private static final String BASE_URL = PREFIX.concat(URL).concat(VERSION);

    @Singleton
    @Provides
    Pair<String, String> provideCredentials(App app) {
        String username = "";
        String password = "";

        Properties properties = new Properties();
        try {
            InputStream inputStream = app.getAssets().open("api.properties");
            properties.load(inputStream);

            username = new String(
                    Base64.decode(properties.getProperty("username").getBytes(UTF_8),
                            Base64.DEFAULT), UTF_8);
            password = new String(
                    Base64.decode(properties.getProperty("password").getBytes(UTF_8),
                            Base64.DEFAULT), UTF_8);
        } catch (IOException e) {
            Crashlytics.log(e.getMessage());
        }

        return new Pair<>(username, password);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(final Pair<String, String> credentials) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient client = builder.connectTimeout(0, TimeUnit.MILLISECONDS).build();

        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String basic = "Basic " +
                        Base64
                                .encodeToString((credentials.first)
                                        .concat(":")
                                        .concat(credentials.second)
                                        .getBytes(), Base64.NO_WRAP);

                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", basic)
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();

                /* RETRY CALL FOR 3 TIMES */

                Response response = chain.proceed(request);
                int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    tryCount++;
                    response = chain.proceed(request);
                }

                if (BuildConfig.DEBUG) {
                    String bodyString = response.body().string();
                    Log.d("api", String.format("Sending request %s with headers %s", original.url(), original.headers()));
                    Log.d("api", String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers()));
                    response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
                }

                return response;
            }
        });
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
    public ProducersManager provideShutterStockManager(
            ProducersInterface producersInterface,
            ParserManager parserManager,
            ImageRepository imageRepository,
            CategoryRepository categoryRepository) {
        return new ShutterStockManager(shutterStockInterface,
                parserManager,
                imageRepository,
                categoryRepository);
    }

    @Singleton
    @Provides
    public ParserManager provideParserModule() {
        return new ParserManager();
    }
}