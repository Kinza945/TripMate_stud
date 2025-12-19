package com.kynzai.tripmate_stud.domain.usecase.country;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

import java.util.Set;

public class GetFavoriteCountryIdsUseCase {
    private final CountryRepository repository;

    public GetFavoriteCountryIdsUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public LiveData<Set<String>> execute() {
        return repository.getFavoriteCountryIds();
    }
}
