package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.RemoteInfo;

public interface RemoteRepository {
    LiveData<RemoteInfo> getRemoteInfo();
}
