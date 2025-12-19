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
import androidx.core.content.ContextCompat;
import com.kynzai.tripmate_stud.domain.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {

    public interface TripInteractionListener {
        void onFavorite(String id);
        void onRemove(String id);
        void onEdit(Trip trip);
    }

    private List<Trip> trips = new ArrayList<>();
    private final TripInteractionListener listener;
    private boolean actionsEnabled = true;
    private String currentUserId;

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

    public void setCurrentUserId(String userId) {
        currentUserId = userId;
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
        holder.subtitle.setText(trip.getLocation());
        holder.favorite.setImageResource(trip.isFavorite() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
        int favoriteTint = ContextCompat.getColor(holder.itemView.getContext(),
                trip.isFavorite() ? R.color.onSurface : R.color.onSurfaceVariant);
        holder.favorite.setColorFilter(favoriteTint);
        holder.favorite.setOnClickListener(v -> listener.onFavorite(trip.getId()));
        holder.remove.setOnClickListener(v -> listener.onRemove(trip.getId()));
        boolean canEdit = actionsEnabled && currentUserId != null && currentUserId.equals(trip.getOwnerUid());
        holder.favorite.setVisibility(actionsEnabled ? View.VISIBLE : View.GONE);
        holder.remove.setVisibility(canEdit ? View.VISIBLE : View.GONE);
        holder.edit.setVisibility(canEdit ? View.VISIBLE : View.GONE);
        holder.edit.setOnClickListener(v -> listener.onEdit(trip));
        boolean recommended = trip.getOwnerUid() == null || trip.getOwnerUid().isEmpty();
        holder.recommended.setVisibility(recommended ? View.VISIBLE : View.GONE);
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
        TextView subtitle;
        TextView recommended;
        ImageButton favorite;
        ImageButton remove;
        ImageButton edit;

        TripHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.trip_image);
            title = itemView.findViewById(R.id.trip_title);
            subtitle = itemView.findViewById(R.id.trip_subtitle);
            recommended = itemView.findViewById(R.id.trip_recommended);
            favorite = itemView.findViewById(R.id.trip_favorite);
            remove = itemView.findViewById(R.id.trip_remove);
            edit = itemView.findViewById(R.id.trip_edit);
        }
    }
}
