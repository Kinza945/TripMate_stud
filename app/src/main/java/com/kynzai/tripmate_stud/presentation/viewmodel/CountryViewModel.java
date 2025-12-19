package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.data.repository.CountryRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;
import com.kynzai.tripmate_stud.domain.usecase.GetCountriesUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetCountryByIdUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetCurrencyInfoUseCase;

import java.util.List;

public class CountryViewModel extends ViewModel {
    private final CountryRepository repository = new CountryRepositoryImpl();
    private final GetCountriesUseCase getCountriesUseCase = new GetCountriesUseCase(repository);
    private final GetCurrencyInfoUseCase getCurrencyInfoUseCase = new GetCurrencyInfoUseCase(repository);
    private final GetCountryByIdUseCase getCountryByIdUseCase = new GetCountryByIdUseCase(repository);

    public LiveData<List<Country>> getCountries() {
        return getCountriesUseCase.execute();
    }

    public LiveData<CurrencyInfo> getCurrencyInfo() {
        return getCurrencyInfoUseCase.execute();
    }

    public LiveData<Country> getCountryById(String id) {
        return getCountryByIdUseCase.execute(id);
    }
}
