package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.local.FakeFirebaseAuthDataSource;
import com.kynzai.tripmate_stud.domain.model.UserSession;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FakeFirebaseAuthDataSource authDataSource;

    public AuthRepositoryImpl(FakeFirebaseAuthDataSource authDataSource) {
        this.authDataSource = authDataSource;
    }

    @Override
    public LiveData<UserSession> getSession() {
        return authDataSource.getSession();
    }

    @Override
    public void signIn(String email, String password) {
        authDataSource.signIn(email, password);
    }

    @Override
    public void register(String email, String password) {
        authDataSource.register(email, password);
    }

    @Override
    public void signOut() {
        authDataSource.signOut();
    }
}
