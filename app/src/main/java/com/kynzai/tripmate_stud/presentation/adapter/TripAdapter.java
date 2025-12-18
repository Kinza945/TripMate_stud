package com.kynzai.tripmate_stud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {
    public interface TripClickListener {
        void onTripClick(Trip trip);
        void onFavoriteClick(Trip trip);
    }

    private final TripClickListener listener;
    private List<Trip> items = new ArrayList<>();

    public TripAdapter(TripClickListener listener) {
        this.listener = listener;
    }

    public void submit(List<Trip> data) {
        items = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_items, parent, false);
        return new TripHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {
        Trip trip = items.get(position);
        holder.title.setText(trip.getTitle());
        holder.subtitle.setText(trip.getDescription());
        holder.rating.setText(String.valueOf(trip.getRating()));
        holder.favoriteButton.setImageResource(trip.isFavorite() ? R.drawable.star_two : R.drawable.star);
        holder.favoriteButton.setOnClickListener(v -> listener.onFavoriteClick(trip));
        holder.itemView.setOnClickListener(v -> listener.onTripClick(trip));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TripHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        TextView rating;
        ImageButton favoriteButton;

        TripHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView);
            subtitle = itemView.findViewById(R.id.text_reference);
            rating = itemView.findViewById(R.id.stars_id);
            favoriteButton = itemView.findViewById(R.id.favorite_action);
        }
    }
}
