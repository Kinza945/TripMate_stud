package com.kynzai.tripmate_stud.domain.model;

public class UserProfile {
    private final String email;
    private final String displayName;

    public UserProfile(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}
