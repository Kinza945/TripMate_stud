package com.kynzai.tripmate_stud.domain.usecase.profile;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;

public class GetUserByIdUseCase {
    private final ProfileRepository repository;

    public GetUserByIdUseCase(ProfileRepository repository) {
        this.repository = repository;
    }

    public LiveData<UserProfile> execute(String uid) {
        return repository.getUserById(uid);
    }
}
