package com.kynzai.tripmate_stud.domain.model;

public class Trip {
    private final String id;
    private final String title;
    private final String country;
    private final String description;
    private final String date;
    private final double rating;
    private boolean favorite;
    private final boolean mine;

    public Trip(String id, String title, String country, String description, String date, double rating, boolean favorite, boolean mine) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.description = description;
        this.date = date;
        this.rating = rating;
        this.favorite = favorite;
        this.mine = mine;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public double getRating() {
        return rating;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isMine() {
        return mine;
    }

    public void toggleFavorite() {
        favorite = !favorite;
    }
}
