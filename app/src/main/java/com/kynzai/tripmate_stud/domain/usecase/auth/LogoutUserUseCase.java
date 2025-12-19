package com.kynzai.tripmate_stud.domain.usecase.auth;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class LogoutUserUseCase {
    private final UserRepository repository;

    public LogoutUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.logout();
    }
}
