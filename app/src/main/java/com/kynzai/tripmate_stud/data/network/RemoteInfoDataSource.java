package com.kynzai.tripmate_stud.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kynzai.tripmate_stud.domain.model.RemoteInfo;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteInfoDataSource {
    private final MutableLiveData<RemoteInfo> remoteInfo = new MutableLiveData<>();

    public RemoteInfoDataSource() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit ratesRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangerate.host/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        Retrofit weatherRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RemoteApiService rateService = ratesRetrofit.create(RemoteApiService.class);
        RemoteApiService weatherService = weatherRetrofit.create(RemoteApiService.class);

        fetchAsync(rateService, weatherService);
    }

    private void fetchAsync(RemoteApiService rateService, RemoteApiService weatherService) {
        new Thread(() -> {
            String currency = "Нет данных";
            String weather = "Нет данных";
            try {
                Response<CurrencyResponse> rateResponse = rateService.loadRates("USD", "EUR").execute();
                if (rateResponse.isSuccessful() && rateResponse.body() != null && rateResponse.body().rates != null) {
                    Double eur = rateResponse.body().rates.get("EUR");
                    if (eur != null) {
                        currency = String.format(Locale.getDefault(), "1 USD = %.2f EUR", eur);
                    }
                }
            } catch (IOException e) {
                Log.w("RemoteInfoDataSource", "Currency fallback", e);
            }

            try {
                Response<WeatherResponse> weatherResponse = weatherService.loadWeather(55.75, 37.62, true).execute();
                if (weatherResponse.isSuccessful() && weatherResponse.body() != null && weatherResponse.body().currentWeather != null) {
                    WeatherResponse.CurrentWeather cw = weatherResponse.body().currentWeather;
                    weather = String.format(Locale.getDefault(), "Погода Москва: %.1f°C, ветер %.1f м/с", cw.temperature, cw.windSpeed);
                }
            } catch (IOException e) {
                Log.w("RemoteInfoDataSource", "Weather fallback", e);
            }

            if (currency.equals("Нет данных") && weather.equals("Нет данных")) {
                remoteInfo.postValue(new RemoteInfo("1 USD = 0.92 EUR (offline)", "Погода: 12°C, ясно (кеш)"));
            } else {
                remoteInfo.postValue(new RemoteInfo(currency, weather));
            }
        }).start();
    }

    public LiveData<RemoteInfo> observeInfo() {
        return remoteInfo;
    }
}
