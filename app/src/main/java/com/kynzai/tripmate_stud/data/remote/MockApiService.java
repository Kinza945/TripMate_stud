package com.kynzai.tripmate_stud.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MockApiService {
    @GET("countries")
    Call<List<CountryResponse>> getCountries();

    @GET("trips")
    Call<List<TripResponse>> getTrips();

    class CountryResponse {
        public String id;
        public String name;
        public String description;
        public String capital;
        public String currency;
        public String temperature;
        public String imageUrl;
        public String type;
    }

    class TripResponse {
        public String id;
        public String title;
        public String description;
        public String imageUrl;
        public String location;
        public String name;
        public String countryName;
        public String capital;
    }
}
