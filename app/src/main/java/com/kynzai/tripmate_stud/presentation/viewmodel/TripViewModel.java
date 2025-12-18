package com.kynzai.tripmate_stud.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.usecase.AddTripUseCase;
import com.kynzai.tripmate_stud.domain.usecase.GetTripsUseCase;
import com.kynzai.tripmate_stud.domain.usecase.ToggleTripFavoriteUseCase;

import java.util.List;

public class TripViewModel extends ViewModel {
    private final GetTripsUseCase getTripsUseCase;
    private final ToggleTripFavoriteUseCase toggleTripFavoriteUseCase;
    private final AddTripUseCase addTripUseCase;

    public TripViewModel(GetTripsUseCase getTripsUseCase, ToggleTripFavoriteUseCase toggleTripFavoriteUseCase, AddTripUseCase addTripUseCase) {
        this.getTripsUseCase = getTripsUseCase;
        this.toggleTripFavoriteUseCase = toggleTripFavoriteUseCase;
        this.addTripUseCase = addTripUseCase;
    }

    public LiveData<List<Trip>> getTrips() {
        return getTripsUseCase.execute();
    }

    public void toggleFavorite(String id) {
        toggleTripFavoriteUseCase.execute(id);
    }

    public void addTrip(Trip trip) {
        addTripUseCase.execute(trip);
    }
}
