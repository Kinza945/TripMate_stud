package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class RegisterUserUseCase {
    private final UserRepository repository;

    public RegisterUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, String password) {
        repository.register(email, password);
    }
}
