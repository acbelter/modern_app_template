package com.acbelter.modernapp.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.acbelter.modernapp.data.database.DatabaseService;
import com.acbelter.modernapp.data.network.NetworkService;
import com.acbelter.modernapp.data.repository.UsersRepoImpl;
import com.acbelter.modernapp.domain.repository.UsersRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context mAppContext;

    public AppModule(Context context) {
        mAppContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mAppContext;
    }
}
