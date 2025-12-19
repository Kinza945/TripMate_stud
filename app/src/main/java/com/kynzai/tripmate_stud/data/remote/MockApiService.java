package com.kynzai.tripmate_stud.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MockApiService {
    @GET("countries")
    Call<List<CountryResponse>> getCountries();

    @GET("countries/{id}")
    Call<CountryResponse> getCountryById(@Path("id") String id);

    @GET("trips")
    Call<List<TripResponse>> getTrips();

    @GET("trips/{id}")
    Call<TripResponse> getTripById(@Path("id") String id);

    class CountryResponse {
        public String id;
        public String name;
        public String description;
        public String capital;
        public String currency;
        public String temperature;
        public String imageUrl;
    }

    class TripResponse {
        public String id;
        public String name;
        public String countryName;
        public String description;
        public String imageUrl;
    }
}
