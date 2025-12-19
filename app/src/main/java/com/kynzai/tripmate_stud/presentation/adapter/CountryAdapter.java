package com.kynzai.tripmate_stud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

    public interface CountryClickListener {
        void onCountryClicked(Country country);
        void onFavoriteClicked(Country country);
    }

    private List<Country> countries = new ArrayList<>();
    private final CountryClickListener listener;
    private java.util.Set<String> favoriteIds = new java.util.HashSet<>();

    public CountryAdapter(CountryClickListener listener) {
        this.listener = listener;
    }

    public void submit(List<Country> items) {
        countries = items == null ? new ArrayList<>() : items;
        notifyDataSetChanged();
    }

    public void setFavoriteIds(java.util.Set<String> ids) {
        favoriteIds = ids == null ? new java.util.HashSet<>() : ids;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        Country country = countries.get(position);
        holder.title.setText(country.getName());
        holder.subtitle.setText(country.getDescription());
        holder.favorite.setImageResource(favoriteIds.contains(country.getId()) ? R.drawable.star_two : R.drawable.star_one);
        Glide.with(holder.image.getContext())
                .load(country.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.image);
        holder.itemView.setOnClickListener(v -> listener.onCountryClicked(country));
        holder.favorite.setOnClickListener(v -> listener.onFavoriteClicked(country));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView subtitle;
        android.widget.ImageButton favorite;

        CountryHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.country_image);
            title = itemView.findViewById(R.id.country_title);
            subtitle = itemView.findViewById(R.id.country_description);
            favorite = itemView.findViewById(R.id.country_favorite);
        }
    }
}
