package com.kynzai.tripmate_stud.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.data.local.LocalTripStorage;
import com.kynzai.tripmate_stud.data.remote.MockApiService;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRepositoryImpl implements TripRepository {

    private static final String MOCK_BASE_URL = "https://694461477dd335f4c3602a64.mockapi.io/TripMate_studapi/";

    private final LocalTripStorage storage;
    private final MutableLiveData<List<Trip>> trips = new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<List<Trip>> favorites = new MutableLiveData<>(Collections.emptyList());
    private final MockApiService apiService;

    public TripRepositoryImpl(Context context) {
        storage = new LocalTripStorage(context.getApplicationContext());
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOCK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(MockApiService.class);

        List<Trip> saved = storage.getTrips();
        if (saved == null) {
            saved = new ArrayList<>();
        }
        trips.setValue(saved);
        favorites.setValue(filterFavorites(saved));
        fetchTrips();
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
            if (t.getId() != null && t.getId().equals(id)) {
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
            if (t.getId() == null || !t.getId().equals(id)) {
                updated.add(t);
            }
        }
        updateAndPersist(updated);
    }

    private void fetchTrips() {
        apiService.getTrips().enqueue(new Callback<List<MockApiService.TripResponse>>() {
            @Override
            public void onResponse(Call<List<MockApiService.TripResponse>> call, Response<List<MockApiService.TripResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Trip> currentTrips = trips.getValue();
                    List<String> favoriteIds = new ArrayList<>();
                    if (currentTrips != null) {
                        for (Trip trip : currentTrips) {
                            if (trip.isFavorite()) {
                                favoriteIds.add(trip.getId());
                            }
                        }
                    }
                    List<Trip> mapped = new ArrayList<>();
                    List<String> remoteIds = new ArrayList<>();
                    for (MockApiService.TripResponse item : response.body()) {
                        String title = firstNonEmpty(item.title, item.name);
                        String location = firstNonEmpty(item.location, item.capital);
                        boolean isFavorite = favoriteIds.contains(item.id);
                        mapped.add(new Trip(item.id, title, safe(item.description), safe(item.imageUrl), location, isFavorite));
                        remoteIds.add(item.id);
                    }

                    if (currentTrips != null) {
                        for (Trip trip : currentTrips) {
                            if (trip.getId() != null && !remoteIds.contains(trip.getId())) {
                                mapped.add(trip);
                            }
                        }
                    }
                    updateAndPersist(mapped);
                }
            }

            @Override
            public void onFailure(Call<List<MockApiService.TripResponse>> call, Throwable t) {
                // Keep cached trips when offline
            }
        });
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String firstNonEmpty(String primary, String fallback) {
        if (primary != null && !primary.isEmpty()) {
            return primary;
        }
        return fallback == null ? "" : fallback;
    }
}
