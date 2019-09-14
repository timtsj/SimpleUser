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

package com.tsdreamdeveloper.simpleuser.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.tsdreamdeveloper.simpleuser.R;
import com.tsdreamdeveloper.simpleuser.api.App;
import com.tsdreamdeveloper.simpleuser.api.NetworkService;
import com.tsdreamdeveloper.simpleuser.common.rxUtils;
import com.tsdreamdeveloper.simpleuser.mvp.model.User;
import com.tsdreamdeveloper.simpleuser.mvp.view.MainView;
import com.tsdreamdeveloper.simpleuser.ui.activity.UserDetailActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author Timur Seisembayev
 * @since 22.07.2019
 */

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    NetworkService networkService;

    public MainPresenter() {
        App.getAppComponent().inject(this);
        getUsers();
    }

    /**
     * Fun for get contacts from api
     */
    private void getUsers() {
        getViewState().onLoadingStart();
        Disposable subscription = networkService
                .getUsers()
                .compose(rxUtils.applySchedulers())
                .subscribe(success -> {
                    result(success);
                }, throwable -> {
                    getViewState().onLoadingFinish();
                    Log.e(TAG, "getUsers: ", throwable);
                    getViewState().showMessage(throwable.getMessage());
                });
        unsubscribeOnDestroy(subscription);
    }

    /**
     * Fun for pass received contacts list from api to adapter
     *
     * @param response contact list
     */
    private void result(List<User> response) {
        getViewState().onLoadingFinish();
        getViewState().addList(response);
    }
}
