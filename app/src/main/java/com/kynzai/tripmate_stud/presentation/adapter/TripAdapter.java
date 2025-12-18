package com.kynzai.tripmate_stud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {

    public interface TripInteractionListener {
        void onFavorite(String id);
        void onRemove(String id);
    }

    private List<Trip> trips = new ArrayList<>();
    private final TripInteractionListener listener;
    private boolean actionsEnabled = true;

    public TripAdapter(TripInteractionListener listener) {
        this.listener = listener;
    }

    public void submit(List<Trip> items) {
        trips = items;
        notifyDataSetChanged();
    }

    public void setActionsEnabled(boolean enabled) {
        actionsEnabled = enabled;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.title.setText(trip.getTitle());
        holder.description.setText(trip.getDescription());
        holder.location.setText(trip.getLocation());
        holder.favorite.setImageResource(trip.isFavorite() ? R.drawable.star_two : R.drawable.star_one);
        holder.favorite.setOnClickListener(v -> listener.onFavorite(trip.getId()));
        holder.remove.setOnClickListener(v -> listener.onRemove(trip.getId()));
        holder.favorite.setVisibility(actionsEnabled ? View.VISIBLE : View.GONE);
        holder.remove.setVisibility(actionsEnabled ? View.VISIBLE : View.GONE);
        Glide.with(holder.image.getContext())
                .load(trip.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        TextView location;
        ImageButton favorite;
        ImageButton remove;

        TripHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.trip_image);
            title = itemView.findViewById(R.id.trip_title);
            description = itemView.findViewById(R.id.trip_description);
            location = itemView.findViewById(R.id.trip_location);
            favorite = itemView.findViewById(R.id.trip_favorite);
            remove = itemView.findViewById(R.id.trip_remove);
        }
    }
}
