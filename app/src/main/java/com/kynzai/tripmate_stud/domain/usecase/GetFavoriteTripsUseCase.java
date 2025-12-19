package com.kynzai.tripmate_stud.domain.usecase;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

import java.util.List;

public class GetFavoriteTripsUseCase {
    private final TripRepository repository;

    public GetFavoriteTripsUseCase(TripRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Trip>> execute() {
        return repository.getFavoriteTrips();
    }
}
