package com.kynzai.tripmate_stud.domain.usecase.auth;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class GetAuthMessageUseCase {
    private final UserRepository repository;

    public GetAuthMessageUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> execute() {
        return repository.getAuthMessage();
    }
}
