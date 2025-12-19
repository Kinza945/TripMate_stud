package com.kynzai.tripmate_stud.domain.usecase.auth;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class ClearAuthErrorUseCase {
    private final UserRepository repository;

    public ClearAuthErrorUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.clearAuthError();
    }
}
