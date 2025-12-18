package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserSession;

public interface AuthRepository {
    LiveData<UserSession> getSession();

    void signIn(String email, String password);

    void register(String email, String password);

    void signOut();
}
