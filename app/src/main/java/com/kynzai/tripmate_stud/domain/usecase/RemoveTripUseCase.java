package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class RemoveTripUseCase {
    private final TripRepository repository;

    public RemoveTripUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        repository.removeTrip(id);
    }
}
