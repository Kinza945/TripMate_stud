package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;
import com.kynzai.tripmate_stud.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private final FirebaseAuth auth;
    private final ProfileRepository profileRepository;
    private final MutableLiveData<UserProfile> currentUser = new MutableLiveData<>();
    private final MutableLiveData<String> authMessage = new MutableLiveData<>();
    private final MutableLiveData<String> authError = new MutableLiveData<>();
    private final FirebaseAuth.AuthStateListener authStateListener;

    public UserRepositoryImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
    public LiveData<String> getAuthMessage() {
        return authMessage;
    }

    @Override
    public LiveData<String> getAuthError() {
        return authError;
    }

    @Override
    public void register(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            UserProfile profile = mapUser(firebaseUser);
                            profileRepository.createUserProfile(firebaseUser.getUid(), profile);
                        }
                        authMessage.postValue("Регистрация успешна");
                    } else {
                        authError.postValue(resolveError(task.getException()));
                    }
                });
    }

    @Override
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        authMessage.postValue("Вход выполнен");
                    } else {
                        authError.postValue(resolveError(task.getException()));
                    }
                });
    }

    @Override
    public void logout() {
        auth.signOut();
        authMessage.postValue("Выход выполнен");
    }

    @Override
    public void clearAuthMessage() {
        authMessage.postValue(null);
    }

    @Override
    public void clearAuthError() {
        authError.postValue(null);
    }

    private UserProfile mapUser(FirebaseUser user) {
        if (user == null) {
            return null;
        }
        String email = user.getEmail() == null ? "" : user.getEmail();
        String displayName = user.getDisplayName();
        if (displayName == null || displayName.isEmpty()) {
            displayName = deriveDisplayName(email);
        }
        return new UserProfile(user.getUid(), email, displayName);
    }

    private String resolveError(Exception exception) {
        if (exception == null || exception.getMessage() == null || exception.getMessage().isEmpty()) {
            return "Ошибка авторизации";
        }
        return exception.getMessage();
    }

    private String deriveDisplayName(String email) {
        if (email == null || !email.contains("@")) {
            return "Пользователь";
        }
        return email.substring(0, email.indexOf('@'));
    }
}
