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

package com.tsdreamdeveloper.simpleuser.api;

import android.app.Application;

import com.tsdreamdeveloper.simpleuser.di.AppComponent;
import com.tsdreamdeveloper.simpleuser.di.DaggerAppComponent;
import com.tsdreamdeveloper.simpleuser.di.modules.ContextModule;
import com.tsdreamdeveloper.simpleuser.di.modules.RetrofitModule;
import com.tsdreamdeveloper.simpleuser.di.modules.SharedPrefsModule;

/**
 * @author Timur Seisembayev
 * @since 20.07.2019
 */

public class App extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setsAppComponent();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public void setsAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .retrofitModule(new RetrofitModule(this))
                .sharedPrefsModule(new SharedPrefsModule(this))
                .build();
    }
}