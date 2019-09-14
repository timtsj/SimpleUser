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

package com.tsdreamdeveloper.simpleuser.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.tsdreamdeveloper.simpleuser.R;
import com.tsdreamdeveloper.simpleuser.databinding.ActivityMainBinding;
import com.tsdreamdeveloper.simpleuser.mvp.model.User;
import com.tsdreamdeveloper.simpleuser.mvp.presenter.MainPresenter;
import com.tsdreamdeveloper.simpleuser.mvp.view.MainView;
import com.tsdreamdeveloper.simpleuser.ui.adapter.UserAdapter;

import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;

import static com.tsdreamdeveloper.simpleuser.ui.activity.UserDetailActivity.EXTRA_USER;

/**
 * @author Timur Seisembayev
 * @since 09.03.2019
 */

public class MainActivity extends BaseActivity implements MainView, UserAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectPresenter
    MainPresenter presenter;

    private UserAdapter adapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.e(TAG, "onCreate: " + throwable.getMessage());
        });
        initRecyclerView();
    }

    /**
     * Implement RecyclerView and Adapter
     */
    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Add users list from api
     *
     * @param users list from api
     */
    @Override
    public void addList(List<User> users) {
        adapter.clearItems();
        adapter.setItems(users);
    }

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        String json = new Gson().toJson(user);
        intent.putExtra(EXTRA_USER, json);
        startActivity(intent);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.fab_create_user) {
            startActivity(new Intent(this, UserDetailActivity.class));
        }
    }
}
