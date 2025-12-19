package com.kynzai.tripmate_stud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;
import com.kynzai.tripmate_stud.data.repository.TripRepositoryImpl;
import com.kynzai.tripmate_stud.domain.usecase.trip.AddTripUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.EditTripUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.GetFavoriteTripsUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.GetTripByIdUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.GetTripsUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.RemoveTripUseCase;
import com.kynzai.tripmate_stud.domain.usecase.trip.ToggleFavoriteUseCase;

import java.util.List;

public class TripViewModel extends AndroidViewModel {

    private final GetTripsUseCase getTripsUseCase;
    private final GetFavoriteTripsUseCase getFavoriteTripsUseCase;
    private final GetTripByIdUseCase getTripByIdUseCase;
    private final AddTripUseCase addTripUseCase;
    private final EditTripUseCase editTripUseCase;
    private final ToggleFavoriteUseCase toggleFavoriteUseCase;
    private final RemoveTripUseCase removeTripUseCase;

    public TripViewModel(@NonNull Application application) {
        super(application);
        TripRepository repository = new TripRepositoryImpl();
        getTripsUseCase = new GetTripsUseCase(repository);
        getFavoriteTripsUseCase = new GetFavoriteTripsUseCase(repository);
        getTripByIdUseCase = new GetTripByIdUseCase(repository);
        addTripUseCase = new AddTripUseCase(repository);
        editTripUseCase = new EditTripUseCase(repository);
        toggleFavoriteUseCase = new ToggleFavoriteUseCase(repository);
        removeTripUseCase = new RemoveTripUseCase(repository);
    }

    public LiveData<List<Trip>> getTrips() {
        return getTripsUseCase.execute();
    }

    public LiveData<List<Trip>> getFavoriteTrips() {
        return getFavoriteTripsUseCase.execute();
    }

    public LiveData<Trip> getTripById(String id) {
        return getTripByIdUseCase.execute(id);
    }

    public void addTrip(Trip trip) {
        addTripUseCase.execute(trip);
    }

    public void editTrip(Trip trip) {
        editTripUseCase.execute(trip);
    }

    public void toggleFavorite(String id) {
        toggleFavoriteUseCase.execute(id);
    }

    public void removeTrip(String id) {
        removeTripUseCase.execute(id);
    }
}
