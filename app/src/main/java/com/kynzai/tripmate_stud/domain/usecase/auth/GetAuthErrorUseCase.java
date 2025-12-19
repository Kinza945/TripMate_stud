package com.kynzai.tripmate_stud.domain.usecase.auth;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class GetAuthErrorUseCase {
    private final UserRepository repository;

    public GetAuthErrorUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> execute() {
        return repository.getAuthError();
    }
}
