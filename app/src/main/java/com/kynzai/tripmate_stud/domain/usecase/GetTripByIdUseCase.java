package com.kynzai.tripmate_stud.domain.usecase;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

public class GetTripByIdUseCase {
    private final TripRepository repository;

    public GetTripByIdUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public LiveData<Trip> execute(String id) {
        return repository.getTripById(id);
    }
}
