package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.local.FakeFirestoreDataSource;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

import java.util.List;

public class CountryRepositoryImpl implements CountryRepository {
    private final FakeFirestoreDataSource firestoreDataSource;

    public CountryRepositoryImpl(FakeFirestoreDataSource firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public LiveData<List<Country>> getCountries() {
        return firestoreDataSource.observeCountries();
    }

    @Override
    public void toggleFavorite(String id) {
        firestoreDataSource.toggleCountryFavorite(id);
    }

    @Override
    public Country getCountryById(String id) {
        return firestoreDataSource.findCountry(id);
    }
}
