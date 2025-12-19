package com.kynzai.tripmate_stud.domain.usecase.country;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

import java.util.List;

public class GetCountriesUseCase {
    private final CountryRepository repository;

    public GetCountriesUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Country>> execute() {
        return repository.getCountries();
    }
}
