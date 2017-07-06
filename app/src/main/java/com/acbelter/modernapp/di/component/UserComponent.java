package com.acbelter.modernapp.di.component;

import com.acbelter.modernapp.di.module.UserModule;
import com.acbelter.modernapp.di.scope.UserScope;
import com.acbelter.modernapp.ui.UsersActivity;

import dagger.Subcomponent;

@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {
    void inject(UsersActivity usersActivity);
}
