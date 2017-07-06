package com.acbelter.modernapp.presentation;

import com.acbelter.modernapp.domain.interactor.UsersInteractor;
import com.acbelter.modernapp.ui.UsersView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class UsersPresenter extends MvpPresenter<UsersView> {
    private UsersInteractor mUsersInteractor;
    private Disposable mGetUsersDisposable;

    @Inject
    public UsersPresenter(UsersInteractor usersInteractor) {
        mUsersInteractor = usersInteractor;
    }

    public void getUsers() {
        if (mGetUsersDisposable != null) {
            return;
        }

        mGetUsersDisposable = mUsersInteractor.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        users -> {
                            Timber.d("getUsers()->onNext(): " + users.size());
                            getViewState().showUsers(users);
                        },
                        e -> {
                            Timber.d("getUsers()->onError(): " + e.toString());
                            getViewState().showError();
                            stopGetUsersProcess();
                        },
                        () -> {
                            Timber.d("getUsers()->onComplete()");
                            stopGetUsersProcess();
                        },
                        d -> {
                            Timber.d("getUsers()->onSubscribe()");
                            getViewState().showUsersLoading();
                        }
                );
    }

    private void stopGetUsersProcess() {
        if (mGetUsersDisposable != null && !mGetUsersDisposable.isDisposed()) {
            mGetUsersDisposable.dispose();
        }
        mGetUsersDisposable = null;
    }

    public void stop() {
        stopGetUsersProcess();
    }
}
