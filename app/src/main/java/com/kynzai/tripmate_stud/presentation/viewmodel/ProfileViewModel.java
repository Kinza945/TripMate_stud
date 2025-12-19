package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;

import com.kynzai.tripmate_stud.data.repository.ProfileRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;
import com.kynzai.tripmate_stud.domain.usecase.profile.GetUserByIdUseCase;

public class ProfileViewModel extends AndroidViewModel {
    private final GetUserByIdUseCase getUserByIdUseCase;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        ProfileRepository repository = new ProfileRepositoryImpl();
        getUserByIdUseCase = new GetUserByIdUseCase(repository);
    }

    public LiveData<UserProfile> getUserById(String uid) {
        return getUserByIdUseCase.execute(uid);
    }
}
