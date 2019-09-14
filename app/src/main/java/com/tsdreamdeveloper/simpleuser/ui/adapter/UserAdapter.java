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

package com.tsdreamdeveloper.simpleuser.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tsdreamdeveloper.simpleuser.R;
import com.tsdreamdeveloper.simpleuser.databinding.ListItemUserBinding;
import com.tsdreamdeveloper.simpleuser.mvp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Timur Seisembayev
 * @since 22.07.2019
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    /**
     * The interface that receives onClick item.
     */
    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    /**
     * New user list
     */
    private List<User> userList;
    private final OnItemClickListener listener;

    public UserAdapter(OnItemClickListener onItemClickListener) {
        this.userList = new ArrayList<>();
        listener = onItemClickListener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_user, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.bind(userList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ListItemUserBinding binding;

        ViewHolder(ListItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(User user, final OnItemClickListener listener) {
            binding.setUser(user);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> listener.onItemClick(user));
        }
    }

    public void setItems(List<User> users) {
        this.userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.userList.clear();
        notifyDataSetChanged();
    }
}