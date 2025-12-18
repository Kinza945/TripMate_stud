package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class AuthUseCases {
    private final AuthRepository repository;

    public AuthUseCases(AuthRepository repository) {
        this.repository = repository;
    }

    public void login(String email, String password) {
        repository.signIn(email, password);
    }

    public void register(String email, String password) {
        repository.register(email, password);
    }

    public void signOut() {
        repository.signOut();
    }
}
