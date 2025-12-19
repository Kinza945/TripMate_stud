package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;

import java.util.HashMap;
import java.util.Map;

public class ProfileRepositoryImpl implements ProfileRepository {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public LiveData<UserProfile> getUserById(String uid) {
        MutableLiveData<UserProfile> result = new MutableLiveData<>();
        firestore.collection("users")
                .document(uid)
                .addSnapshotListener((snapshot, error) -> {
                    if (snapshot != null && snapshot.exists()) {
                        String email = snapshot.getString("email");
                        String displayName = snapshot.getString("displayName");
                        result.postValue(new UserProfile(email == null ? "" : email,
                                displayName == null ? "Пользователь" : displayName));
                    }
                });
        return result;
    }

    @Override
    public void createUserProfile(String uid, String email, String displayName) {
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("displayName", displayName == null || displayName.isEmpty() ? "Пользователь" : displayName);
        data.put("createdAt", FieldValue.serverTimestamp());
        firestore.collection("users").document(uid).set(data);
    }
}
