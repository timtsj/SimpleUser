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

import android.content.Context;
import android.preference.PreferenceManager;

import com.tsdreamdeveloper.simpleuser.common.SharedPrefsHelper;

import dagger.Module;
import dagger.Provides;

/**
 * @author Timur Seisembayev
 * @since 22.07.2019
 */

@Module
public class SharedPrefsModule {
    private Context mContext;
    private SharedPrefsHelper mSharedPrefsHelper;

    public SharedPrefsModule(Context context) {
        mContext = context;
    }

    @Provides
    public SharedPrefsHelper provideSharedPrefs() {
        mSharedPrefsHelper = new SharedPrefsHelper(PreferenceManager.getDefaultSharedPreferences(mContext), mContext);
        return mSharedPrefsHelper;
    }
}