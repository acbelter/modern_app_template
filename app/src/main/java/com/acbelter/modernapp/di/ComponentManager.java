package com.acbelter.modernapp.di;

import android.content.Context;

import com.acbelter.modernapp.di.component.AppComponent;
import com.acbelter.modernapp.di.component.DaggerAppComponent;
import com.acbelter.modernapp.di.component.UserComponent;
import com.acbelter.modernapp.di.module.AppModule;
import com.acbelter.modernapp.di.module.DatabaseModule;
import com.acbelter.modernapp.di.module.NetworkModule;
import com.acbelter.modernapp.di.module.UserModule;

public class ComponentManager {
    private AppComponent mAppComponent;
    private UserComponent mUserComponent;

    public ComponentManager(Context context) {
        mAppComponent = buildAppComponent(context.getApplicationContext());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public UserComponent addUserComponent() {
        if (mUserComponent == null) {
            mUserComponent = mAppComponent.addUserComponent(new UserModule());
        }
        return mUserComponent;
    }

    public void removeUserComponent() {
        mUserComponent = null;
    }

    private AppComponent buildAppComponent(Context context) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .databaseModule(new DatabaseModule())
                .networkModule(new NetworkModule())
                .build();
    }
}
