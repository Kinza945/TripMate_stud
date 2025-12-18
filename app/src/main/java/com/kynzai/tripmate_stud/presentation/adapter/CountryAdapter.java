package com.kynzai.tripmate_stud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {
    public interface CountryClickListener {
        void onCountryClicked(Country country);
        void onFavoriteClicked(Country country);
    }

    private final CountryClickListener listener;
    private List<Country> items = new ArrayList<>();

    public CountryAdapter(CountryClickListener listener) {
        this.listener = listener;
    }

    public void submit(List<Country> countries) {
        items = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        Country country = items.get(position);
        holder.title.setText(country.getName());
        holder.description.setText(country.getDescription());
        holder.rating.setText(String.valueOf(country.getRating()));
        holder.icon.setImageResource(R.drawable.star_one);
        holder.favoriteButton.setImageResource(country.isFavorite() ? R.drawable.star_two : R.drawable.star);
        holder.favoriteButton.setOnClickListener(v -> listener.onFavoriteClicked(country));
        holder.itemView.setOnClickListener(v -> listener.onCountryClicked(country));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CountryHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView rating;
        ImageView icon;
        ImageButton favoriteButton;

        CountryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.country_title);
            description = itemView.findViewById(R.id.country_description);
            rating = itemView.findViewById(R.id.country_rating);
            icon = itemView.findViewById(R.id.country_icon);
            favoriteButton = itemView.findViewById(R.id.country_favorite);
        }
    }
}
