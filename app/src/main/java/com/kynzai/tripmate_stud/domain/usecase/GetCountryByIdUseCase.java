package com.kynzai.tripmate_stud.domain.usecase;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

public class GetCountryByIdUseCase {
    private final CountryRepository repository;

    public GetCountryByIdUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public LiveData<Country> execute(String id) {
        return repository.getCountryById(id);
    }
}
