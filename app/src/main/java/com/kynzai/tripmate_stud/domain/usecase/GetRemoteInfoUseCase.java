package com.kynzai.tripmate_stud.domain.usecase;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.RemoteInfo;
import com.kynzai.tripmate_stud.domain.repository.RemoteRepository;

public class GetRemoteInfoUseCase {
    private final RemoteRepository repository;

    public GetRemoteInfoUseCase(RemoteRepository repository) {
        this.repository = repository;
    }

    public LiveData<RemoteInfo> execute() {
        return repository.getRemoteInfo();
    }
}
