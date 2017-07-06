package com.acbelter.modernapp.domain.repository;

import com.acbelter.modernapp.domain.model.User;

import java.util.List;

import io.reactivex.Observable;

public interface UsersRepo {
    Observable<List<User>> getUsers();
}
