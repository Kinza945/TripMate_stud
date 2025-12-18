package com.kynzai.tripmate_stud.data.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RemoteApiService {
    @GET("latest")
    Call<CurrencyResponse> loadRates(@Query("base") String base, @Query("symbols") String symbols);

    @GET("v1/forecast")
    Call<WeatherResponse> loadWeather(@Query("latitude") double lat, @Query("longitude") double lon, @Query("current_weather") boolean current);
}
