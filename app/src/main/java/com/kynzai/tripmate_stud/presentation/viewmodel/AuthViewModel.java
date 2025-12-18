package com.kynzai.tripmate_stud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.repository.AuthRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {

    private final AuthRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthRepositoryImpl(application.getApplicationContext());
    }

    public LiveData<UserProfile> getCurrentUser() {
        return repository.getCurrentUser();
    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

    public void register(String email, String password) {
        repository.register(email, password);
    }

    public void logout() {
        repository.logout();
    }
}
