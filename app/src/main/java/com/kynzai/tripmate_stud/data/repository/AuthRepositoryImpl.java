package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth auth;
    private final MutableLiveData<UserProfile> currentUser = new MutableLiveData<>();
    private final FirebaseAuth.AuthStateListener authStateListener;

    public AuthRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> currentUser.postValue(mapUser(firebaseAuth.getCurrentUser()));
        auth.addAuthStateListener(authStateListener);
        currentUser.setValue(mapUser(auth.getCurrentUser()));
    }

    @Override
    public LiveData<UserProfile> getCurrentUser() {
        return currentUser;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void logout() {
        auth.signOut();
    }

    private UserProfile mapUser(FirebaseUser user) {
        if (user == null) {
            return null;
        }
        String email = user.getEmail() == null ? "" : user.getEmail();
        String displayName = user.getDisplayName();
        if (displayName == null || displayName.isEmpty()) {
            displayName = "Пользователь";
        }
        return new UserProfile(email, displayName);
    }
}
