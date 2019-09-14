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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.tsdreamdeveloper.simpleuser.common.Utils.EDIT_USERS;
import static com.tsdreamdeveloper.simpleuser.common.Utils.USERS;


/**
 * @author Timur Seisembayev
 * @since 20.07.2019
 */
public interface Api {

    @GET(USERS)
    Single<List<User>> getUsers();

    @POST(USERS)
    Single<User> createUser(@Body User user);

    @PATCH(EDIT_USERS)
    Single<User> editUser(@Path("Id") long userId, @Body User user);
}
