package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.AppGraph;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.presentation.adapter.TripAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.FavoritesViewModel;

import java.util.List;

public class LoveFragment extends Fragment implements TripAdapter.TripClickListener {

    private FavoritesViewModel viewModel;
    private TripAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love, container, false);
        viewModel = AppGraph.getInstance(requireContext()).provideFavoritesViewModel();
        adapter = new TripAdapter(this);

        RecyclerView recyclerView = view.findViewById(R.id.favorites_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        observe();
        return view;
    }

    private void observe() {
        viewModel.getFavorites().observe(getViewLifecycleOwner(), this::renderFavorites);
    }

    private void renderFavorites(List<Trip> trips) {
        adapter.submit(trips);
    }

    @Override
    public void onTripClick(Trip trip) {
        // Favorites uses same detail navigation as trips
        Bundle args = new Bundle();
        args.putString("tripId", trip.getId());
        Navigation.findNavController(requireView()).navigate(R.id.action_love_to_travelDetail, args);
    }

    @Override
    public void onFavoriteClick(Trip trip) {
        viewModel.toggleFavorite(trip.getId());
    }
}
