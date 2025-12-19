package com.kynzai.tripmate_stud.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.data.remote.CurrencyApiService;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

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
import com.kynzai.tripmate_stud.data.remote.MockApiService;

public class CountryRepositoryImpl implements CountryRepository {

    private static final String MOCK_BASE_URL = "https://694461477dd335f4c3602a64.mockapi.io/TripMate_studapi/";

    private final MutableLiveData<List<Country>> countries = new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<CurrencyInfo> currencyInfo = new MutableLiveData<>();
    private final CurrencyApiService currencyService;
    private final MockApiService mockApiService;

    public CountryRepositoryImpl() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit mockRetrofit = new Retrofit.Builder()
                .baseUrl(MOCK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mockApiService = mockRetrofit.create(MockApiService.class);

        Retrofit currencyRetrofit = new Retrofit.Builder()
                .baseUrl("https://open.er-api.com/v6/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        currencyService = currencyRetrofit.create(CurrencyApiService.class);

        loadCountries();
        fetchCurrency();
    }

    private void loadCountries() {
        mockApiService.getCountries().enqueue(new Callback<List<MockApiService.CountryResponse>>() {
            @Override
            public void onResponse(Call<List<MockApiService.CountryResponse>> call, Response<List<MockApiService.CountryResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Country> mapped = new ArrayList<>();
                    for (MockApiService.CountryResponse item : response.body()) {
                        mapped.add(new Country(
                                item.id,
                                safe(item.name),
                                safe(item.description),
                                safe(item.imageUrl),
                                safe(item.capital),
                                safe(item.currency),
                                safe(item.temperature)
                        ));
                    }
                    countries.postValue(mapped);
                }
            }

            @Override
            public void onFailure(Call<List<MockApiService.CountryResponse>> call, Throwable t) {
                Log.e("CountryRepository", "Country fetch failed", t);
            }
        });
    }

    private void fetchCurrency() {
        currencyService.getLatestRates("USD", "EUR,JPY").enqueue(new Callback<CurrencyApiService.CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyApiService.CurrencyResponse> call, Response<CurrencyApiService.CurrencyResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().rates != null) {
                    Double eur = response.body().rates.get("EUR");
                    if (eur != null) {
                        currencyInfo.postValue(new CurrencyInfo(response.body().base, "EUR", eur));
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyApiService.CurrencyResponse> call, Throwable t) {
                Log.e("CountryRepository", "Currency fetch failed", t);
            }
        });
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @Override
    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    @Override
    public LiveData<CurrencyInfo> getCurrencyInfo() {
        return currencyInfo;
    }
}
