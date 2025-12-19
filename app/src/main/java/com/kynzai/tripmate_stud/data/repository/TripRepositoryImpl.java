package com.kynzai.tripmate_stud.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.data.remote.MockApiService;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.domain.repository.TripRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripRepositoryImpl implements TripRepository {

    private static final String MOCK_BASE_URL = "https://694461477dd335f4c3602a64.mockapi.io/TripMate_studapi/";

    private final MutableLiveData<List<Trip>> trips = new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<List<Trip>> favorites = new MutableLiveData<>(Collections.emptyList());
    private final MockApiService apiService;
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final List<Trip> baseTrips = new ArrayList<>();
    private final List<Trip> userTrips = new ArrayList<>();
    private final Set<String> favoriteIds = new HashSet<>();
    private ListenerRegistration tripsRegistration;
    private ListenerRegistration favoritesRegistration;
    private final FirebaseAuth.AuthStateListener authStateListener;

    public TripRepositoryImpl() {
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
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        authStateListener = firebaseAuth -> handleAuthState(firebaseAuth.getCurrentUser() != null);
        auth.addAuthStateListener(authStateListener);

        fetchTrips();
        handleAuthState(auth.getCurrentUser() != null);
    }

    private void handleAuthState(boolean signedIn) {
        clearListeners();
        if (!signedIn) {
            userTrips.clear();
            favoriteIds.clear();
            updateCombinedTrips();
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        tripsRegistration = firestore.collection("trips")
                .whereEqualTo("ownerUid", uid)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshot, error) -> {
                    if (snapshot == null || error != null) {
                        return;
                    }
                    userTrips.clear();
                    snapshot.getDocuments().forEach(doc -> {
                        String id = doc.getId();
                        String title = safe(doc.getString("title"));
                        String description = safe(doc.getString("description"));
                        String imageUrl = safe(doc.getString("imageUrl"));
                        String location = safe(doc.getString("location"));
                        boolean favorite = favoriteIds.contains(id);
                        userTrips.add(new Trip(id, title, description, imageUrl, location, favorite));
                    });
                    updateCombinedTrips();
                });
        favoritesRegistration = firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .addSnapshotListener((snapshot, error) -> {
                    if (snapshot == null || error != null) {
                        return;
                    }
                    favoriteIds.clear();
                    snapshot.getDocuments().forEach(doc -> favoriteIds.add(doc.getId()));
                    updateCombinedTrips();
                });
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
    public LiveData<Trip> getTripById(String id) {
        MutableLiveData<Trip> result = new MutableLiveData<>();
        trips.observeForever(list -> {
            if (list == null) {
                return;
            }
            for (Trip trip : list) {
                if (trip.getId().equals(id)) {
                    result.postValue(trip);
                    return;
                }
            }
        });
        return result;
    }

    @Override
    public void addTrip(Trip trip) {
        if (auth.getCurrentUser() == null) {
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", trip.getTitle());
        data.put("description", trip.getDescription());
        data.put("imageUrl", trip.getImageUrl());
        data.put("location", trip.getLocation());
        data.put("ownerUid", uid);
        data.put("createdAt", FieldValue.serverTimestamp());
        firestore.collection("trips")
                .document(trip.getId())
                .set(data);
    }

    @Override
    public void editTrip(Trip trip) {
        if (auth.getCurrentUser() == null) {
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", trip.getTitle());
        data.put("description", trip.getDescription());
        data.put("imageUrl", trip.getImageUrl());
        data.put("location", trip.getLocation());
        data.put("ownerUid", uid);
        firestore.collection("trips")
                .document(trip.getId())
                .update(data);
    }

    @Override
    public void toggleFavorite(String id) {
        if (auth.getCurrentUser() == null) {
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        if (favoriteIds.contains(id)) {
            firestore.collection("users")
                    .document(uid)
                    .collection("favorites")
                    .document(id)
                    .delete();
        } else {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("createdAt", FieldValue.serverTimestamp());
            firestore.collection("users")
                    .document(uid)
                    .collection("favorites")
                    .document(id)
                    .set(data);
        }
    }

    @Override
    public void removeTrip(String id) {
        if (auth.getCurrentUser() == null) {
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        firestore.collection("trips")
                .document(id)
                .delete();
        firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .document(id)
                .delete();
    }

    private void fetchTrips() {
        apiService.getTrips().enqueue(new Callback<List<MockApiService.TripResponse>>() {
            @Override
            public void onResponse(Call<List<MockApiService.TripResponse>> call, Response<List<MockApiService.TripResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Trip> mapped = new ArrayList<>();
                    for (MockApiService.TripResponse item : response.body()) {
                        String title = safe(item.name);
                        String location = safe(item.countryName);
                        boolean isFavorite = favoriteIds.contains(item.id);
                        mapped.add(new Trip(item.id, title, safe(item.description), safe(item.imageUrl), location, isFavorite));
                    }
                    baseTrips.clear();
                    baseTrips.addAll(mapped);
                    updateCombinedTrips();
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

    private void updateCombinedTrips() {
        Map<String, Trip> merged = new LinkedHashMap<>();
        for (Trip trip : baseTrips) {
            merged.put(trip.getId(), applyFavorite(trip));
        }
        for (Trip trip : userTrips) {
            merged.put(trip.getId(), applyFavorite(trip));
        }
        List<Trip> combined = new ArrayList<>(merged.values());
        trips.postValue(combined);
        favorites.postValue(filterFavorites(combined));
    }

    private Trip applyFavorite(Trip trip) {
        if (favoriteIds.contains(trip.getId())) {
            return trip.isFavorite() ? trip : trip.toggleFavorite();
        }
        return trip.isFavorite() ? trip.toggleFavorite() : trip;
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

    private void clearListeners() {
        if (tripsRegistration != null) {
            tripsRegistration.remove();
            tripsRegistration = null;
        }
        if (favoritesRegistration != null) {
            favoritesRegistration.remove();
            favoritesRegistration = null;
        }
    }
}
