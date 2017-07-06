package com.acbelter.modernapp.data.database;

import com.acbelter.modernapp.data.dbmodel.DatabaseUser;
import com.acbelter.modernapp.domain.model.User;

import java.util.List;

import io.reactivex.Observable;

public interface DatabaseService {
    Observable<Long> insertUser(DatabaseUser user);
    Observable<Integer> insertUsers(List<DatabaseUser> users);
    Observable<DatabaseUser> getUser(long id);
    Observable<List<DatabaseUser>> getUsers();
    Observable<Boolean> updateUser(DatabaseUser user);
    Observable<Boolean> deleteUser(long id);
    Observable<Integer> deleteUsers();
}