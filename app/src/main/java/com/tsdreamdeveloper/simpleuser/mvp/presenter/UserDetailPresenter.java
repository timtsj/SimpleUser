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
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.tsdreamdeveloper.simpleuser.R;
import com.tsdreamdeveloper.simpleuser.api.App;
import com.tsdreamdeveloper.simpleuser.api.NetworkService;
import com.tsdreamdeveloper.simpleuser.common.rxUtils;
import com.tsdreamdeveloper.simpleuser.mvp.model.User;
import com.tsdreamdeveloper.simpleuser.mvp.view.UserDetailView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author Timur Seisembayev
 * @since 14.09.2019
 */

@InjectViewState
public class UserDetailPresenter extends BasePresenter<UserDetailView> {

    private static final String TAG = UserDetailPresenter.class.getSimpleName();

    @Inject
    NetworkService networkService;

    @Inject
    Context context;

    public UserDetailPresenter() {
        App.getAppComponent().inject(this);
    }

    public void createUser(String firstName, String lastName, String email) {
        getViewState().onLoadingStart();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        Disposable subscription = networkService
                .createUser(user)
                .compose(rxUtils.applySchedulers())
                .subscribe(success -> {
                    result(success);
                }, throwable -> {
                    getViewState().onLoadingFinish();
                    Log.e(TAG, "createUser: ", throwable);
                    getViewState().showMessage(throwable.getMessage());
                });
        unsubscribeOnDestroy(subscription);
    }

    public void editUser(User user) {
        getViewState().onLoadingStart();
        Disposable subscription = networkService
                .editUser(user.getId(), user)
                .compose(rxUtils.applySchedulers())
                .subscribe(success -> {
                    result(success);
                }, throwable -> {
                    getViewState().onLoadingFinish();
                    Log.e(TAG, "editUser: ", throwable);
                    getViewState().showMessage(throwable.getMessage());
                });
        unsubscribeOnDestroy(subscription);
    }

    private void result(User response) {
        getViewState().onLoadingFinish();
        getViewState().showMessage(context.getString(R.string.data_saved_label));
    }
}
