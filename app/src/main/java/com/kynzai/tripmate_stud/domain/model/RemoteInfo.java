package com.kynzai.tripmate_stud.domain.model;

public class RemoteInfo {
    private final String currencyRate;
    private final String weather;

    public RemoteInfo(String currencyRate, String weather) {
        this.currencyRate = currencyRate;
        this.weather = weather;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public String getWeather() {
        return weather;
    }
}
