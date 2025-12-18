package com.kynzai.tripmate_stud.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.data.remote.CurrencyApiService;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.domain.model.CurrencyInfo;
import com.kynzai.tripmate_stud.domain.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryRepositoryImpl implements CountryRepository {

    private final MutableLiveData<List<Country>> countries = new MutableLiveData<>();
    private final MutableLiveData<CurrencyInfo> currencyInfo = new MutableLiveData<>();
    private final CurrencyApiService apiService;

    public CountryRepositoryImpl() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://open.er-api.com/v6/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(CurrencyApiService.class);
        loadCountries();
        fetchCurrency();
    }

    private void loadCountries() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("ru", "Россия", "Красочные города, культурное наследие и незабываемые железнодорожные путешествия.", "https://images.unsplash.com/photo-1546541612-7c5f70e571d4", "Москва"));
        countryList.add(new Country("es", "Испания", "Солнечные побережья, гастрономия и города Гауди.", "https://images.unsplash.com/photo-1505761671935-60b3a7427bad", "Мадрид"));
        countryList.add(new Country("jp", "Япония", "Сочетание традиций и технологий, восхитительная кухня и культура уважения.", "https://images.unsplash.com/photo-1505060055475-0858d9047f3f", "Токио"));
        countryList.add(new Country("br", "Бразилия", "Карнавалы, тропики и длинные пляжи для серфинга.", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d", "Рио-де-Жанейро"));
        countries.setValue(countryList);
    }

    private void fetchCurrency() {
        apiService.getLatestRates("USD", "EUR,JPY").enqueue(new Callback<CurrencyApiService.CurrencyResponse>() {
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

    @Override
    public LiveData<List<Country>> getCountries() {
        return countries;
    }

    @Override
    public LiveData<CurrencyInfo> getCurrencyInfo() {
        return currencyInfo;
    }
}
