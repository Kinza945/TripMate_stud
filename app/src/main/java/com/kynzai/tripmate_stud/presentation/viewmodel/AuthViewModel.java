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
import com.kynzai.tripmate_stud.domain.usecase.auth.ClearAuthErrorUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.ClearAuthMessageUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.GetAuthErrorUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.GetAuthMessageUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.GetCurrentUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.LoginUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.LogoutUserUseCase;
import com.kynzai.tripmate_stud.domain.usecase.auth.RegisterUserUseCase;

public class AuthViewModel extends AndroidViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetAuthMessageUseCase getAuthMessageUseCase;
    private final GetAuthErrorUseCase getAuthErrorUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final ClearAuthMessageUseCase clearAuthMessageUseCase;
    private final ClearAuthErrorUseCase clearAuthErrorUseCase;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        ProfileRepository profileRepository = new ProfileRepositoryImpl();
        UserRepository repository = new UserRepositoryImpl(profileRepository);
        getCurrentUserUseCase = new GetCurrentUserUseCase(repository);
        getAuthMessageUseCase = new GetAuthMessageUseCase(repository);
        getAuthErrorUseCase = new GetAuthErrorUseCase(repository);
        loginUserUseCase = new LoginUserUseCase(repository);
        registerUserUseCase = new RegisterUserUseCase(repository);
        logoutUserUseCase = new LogoutUserUseCase(repository);
        clearAuthMessageUseCase = new ClearAuthMessageUseCase(repository);
        clearAuthErrorUseCase = new ClearAuthErrorUseCase(repository);
    }

    public LiveData<UserProfile> getCurrentUser() {
        return getCurrentUserUseCase.execute();
    }

    public LiveData<String> getAuthMessage() {
        return getAuthMessageUseCase.execute();
    }

    public LiveData<String> getAuthError() {
        return getAuthErrorUseCase.execute();
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
        clearAuthMessageUseCase.execute();
    }

    public void clearAuthError() {
        clearAuthErrorUseCase.execute();
    }
}
