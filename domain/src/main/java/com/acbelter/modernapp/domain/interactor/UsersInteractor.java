package com.acbelter.modernapp.domain.interactor;

import com.acbelter.modernapp.domain.model.User;
import com.acbelter.modernapp.domain.repository.UsersRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UsersInteractor {
    private UsersRepo mUsersRepo;

    @Inject
    public UsersInteractor(UsersRepo usersRepo) {
        mUsersRepo = usersRepo;
    }

    public Observable<List<User>> getUsers() {
        return mUsersRepo.getUsers();
    }
}
