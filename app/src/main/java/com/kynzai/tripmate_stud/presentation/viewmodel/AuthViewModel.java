package com.kynzai.tripmate_stud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.repository.ProfileRepositoryImpl;
import com.kynzai.tripmate_stud.data.repository.UserRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.UserProfile;
import com.kynzai.tripmate_stud.domain.repository.ProfileRepository;
import com.kynzai.tripmate_stud.domain.repository.UserRepository;
import com.kynzai.tripmate_stud.domain.usecase.GetCurrentUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetUserByIdUseCase;
import com.kynzai.tripmate_stud.domain.usecase.LoginUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.LogoutUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.RegisterUserUseCase;

public class AuthViewModel extends AndroidViewModel {

    private final UserRepository repository;
    private final ProfileRepository profileRepository;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final LogoutUserUseCase logoutUserUseCase;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepositoryImpl();
        repository = new UserRepositoryImpl(profileRepository);
        getCurrentUserUseCase = new GetCurrentUserUseCase(repository);
        getUserByIdUseCase = new GetUserByIdUseCase(profileRepository);
        loginUserUseCase = new LoginUserUseCase(repository);
        registerUserUseCase = new RegisterUserUseCase(repository);
        logoutUserUseCase = new LogoutUserUseCase(repository);
    }

    public LiveData<UserProfile> getCurrentUser() {
        return getCurrentUserUseCase.execute();
    }

    public LiveData<UserProfile> getUserById(String uid) {
        return getUserByIdUseCase.execute(uid);
    }

    public LiveData<String> getAuthMessage() {
        return repository.getAuthMessage();
    }

    public LiveData<String> getAuthError() {
        return repository.getAuthError();
    }

    public void login(String email, String password) {
        loginUserUseCase.execute(email, password);
    }

    public void register(String email, String password) {
        registerUserUseCase.execute(email, password);
    }

    public void logout() {
        logoutUserUseCase.execute();
    }

    public void clearAuthMessage() {
        repository.clearAuthMessage();
    }

    public void clearAuthError() {
        repository.clearAuthError();
    }
}
