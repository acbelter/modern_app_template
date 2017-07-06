package com.acbelter.modernapp.di.component;

import com.acbelter.modernapp.di.module.AppModule;
import com.acbelter.modernapp.di.module.DatabaseModule;
import com.acbelter.modernapp.di.module.NetworkModule;
import com.acbelter.modernapp.di.module.UserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        DatabaseModule.class})
public interface AppComponent {
    UserComponent addUserComponent(UserModule userModule);
}