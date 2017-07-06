package com.acbelter.modernapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acbelter.modernapp.R;
import com.acbelter.modernapp.domain.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<User> mUsers;

    public UsersAdapter(List<User> users) {
        mUsers = users;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mUsers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public List<User> getUsers() {
        return mUsers;
    }
}