package com.kynzai.tripmate_stud.data.network;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CurrencyResponse {
    @SerializedName("rates")
    public Map<String, Double> rates;
}
