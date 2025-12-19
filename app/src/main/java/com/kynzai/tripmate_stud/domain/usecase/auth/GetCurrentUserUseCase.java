package com.kynzai.tripmate_stud.domain.usecase.auth;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class GetCurrentUserUseCase {
    private final UserRepository repository;

    public GetCurrentUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<UserProfile> execute() {
        return repository.getCurrentUser();
    }
}
