package com.kynzai.tripmate_stud.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApiService {
    @GET("v6/{apiKey}/latest/{base}")
    Call<CurrencyResponse> getLatestRates(@Path("apiKey") String apiKey, @Path("base") String base);

    class CurrencyResponse {
        public String base_code;
        public java.util.Map<String, Double> conversion_rates;
    }
}
