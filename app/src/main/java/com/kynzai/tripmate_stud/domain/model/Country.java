package com.kynzai.tripmate_stud.domain.model;

import java.io.Serializable;

public class Country implements Serializable {
    private final String id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String capital;
    private final String currency;
    private final String temperature;

    public Country(String id, String name, String description, String imageUrl, String capital, String currency, String temperature) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.capital = capital;
        this.currency = currency;
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCapital() {
        return capital;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTemperature() {
        return temperature;
    }
}
