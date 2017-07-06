package com.acbelter.modernapp.domain.model;

public class User {
    private String mName;

    public User() {}

    public User(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return mName.equals(user.mName);

    }

    @Override
    public int hashCode() {
        return mName.hashCode();
    }

    @Override
    public String toString() {
        return mName;
    }
}
