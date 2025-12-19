package com.kynzai.tripmate_stud.domain.model;

import java.util.UUID;

public class Trip {
    private final String id;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final String location;
    private final boolean favorite;

    public Trip(String id, String title, String description, String imageUrl, String location, boolean favorite) {
        this.id = id == null ? UUID.randomUUID().toString() : id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Trip toggleFavorite() {
        return new Trip(id, title, description, imageUrl, location, !favorite);
    }
}
