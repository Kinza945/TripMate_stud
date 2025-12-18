package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Country;

import java.util.List;

public interface CountryRepository {
    LiveData<List<Country>> getCountries();

    void toggleFavorite(String id);

    Country getCountryById(String id);
}
