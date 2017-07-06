package com.acbelter.modernapp.di.module;

import com.acbelter.modernapp.data.network.NetworkService;
import com.acbelter.modernapp.data.network.RetrofitNetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    NetworkService provideNetworkService() {
        return new RetrofitNetworkService();
    }
}