package com.kynzai.tripmate_stud.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.local.FakeAuthService;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FakeAuthService service;

    public AuthRepositoryImpl(Context context) {
        service = new FakeAuthService(context.getApplicationContext());
    }

    @Override
    public LiveData<UserProfile> getCurrentUser() {
        return service.getCurrentUser();
    }

    @Override
    public void register(String email, String password) {
        service.register(email, password);
    }

    @Override
    public void login(String email, String password) {
        service.login(email, password);
    }

    @Override
    public void logout() {
        service.logout();
    }
}
