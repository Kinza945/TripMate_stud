package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;
import com.kynzai.tripmate_stud.data.repository.CountryRepositoryImpl;
import com.kynzai.tripmate_stud.domain.usecase.country.GetCountriesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.country.GetCountryByIdUseCase;
import com.kynzai.tripmate_stud.domain.usecase.country.GetCurrencyInfoUseCase;

import java.util.List;

public class CountryViewModel extends ViewModel {
    private final GetCountriesUseCase getCountriesUseCase;
    private final GetCountryByIdUseCase getCountryByIdUseCase;
    private final GetCurrencyInfoUseCase getCurrencyInfoUseCase;

    public CountryViewModel() {
        CountryRepository repository = new CountryRepositoryImpl();
        getCountriesUseCase = new GetCountriesUseCase(repository);
        getCountryByIdUseCase = new GetCountryByIdUseCase(repository);
        getCurrencyInfoUseCase = new GetCurrencyInfoUseCase(repository);
    }

    public LiveData<List<Country>> getCountries() {
        return getCountriesUseCase.execute();
    }

    public LiveData<Country> getCountryById(String id) {
        return getCountryByIdUseCase.execute(id);
    }

    public LiveData<CurrencyInfo> getCurrencyInfo() {
        return getCurrencyInfoUseCase.execute();
    }
}
