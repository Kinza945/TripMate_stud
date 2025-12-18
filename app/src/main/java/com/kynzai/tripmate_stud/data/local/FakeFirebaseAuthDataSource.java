package com.kynzai.tripmate_stud.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.domain.model.UserSession;

/**
 * Simple in-memory auth provider that mimics Firebase email authentication
 * for offline demo purposes.
 */
public class FakeFirebaseAuthDataSource {
    private final MutableLiveData<UserSession> session = new MutableLiveData<>(new UserSession("guest@tripmate", false));

    public LiveData<UserSession> getSession() {
        return session;
    }

    public void signIn(String email, String password) {
        session.postValue(new UserSession(email, true));
    }

    public void register(String email, String password) {
        session.postValue(new UserSession(email, true));
    }

    public void signOut() {
        session.postValue(new UserSession("guest@tripmate", false));
    }
}
