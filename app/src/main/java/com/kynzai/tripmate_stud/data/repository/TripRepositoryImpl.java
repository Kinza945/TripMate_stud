package com.kynzai.tripmate_stud.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.data.local.LocalTripStorage;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

import java.util.ArrayList;
import java.util.List;

public class TripRepositoryImpl implements TripRepository {

    private final LocalTripStorage storage;
    private final MutableLiveData<List<Trip>> trips = new MutableLiveData<>();
    private final MutableLiveData<List<Trip>> favorites = new MutableLiveData<>();

    public TripRepositoryImpl(Context context) {
        storage = new LocalTripStorage(context.getApplicationContext());
        List<Trip> saved = storage.getTrips();
        if (saved.isEmpty()) {
            saved = createDefaults();
            storage.saveTrips(saved);
        }
        trips.setValue(saved);
        favorites.setValue(filterFavorites(saved));
    }

    private List<Trip> createDefaults() {
        List<Trip> defaults = new ArrayList<>();
        defaults.add(new Trip(null, "Любовь всегда в Исландии", "Поездка по водопадам и геотермальным источникам.", "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee", "Рейкьявик", true));
        defaults.add(new Trip(null, "Венеция", "Каналы, гондолы и история на каждом шагу.", "https://images.unsplash.com/photo-1505761671935-60b3a7427bad", "Италия", false));
        defaults.add(new Trip(null, "Киото", "Старинные храмы, чайные церемонии и сакура.", "https://images.unsplash.com/photo-1505060055475-0858d9047f3f", "Япония", false));
        return defaults;
    }

    private List<Trip> filterFavorites(List<Trip> source) {
        List<Trip> favs = new ArrayList<>();
        for (Trip trip : source) {
            if (trip.isFavorite()) {
                favs.add(trip);
            }
        }
        return favs;
    }

    private void updateAndPersist(List<Trip> updated) {
        trips.setValue(updated);
        favorites.setValue(filterFavorites(updated));
        storage.saveTrips(updated);
    }

    @Override
    public LiveData<List<Trip>> getTrips() {
        return trips;
    }

    @Override
    public LiveData<List<Trip>> getFavoriteTrips() {
        return favorites;
    }

    @Override
    public void addTrip(Trip trip) {
        List<Trip> current = trips.getValue();
        if (current == null) current = new ArrayList<>();
        current.add(trip);
        updateAndPersist(current);
    }

    @Override
    public void toggleFavorite(String id) {
        List<Trip> current = trips.getValue();
        if (current == null) return;
        List<Trip> updated = new ArrayList<>();
        for (Trip t : current) {
            if (t.getId().equals(id)) {
                updated.add(t.toggleFavorite());
            } else {
                updated.add(t);
            }
        }
        updateAndPersist(updated);
    }

    @Override
    public void removeTrip(String id) {
        List<Trip> current = trips.getValue();
        if (current == null) return;
        List<Trip> updated = new ArrayList<>();
        for (Trip t : current) {
            if (!t.getId().equals(id)) {
                updated.add(t);
            }
        }
        updateAndPersist(updated);
    }
}
