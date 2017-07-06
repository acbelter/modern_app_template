package com.acbelter.modernapp.di.module;

import com.acbelter.modernapp.data.database.DatabaseService;
import com.acbelter.modernapp.data.network.NetworkService;
import com.acbelter.modernapp.data.repository.UsersRepoImpl;
import com.acbelter.modernapp.di.scope.UserScope;
import com.acbelter.modernapp.domain.interactor.UsersInteractor;
import com.acbelter.modernapp.domain.repository.UsersRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    @Provides
    @UserScope
    UsersRepo provideUsersRepo(DatabaseService databaseService,
                               NetworkService networkService) {
        return new UsersRepoImpl(databaseService, networkService);
    }

    @Provides
    @UserScope
    UsersInteractor provideUsersInteractor(UsersRepo usersRepo) {
        return new UsersInteractor(usersRepo);
    }
}
