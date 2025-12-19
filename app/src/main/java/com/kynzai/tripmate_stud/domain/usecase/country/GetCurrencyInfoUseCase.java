package com.kynzai.tripmate_stud.domain.usecase.country;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

public class GetCurrencyInfoUseCase {
    private final CountryRepository repository;

    public GetCurrencyInfoUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public LiveData<CurrencyInfo> execute() {
        return repository.getCurrencyInfo();
    }
}
