/*
 * Copyright 2019 TSDream Developer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tsdreamdeveloper.simpleuser.di.modules;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tsdreamdeveloper.simpleuser.BuildConfig;
import com.tsdreamdeveloper.simpleuser.common.ConnectivityInterceptor;
import com.tsdreamdeveloper.simpleuser.common.LiveNetworkMonitor;
import com.tsdreamdeveloper.simpleuser.common.Utils;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Timur Seisembayev
 * @since 20.07.2019
 */

@Module
public class RetrofitModule {

    private static final int TIMEOUT = 1;
    private Retrofit retrofit;
    private Context mContext;
    private String mHttpUrlBasement;

    public RetrofitModule(Application app) {
        mHttpUrlBasement = Utils.BASE_URL;
        mContext = app.getApplicationContext();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient httpClient, Converter.Factory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(mHttpUrlBasement)
                    .client(httpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .build();
        }
        return retrofit;
    }

    @Provides
    public OkHttpClient provideHttpClient() {
        OkHttpClient.Builder client;
        // provideInterceptorIsConnected checked is network available
        client = new OkHttpClient.Builder()
                .addInterceptor(provideInterceptorIsConnected(mContext))
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT, TimeUnit.MINUTES);

        if (BuildConfig.DEBUG) {
            client.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return client.build();
    }

    @Provides
    public Interceptor provideInterceptorIsConnected(Context getCurrentContext) {
        Observable<Boolean> rxNetworkMonitor =
                Observable.fromCallable(new LiveNetworkMonitor(getCurrentContext));
        return new ConnectivityInterceptor(rxNetworkMonitor);
    }

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }
}
