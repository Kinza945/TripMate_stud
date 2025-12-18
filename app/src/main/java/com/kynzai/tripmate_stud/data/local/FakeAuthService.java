package com.kynzai.tripmate_stud.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.domain.model.UserProfile;

public class FakeAuthService {
    private static final String PREF = "auth_pref";
    private static final String KEY_EMAIL = "email";
    private final MutableLiveData<UserProfile> currentUser = new MutableLiveData<>();
    private final SharedPreferences preferences;

    public FakeAuthService(Context context) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String savedEmail = preferences.getString(KEY_EMAIL, null);
        if (savedEmail != null) {
            currentUser.setValue(new UserProfile(savedEmail, savedEmail.split("@")[0]));
        }
    }

    public LiveData<UserProfile> getCurrentUser() {
        return currentUser;
    }

    public void register(String email, String password) {
        preferences.edit().putString(KEY_EMAIL, email).apply();
        currentUser.setValue(new UserProfile(email, email.split("@")[0]));
    }

    public void login(String email, String password) {
        preferences.edit().putString(KEY_EMAIL, email).apply();
        currentUser.setValue(new UserProfile(email, email.split("@")[0]));
    }

    public void logout() {
        preferences.edit().clear().apply();
        currentUser.setValue(null);
    }
}
