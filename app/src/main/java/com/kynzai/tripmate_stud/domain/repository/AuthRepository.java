package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;

public interface AuthRepository {
    LiveData<UserProfile> getCurrentUser();
    LiveData<String> getAuthMessage();
    LiveData<String> getAuthError();
    void register(String email, String password);
    void login(String email, String password);
    void logout();
    void clearAuthMessage();
    void clearAuthError();
}
