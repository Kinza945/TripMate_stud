package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;

public interface AuthRepository {
    LiveData<UserProfile> getCurrentUser();
    void register(String email, String password);
    void login(String email, String password);
    void logout();
}
