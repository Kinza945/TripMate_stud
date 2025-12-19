package com.kynzai.tripmate_stud.domain.usecase.country;

import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

public class ToggleFavoriteCountryUseCase {
    private final CountryRepository repository;

    public ToggleFavoriteCountryUseCase(CountryRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.toggleFavoriteCountry(id);
    }
}
