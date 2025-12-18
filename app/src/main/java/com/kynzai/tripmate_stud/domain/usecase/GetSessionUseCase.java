package com.kynzai.tripmate_stud.domain.usecase;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserSession;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class GetSessionUseCase {
    private final AuthRepository repository;

    public GetSessionUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<UserSession> execute() {
        return repository.getSession();
    }
}
