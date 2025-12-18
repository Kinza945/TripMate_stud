package com.kynzai.tripmate_stud.domain.repository;

import androidx.lifecycle.LiveData;

import com.kynzai.tripmate_stud.domain.model.Trip;

import java.util.List;

public interface TripRepository {
    LiveData<List<Trip>> getTrips();
    LiveData<List<Trip>> getFavoriteTrips();
    void addTrip(Trip trip);
    void toggleFavorite(String id);
    void removeTrip(String id);
}
