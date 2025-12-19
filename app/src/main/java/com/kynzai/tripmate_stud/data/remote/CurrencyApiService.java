package com.kynzai.tripmate_stud.data.remote;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyApiService {
    @GET("latest")
    Call<CurrencyResponse> getLatestRates(@Query("base") String base, @Query("symbols") String symbols);

    class CurrencyResponse {
        public String base;
        public Map<String, Double> rates;
    }
}
