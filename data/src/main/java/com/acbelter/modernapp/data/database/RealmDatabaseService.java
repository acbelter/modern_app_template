package com.acbelter.modernapp.data.database;

import android.content.Context;

import com.acbelter.modernapp.data.dbmodel.DatabaseUser;

import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmDatabaseService implements DatabaseService {
    private RealmConfiguration mConfiguration;

    public RealmDatabaseService(Context context) {
        Realm.init(context);
        mConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    private long getMaxId(Realm realm) {
        Number maxId = realm.where(DatabaseUser.class).max("id");
        return maxId != null ? maxId.longValue() : 0;
    }

    private Realm getRealm() {
        return Realm.getInstance(mConfiguration);
    }

    @Override
    public Observable<Long> insertUser(final DatabaseUser user) {
        if (user == null) {
            return Observable.error(new DatabaseQueryException("User must be not null"));
        }

        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            user.id = getMaxId(realm) + 1;
                            DatabaseUser newUser = realm.copyToRealmOrUpdate(user);
                            e.onNext(newUser.id);
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to insert user"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<Integer> insertUsers(final List<DatabaseUser> users) {
        if (users == null || users.isEmpty()) {
            return Observable.just(0);
        }

        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            long maxId = getMaxId(realm);
                            for (DatabaseUser user : users) {
                                maxId++;
                                user.id = maxId;
                            }
                            int count = realm.copyToRealm(users).size();
                            e.onNext(count);
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to insert users"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<DatabaseUser> getUser(final long id) {
        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            DatabaseUser user = realm.where(DatabaseUser.class)
                                    .equalTo("id", id)
                                    .findFirst();
                            if (user != null) {
                                e.onNext(user);
                            }
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to get user"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<List<DatabaseUser>> getUsers() {
        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            List<DatabaseUser> users =
                                    realm.copyFromRealm(realm.where(DatabaseUser.class).findAll());
                            e.onNext(users);
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to get users"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<Boolean> updateUser(final DatabaseUser user) {
        if (user == null) {
            return Observable.error(new DatabaseQueryException("User must be not null"));
        }

        if (user.id == -1L) {
            return Observable.just(false);
        }

        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            DatabaseUser userToUpdate = realm.where(DatabaseUser.class)
                                    .equalTo("id", user.id)
                                    .findFirst();
                            if (userToUpdate != null) {
                                realm.copyToRealmOrUpdate(user);
                                e.onNext(true);
                            } else {
                                e.onNext(false);
                            }
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to update user"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<Boolean> deleteUser(final long id) {
        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            RealmResults<DatabaseUser> usersResults =
                                    realm.where(DatabaseUser.class)
                                            .equalTo("id", id)
                                            .findAll();
                            e.onNext(usersResults.deleteAllFromRealm());
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to delete user"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }

    @Override
    public Observable<Integer> deleteUsers() {
        return Observable.create(
                e -> {
                    Realm realmInstance = getRealm();
                    try {
                        realmInstance.executeTransaction(realm -> {
                            RealmResults<DatabaseUser> usersResults =
                                    realm.where(DatabaseUser.class).findAll();
                            int count = usersResults.size();
                            if (usersResults.deleteAllFromRealm()) {
                                e.onNext(count);
                            } else {
                                e.onNext(0);
                            }
                            e.onComplete();
                        });
                    } catch (Exception ex) {
                        e.onError(new DatabaseQueryException("Fail to delete users"));
                    } finally {
                        realmInstance.close();
                    }
                });
    }
}