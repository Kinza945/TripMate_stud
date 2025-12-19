package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;

public interface ProfileRepository {
    LiveData<UserProfile> getUserById(String uid);
    void createUserProfile(String uid, String email, String displayName);
}
