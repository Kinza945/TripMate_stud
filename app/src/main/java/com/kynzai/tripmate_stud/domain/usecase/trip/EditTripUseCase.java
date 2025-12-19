package com.kynzai.tripmate_stud.domain.usecase.trip;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class EditTripUseCase {
    private final TripRepository repository;

    public EditTripUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public void execute(Trip trip) {
        repository.editTrip(trip);
    }
}
