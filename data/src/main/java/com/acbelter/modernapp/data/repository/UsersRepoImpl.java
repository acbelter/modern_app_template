package com.acbelter.modernapp.data.repository;

import android.util.Log;

import com.acbelter.modernapp.data.database.DatabaseService;
import com.acbelter.modernapp.data.dbmodel.DatabaseUser;
import com.acbelter.modernapp.data.network.NetworkService;
import com.acbelter.modernapp.domain.model.User;
import com.acbelter.modernapp.domain.repository.UsersRepo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class UsersRepoImpl implements UsersRepo {
    private DatabaseService mDatabaseService;
    private NetworkService mNetworkService;

    public UsersRepoImpl(DatabaseService databaseService,
                         NetworkService networkService) {
        mDatabaseService = databaseService;
        mNetworkService = networkService;
    }

    @Override
    public Observable<List<User>> getUsers() {
        Observable<List<DatabaseUser>> databaseObservable = mDatabaseService.getUsers()
                .doOnNext(users ->
                        Timber.d("Loaded users from database: " + users.size()));

        Observable<List<DatabaseUser>> networkObservable = mNetworkService.getUsers()
                .flatMapIterable(list -> list)
                .map(UserConverter::fromNetworkToDatabaseUser)
                .toList()
                .toObservable()
                .doOnNext(users -> {
                    Timber.d("Start saving users to database: " + users.size());
                    mDatabaseService.insertUsers(users)
                            .doOnNext(count -> {
                                Timber.d("Finish saving users to database: " + count);
                            })
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                });

        return Observable
                .concat(databaseObservable, networkObservable)
                .filter(users -> {
                    Timber.d("Filter users: " + users.size());
                    return !users.isEmpty();
                })
                .firstOrError()
                .toObservable()
                .flatMapIterable(list -> list)
                .map(UserConverter::fromDatabaseUser)
                .toList()
                .toObservable();
    }
}
