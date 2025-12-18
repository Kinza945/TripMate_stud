package com.kynzai.tripmate_stud.domain.model;

public class Country {
    private final String id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String capital;

    public Country(String id, String name, String description, String imageUrl, String capital) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.capital = capital;
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
}
