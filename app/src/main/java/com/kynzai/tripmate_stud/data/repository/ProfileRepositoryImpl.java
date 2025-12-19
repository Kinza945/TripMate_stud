package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;

public class ProfileRepositoryImpl implements ProfileRepository {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ListenerRegistration listenerRegistration;

    @Override
    public LiveData<UserProfile> getUserById(String uid) {
        MutableLiveData<UserProfile> result = new MutableLiveData<>();
        if (uid == null || uid.isEmpty()) {
            result.setValue(null);
            return result;
        }
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
        listenerRegistration = firestore.collection("users")
                .document(uid)
                .addSnapshotListener((snapshot, error) -> {
                    if (snapshot == null || error != null) {
                        return;
                    }
                    result.postValue(mapProfile(uid, snapshot));
                });
        return result;
    }

    @Override
    public void createUserProfile(String uid, UserProfile profile) {
        if (uid == null || uid.isEmpty() || profile == null) {
            return;
        }
        java.util.Map<String, Object> data = new java.util.HashMap<>();
        data.put("displayName", profile.getDisplayName());
        data.put("email", profile.getEmail());
        data.put("createdAt", com.google.firebase.firestore.FieldValue.serverTimestamp());
        firestore.collection("users")
                .document(uid)
                .set(data);
    }

    private UserProfile mapProfile(String uid, DocumentSnapshot snapshot) {
        String displayName = snapshot.getString("displayName");
        String email = snapshot.getString("email");
        return new UserProfile(uid, email == null ? "" : email, displayName == null ? "" : displayName);
    }

    
}
