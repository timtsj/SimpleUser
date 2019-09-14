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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.gson.Gson;
import com.tsdreamdeveloper.simpleuser.R;
import com.tsdreamdeveloper.simpleuser.databinding.ActivityUserDetailBinding;
import com.tsdreamdeveloper.simpleuser.mvp.model.User;
import com.tsdreamdeveloper.simpleuser.mvp.presenter.UserDetailPresenter;
import com.tsdreamdeveloper.simpleuser.mvp.view.UserDetailView;

import static com.tsdreamdeveloper.simpleuser.common.Utils.validate;

public class UserDetailActivity extends BaseActivity implements UserDetailView {

    public static final String EXTRA_USER = "com.tsdreamdeveloper.simpleuser.extra.USER";
    private ActivityUserDetailBinding binding;

    @InjectPresenter
    UserDetailPresenter presenter;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
        init();
    }

    private void init() {
        mUser = new Gson().fromJson(getIntent().getStringExtra(EXTRA_USER), User.class);
        binding.setUser(mUser);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            String firstName = binding.etFirstName.getText().toString();
            String lastName = binding.etLastName.getText().toString();
            String email = binding.etEmail.getText().toString();

            binding.tilFirstName.setError(TextUtils.isEmpty(firstName) ? getString(R.string.enter_first_name_label) : "");
            binding.tilLastName.setError(TextUtils.isEmpty(lastName)? getString(R.string.enter_last_name_label) : "");
            binding.tilEmail.setError(TextUtils.isEmpty(email) ? getString(R.string.enter_email_label) : validate(email) ? "" : getString(R.string.enter_correct_email_label));

            if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && validate(email)) {
                if (mUser == null) {
                    presenter.createUser(firstName, lastName, email);
                } else {
                    mUser.setFirstName(firstName);
                    mUser.setLastName(lastName);
                    mUser.setEmail(email);
                    presenter.editUser(mUser);
                }
            }
        }
    }
}
