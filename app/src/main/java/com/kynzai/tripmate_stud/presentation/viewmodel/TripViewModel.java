package com.kynzai.tripmate_stud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.data.repository.TripRepositoryImpl;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

import java.util.List;

public class TripViewModel extends AndroidViewModel {

    private final TripRepository repository;

    public TripViewModel(@NonNull Application application) {
        super(application);
        repository = new TripRepositoryImpl(application.getApplicationContext());
    }

    public LiveData<List<Trip>> getTrips() {
        return repository.getTrips();
    }

    public LiveData<List<Trip>> getFavoriteTrips() {
        return repository.getFavoriteTrips();
    }

    public void addTrip(Trip trip) {
        repository.addTrip(trip);
    }

    public void toggleFavorite(String id) {
        repository.toggleFavorite(id);
    }

    public void removeTrip(String id) {
        repository.removeTrip(id);
    }
}
