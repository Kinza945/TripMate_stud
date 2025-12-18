package com.kynzai.tripmate_stud.domain.model;

public class UserSession {
    private final String email;
    private final boolean authenticated;

    public UserSession(String email, boolean authenticated) {
        this.email = email;
        this.authenticated = authenticated;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
