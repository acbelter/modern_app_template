package com.acbelter.modernapp.ui;

import com.acbelter.modernapp.domain.model.User;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface UsersView extends MvpView {
    void showUsersLoading();
    void showUsers(List<User> users);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError();
}
