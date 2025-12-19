package com.kynzai.tripmate_stud.domain.usecase.trip;

import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class ToggleFavoriteUseCase {
    private final TripRepository repository;

    public ToggleFavoriteUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.toggleFavorite(id);
    }
}
