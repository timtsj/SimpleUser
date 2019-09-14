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

package com.tsdreamdeveloper.simpleuser.di;

import android.content.Context;

import com.tsdreamdeveloper.simpleuser.api.NetworkService;
import com.tsdreamdeveloper.simpleuser.di.modules.AppModule;
import com.tsdreamdeveloper.simpleuser.di.modules.ContextModule;
import com.tsdreamdeveloper.simpleuser.common.SharedPrefsHelper;
import com.tsdreamdeveloper.simpleuser.di.modules.SharedPrefsModule;
import com.tsdreamdeveloper.simpleuser.mvp.presenter.MainPresenter;
import com.tsdreamdeveloper.simpleuser.mvp.presenter.UserDetailPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Timur Seisembayev
 * @since 20.07.2019
 */

@Singleton
@Component(modules = {ContextModule.class, AppModule.class, SharedPrefsModule.class})
public interface AppComponent {

    Context getContext();
    SharedPrefsHelper getSharedPrefsHelper();
    NetworkService getContactsService();

    void inject(MainPresenter mainPresenter);
    void inject(UserDetailPresenter userDetailPresenter);
}
