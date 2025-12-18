package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.kynzai.tripmate_stud.data.local.FakeFirestoreDataSource;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

import java.util.ArrayList;
import java.util.List;

public class TripRepositoryImpl implements TripRepository {
    private final FakeFirestoreDataSource firestoreDataSource;

    public TripRepositoryImpl(FakeFirestoreDataSource firestoreDataSource) {
        this.firestoreDataSource = firestoreDataSource;
    }

    @Override
    public LiveData<List<Trip>> getTrips() {
        return firestoreDataSource.observeTrips();
    }

    @Override
    public LiveData<List<Trip>> getFavoriteTrips() {
        return Transformations.map(firestoreDataSource.observeTrips(), trips -> {
            List<Trip> favorites = new ArrayList<>();
            if (trips != null) {
                for (Trip trip : trips) {
                    if (trip.isFavorite()) {
                        favorites.add(trip);
                    }
                }
            }
            return favorites;
        });
    }

    @Override
    public void addTrip(Trip trip) {
        firestoreDataSource.addTrip(trip.getTitle(), trip.getCountry(), trip.getDescription(), trip.getDate(), trip.isMine());
    }

    @Override
    public void toggleFavorite(String id) {
        firestoreDataSource.toggleTripFavorite(id);
    }

    @Override
    public Trip getTripById(String id) {
        return firestoreDataSource.findTrip(id);
    }
}
