package com.kynzai.tripmate_stud.domain.model;

import java.io.Serializable;

public class Country implements Serializable {

    private String id;
    private String name;
    private String capital;
    private String description;
    private String currency;
    private String temperature;
    private String imageUrl;

    // Пустой конструктор нужен для сериализации
    public Country() {
    }

    public Country(String id,
                   String name,
                   String capital,
                   String description,
                   String currency,
                   String temperature,
                   String imageUrl) {
        this.id = id;
        this.name = name;
        this.capital = capital;
        this.description = description;
        this.currency = currency;
        this.temperature = temperature;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
