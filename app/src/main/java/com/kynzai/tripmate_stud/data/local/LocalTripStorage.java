package com.kynzai.tripmate_stud.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kynzai.tripmate_stud.domain.model.Trip;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocalTripStorage {
    private static final String PREF = "trip_storage";
    private static final String KEY_TRIPS = "trips";
    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public LocalTripStorage(Context context) {
        preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public List<Trip> getTrips() {
        String json = preferences.getString(KEY_TRIPS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Trip>>() {}.getType();
        List<Trip> saved = gson.fromJson(json, type);
        return saved == null ? new ArrayList<>() : saved;
    }

    public void saveTrips(List<Trip> trips) {
        preferences.edit().putString(KEY_TRIPS, gson.toJson(trips)).apply();
    }
}
