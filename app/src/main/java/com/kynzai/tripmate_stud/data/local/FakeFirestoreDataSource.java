package com.kynzai.tripmate_stud.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FakeFirestoreDataSource {
    private final MutableLiveData<List<Country>> countries = new MutableLiveData<>();
    private final MutableLiveData<List<Trip>> trips = new MutableLiveData<>();

    public FakeFirestoreDataSource() {
        seedCountries();
        seedTrips();
    }

    private void seedCountries() {
        List<Country> defaults = new ArrayList<>();
        defaults.add(new Country("ru", "Россия", "Большая страна с разнообразной природой и культурой.", "Москва", 4.5,
                Arrays.asList("Эрмитаж", "Красная площадь", "Куршская коса"), "country_russia", false));
        defaults.add(new Country("es", "Испания", "Средиземноморское солнце, тапас и пляжи.", "Мадрид", 4.7,
                Arrays.asList("Саграда Фамилия", "Пляжи Валенсии", "Гранада"), "country_spain", true));
        defaults.add(new Country("fr", "Франция", "Лувр, Париж и бесконечные сыры.", "Париж", 4.6,
                Arrays.asList("Эйфелева башня", "Лазурный берег", "Бургундия"), "country_france", false));
        countries.setValue(defaults);
    }

    private void seedTrips() {
        List<Trip> defaults = new ArrayList<>();
        defaults.add(new Trip("trip1", "Любовь сказка в Ирландии", "Ирландия", "Замки, зеленые холмы и уютные пабами.", "05.05.2025", 4.8, true, true));
        defaults.add(new Trip("trip2", "Большое путешествие по России", "Россия", "Поезд по Транссибу и природа Байкала.", "11.06.2025", 4.7, false, false));
        defaults.add(new Trip("trip3", "Пляжи Испании", "Испания", "Бархатный сезон в Барселоне и Валенсии.", "20.09.2025", 4.9, true, false));
        trips.setValue(defaults);
    }

    public LiveData<List<Country>> observeCountries() {
        return countries;
    }

    public LiveData<List<Trip>> observeTrips() {
        return trips;
    }

    public void addTrip(String title, String country, String description, String date, boolean mine) {
        List<Trip> current = trips.getValue();
        if (current == null) {
            current = new ArrayList<>();
        } else {
            current = new ArrayList<>(current);
        }
        current.add(new Trip(UUID.randomUUID().toString(), title, country, description, date, 4.5, false, mine));
        trips.postValue(current);
    }

    public void toggleTripFavorite(String id) {
        List<Trip> current = trips.getValue();
        if (current == null) return;
        List<Trip> updated = new ArrayList<>();
        for (Trip trip : current) {
            if (trip.getId().equals(id)) {
                trip.toggleFavorite();
            }
            updated.add(trip);
        }
        trips.postValue(updated);
    }

    public void toggleCountryFavorite(String id) {
        List<Country> current = countries.getValue();
        if (current == null) return;
        List<Country> updated = new ArrayList<>();
        for (Country country : current) {
            if (country.getId().equals(id)) {
                country.toggleFavorite();
            }
            updated.add(country);
        }
        countries.postValue(updated);
    }

    public Country findCountry(String id) {
        List<Country> current = countries.getValue();
        if (current == null) return null;
        for (Country country : current) {
            if (country.getId().equals(id)) {
                return country;
            }
        }
        return null;
    }

    public Trip findTrip(String id) {
        List<Trip> current = trips.getValue();
        if (current == null) return null;
        for (Trip trip : current) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }
}
