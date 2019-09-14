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

import com.tsdreamdeveloper.simpleuser.mvp.model.User;

import java.util.List;

import io.reactivex.Single;

/**
 * @author Timur Seisembayev
 * @since 20.07.2019
 */
public class NetworkService {

    private Api api;

    public NetworkService(Api api) {
        this.api = api;
    }

    public Single<List<User>> getUsers() {
        return api.getUsers();
    }

    public Single<User> createUser(User user) {
        return api.createUser(user);
    }

    public Single<User> editUser(long userId, User user) {
        return api.editUser(userId, user);
    }
}
