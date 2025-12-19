package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class LoginUserUseCase {
    private final UserRepository repository;

    public LoginUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, String password) {
        repository.login(email, password);
    }
}
