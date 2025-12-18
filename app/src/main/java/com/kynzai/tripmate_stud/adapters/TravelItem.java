package com.kynzai.tripmate_stud.adapters;

import android.widget.ImageView;

public class TravelItem {
    private final int ID;
    private final String nameMain;
    private final String referencesMain;

    private final float stars;
    private final boolean favorite;

    private final boolean recommendationOn;

    private final String imageAssetsName;


    public TravelItem(int idg, String nameMaing, String referencesMaing, float starsg, boolean favoriteg,
                      boolean recommendationOng, String imageBackg){

        ID = idg;
        nameMain = nameMaing;
        referencesMain = referencesMaing;
        stars = starsg;
        favorite = favoriteg;
        recommendationOn = recommendationOng;
        imageAssetsName = imageBackg;
    }

    public int getID(){
        return ID;
    }

    public String getNameMain(){
        return nameMain;
    }
    public String getReferencesMain(){
        return referencesMain;
    }

    public float getStars(){
        return stars;
    }

    public boolean getFavorite(){
        return favorite;
    }

    public boolean getRecommendationOn(){
        return recommendationOn;
    }

    public String getImageAssetsName(){
        return imageAssetsName;
    }



}
