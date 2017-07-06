package com.acbelter.modernapp.ui;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acbelter.modernapp.App;
import com.acbelter.modernapp.R;
import com.acbelter.modernapp.domain.model.User;
import com.acbelter.modernapp.presentation.UsersPresenter;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UsersActivity extends MvpAppCompatActivity implements UsersView {
    @Inject
    @InjectPresenter
    UsersPresenter mUsersPresenter;
    @BindView(R.id.users_list)
    RecyclerView mUsersList;
    @BindView(R.id.btn_get_users)
    Button mGetUsersButton;

    private UsersAdapter mUsersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getComponentManager().addUserComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.bind(this);

        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
        mUsersList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mUsersAdapter = new UsersAdapter(new ArrayList<>());
        mUsersList.setAdapter(mUsersAdapter);
    }

    @ProvidePresenter
    public UsersPresenter provideUsersPresenter() {
        return mUsersPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getComponentManager().removeUserComponent();
    }

    @OnClick(R.id.btn_get_users)
    public void onStartClicked(View view) {
        mUsersPresenter.getUsers();
    }

    @Override
    public void showUsersLoading() {
        mGetUsersButton.setEnabled(false);
    }

    @Override
    public void showUsers(List<User> users) {
        mUsersAdapter.getUsers().clear();
        mUsersAdapter.getUsers().addAll(users);
        mUsersAdapter.notifyDataSetChanged();
        mGetUsersButton.setEnabled(true);
    }

    @Override
    public void showError() {
        mGetUsersButton.setEnabled(true);
        Toast.makeText(getApplicationContext(),
                R.string.toast_show_users_error, Toast.LENGTH_LONG).show();
    }
}
