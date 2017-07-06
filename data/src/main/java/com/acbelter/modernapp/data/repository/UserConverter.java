package com.acbelter.modernapp.data.repository;

import com.acbelter.modernapp.data.dbmodel.DatabaseUser;
import com.acbelter.modernapp.data.netmodel.NetworkUser;
import com.acbelter.modernapp.domain.model.User;

public class UserConverter {
    public static User fromDatabaseUser(DatabaseUser dbUser) {
        if (dbUser == null) {
            throw new IllegalArgumentException("Converted object must be not null");
        }
        return new User(dbUser.name);
    }

    public static User fromNetworkUser(NetworkUser netUser) {
        if (netUser == null) {
            throw new IllegalArgumentException("Converted object must be not null");
        }
        return new User(netUser.name);
    }

    public static DatabaseUser fromNetworkToDatabaseUser(NetworkUser netUser) {
        if (netUser == null) {
            throw new IllegalArgumentException("Converted object must be not null");
        }
        DatabaseUser dbUser = new DatabaseUser();
        dbUser.name = netUser.name;
        return dbUser;
    }

    public static DatabaseUser fromUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Converted object must be not null");
        }
        DatabaseUser dbUser = new DatabaseUser();
        dbUser.name = user.getName();
        return dbUser;
    }
}
