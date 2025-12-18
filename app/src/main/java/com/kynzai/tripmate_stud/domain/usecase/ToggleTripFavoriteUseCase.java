package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class ToggleTripFavoriteUseCase {
    private final TripRepository repository;

    public ToggleTripFavoriteUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.toggleFavorite(id);
    }
}
