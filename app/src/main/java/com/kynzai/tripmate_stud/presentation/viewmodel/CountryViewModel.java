package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.data.repository.CountryRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

import java.util.List;

public class CountryViewModel extends ViewModel {
    private final CountryRepository repository = new CountryRepositoryImpl();

    public LiveData<List<Country>> getCountries() {
        return repository.getCountries();
    }

    public LiveData<CurrencyInfo> getCurrencyInfo() {
        return repository.getCurrencyInfo();
    }
}
