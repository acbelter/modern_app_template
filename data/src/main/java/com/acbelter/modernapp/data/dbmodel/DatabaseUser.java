package com.acbelter.modernapp.data.dbmodel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DatabaseUser extends RealmObject {
    @PrimaryKey
    public long id = -1L;
    @Required
    public String name;
}
