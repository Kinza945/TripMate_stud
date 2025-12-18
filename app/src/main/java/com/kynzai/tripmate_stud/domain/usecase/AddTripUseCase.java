package com.kynzai.tripmate_stud.domain.usecase;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class AddTripUseCase {
    private final TripRepository repository;

    public AddTripUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public void execute(Trip trip) {
        repository.addTrip(trip);
    }
}
