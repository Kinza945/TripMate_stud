package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.network.RemoteInfoDataSource;
import com.kynzai.tripmate_stud.domain.model.RemoteInfo;
import com.kynzai.tripmate_stud.domain.repository.RemoteRepository;

public class RemoteRepositoryImpl implements RemoteRepository {
    private final RemoteInfoDataSource dataSource;

    public RemoteRepositoryImpl(RemoteInfoDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public LiveData<RemoteInfo> getRemoteInfo() {
        return dataSource.observeInfo();
    }
}
