package com.kynzai.tripmate_stud.data.network;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("current_weather")
    public CurrentWeather currentWeather;

    public static class CurrentWeather {
        @SerializedName("temperature")
        public double temperature;

        @SerializedName("windspeed")
        public double windSpeed;
    }
}
