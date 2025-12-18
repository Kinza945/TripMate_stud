package com.kynzai.tripmate_stud.adapters;


import static android.view.View.VISIBLE;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.R;

import java.util.ArrayList;


public class AdapterListTravels extends RecyclerView.Adapter<AdapterListTravels.ViewHolder> {

    private final ArrayList<TravelItem> localDataSet;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mainTextHeader;
        private final TextView referenceMainText;
        private final TextView starsF;
        private final TextView recommendet;

        public ViewHolder(View view) {
            super(view);

           Log.d(" dsf ", " my VIEW WW = " +  view.getRootView().getWidth());

            mainTextHeader =  view.findViewById(R.id.textView);
            referenceMainText = view.findViewById(R.id.text_reference);
            starsF  = view.findViewById(R.id.stars_id);
            recommendet = view.findViewById(R.id.recomendatit);

        }
    }


    public AdapterListTravels(ArrayList<TravelItem> dataSet) {
        localDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.travel_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.mainTextHeader.setText(localDataSet.get(position).getNameMain());
        viewHolder.referenceMainText.setText(localDataSet.get(position).getReferencesMain());
        viewHolder.starsF.setText(String.valueOf(localDataSet.get(position).getStars()));

        if(localDataSet.get(position).getRecommendationOn()){
            viewHolder.recommendet.setVisibility(VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}