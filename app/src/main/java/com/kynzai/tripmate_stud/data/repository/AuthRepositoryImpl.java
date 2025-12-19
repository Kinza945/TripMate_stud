package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.AuthRepository;

import java.util.HashMap;
import java.util.Map;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final MutableLiveData<UserProfile> currentUser = new MutableLiveData<>();
    private final MutableLiveData<String> authMessage = new MutableLiveData<>();
    private final MutableLiveData<String> authError = new MutableLiveData<>();
    private final FirebaseAuth.AuthStateListener authStateListener;

    public AuthRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            currentUser.postValue(mapUser(user));
            if (user != null) {
                ensureUserDocument(user);
            }
        };
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
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            ensureUserDocument(user);
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
            displayName = "Пользователь";
        }
        return new UserProfile(email, displayName);
    }

    private void ensureUserDocument(FirebaseUser user) {
        String uid = user.getUid();
        firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        return;
                    }
                    Map<String, Object> data = new HashMap<>();
                    String email = user.getEmail() == null ? "" : user.getEmail();
                    String displayName = user.getDisplayName();
                    if (displayName == null || displayName.isEmpty()) {
                        displayName = "Пользователь";
                    }
                    data.put("displayName", displayName);
                    data.put("email", email);
                    data.put("createdAt", FieldValue.serverTimestamp());
                    firestore.collection("users")
                            .document(uid)
                            .set(data);
                });
    }

    private String resolveError(Exception exception) {
        if (exception == null || exception.getMessage() == null || exception.getMessage().isEmpty()) {
            return "Ошибка авторизации";
        }
        return exception.getMessage();
    }
}
