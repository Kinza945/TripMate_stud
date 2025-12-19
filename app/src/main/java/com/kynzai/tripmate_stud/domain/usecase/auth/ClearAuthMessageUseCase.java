package com.kynzai.tripmate_stud.domain.usecase.auth;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class ClearAuthMessageUseCase {
    private final UserRepository repository;

    public ClearAuthMessageUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.clearAuthMessage();
    }
}
