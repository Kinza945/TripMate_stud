package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.domain.model.UserSession;
import com.kynzai.tripmate_stud.domain.usecase.AuthUseCases;
import com.kynzai.tripmate_stud.domain.usecase.GetSessionUseCase;

public class ProfileViewModel extends ViewModel {
    private final GetSessionUseCase sessionUseCase;
    private final AuthUseCases authUseCases;

    public ProfileViewModel(GetSessionUseCase sessionUseCase, AuthUseCases authUseCases) {
        this.sessionUseCase = sessionUseCase;
        this.authUseCases = authUseCases;
    }

    public LiveData<UserSession> getSession() {
        return sessionUseCase.execute();
    }

    public void login(String email, String password) {
        authUseCases.login(email, password);
    }

    public void register(String email, String password) {
        authUseCases.register(email, password);
    }

    public void signOut() {
        authUseCases.signOut();
    }
}
