package com.kynzai.tripmate_stud.domain.model;

import java.util.List;

public class Country {
    private final String id;
    private final String name;
    private final String description;
    private final String capital;
    private final double rating;
    private final List<String> highlights;
    private final String imageHint;
    private boolean favorite;

    public Country(String id, String name, String description, String capital, double rating, List<String> highlights, String imageHint, boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capital = capital;
        this.rating = rating;
        this.highlights = highlights;
        this.imageHint = imageHint;
        this.favorite = favorite;
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

    public String getCapital() {
        return capital;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public String getImageHint() {
        return imageHint;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void toggleFavorite() {
        favorite = !favorite;
    }
}
