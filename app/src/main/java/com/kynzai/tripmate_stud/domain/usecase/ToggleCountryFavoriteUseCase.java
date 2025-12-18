package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

public class ToggleCountryFavoriteUseCase {
    private final CountryRepository repository;

    public ToggleCountryFavoriteUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.toggleFavorite(id);
    }
}
