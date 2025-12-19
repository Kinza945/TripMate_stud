package com.kynzai.tripmate_stud.domain.model;

public class UserProfile {
    private final String uid;
    private final String email;
    private final String displayName;

    public UserProfile(String uid, String email, String displayName) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }
}
