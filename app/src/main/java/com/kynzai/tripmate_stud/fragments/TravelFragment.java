package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kynzai.tripmate_stud.AppGraph;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.presentation.adapter.TripAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.ProfileViewModel;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class TravelFragment extends Fragment implements TripAdapter.TripClickListener {

    private TripViewModel viewModel;
    private TripAdapter adapter;
    private com.kynzai.tripmate_stud.presentation.viewmodel.ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel, container, false);
        viewModel = AppGraph.getInstance(requireContext()).provideTripViewModel();
        profileViewModel = AppGraph.getInstance(requireContext()).provideProfileViewModel();
        adapter = new TripAdapter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        FloatingActionButton addFab = view.findViewById(R.id.add_trip);
        addFab.setOnClickListener(v -> {
            if (profileViewModel.getSession().getValue() != null && profileViewModel.getSession().getValue().isAuthenticated()) {
                showAddDialog();
            } else {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                builder.setMessage(R.string.login_required)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });

        observe();
        return view;
    }

    private void observe() {
        viewModel.getTrips().observe(getViewLifecycleOwner(), this::renderTrips);
    }

    private void renderTrips(List<Trip> trips) {
        adapter.submit(trips);
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_trip, null, false);
        EditText title = dialogView.findViewById(R.id.input_title);
        EditText country = dialogView.findViewById(R.id.input_country);
        EditText description = dialogView.findViewById(R.id.input_description);
        EditText date = dialogView.findViewById(R.id.input_date);
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.add_trip)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, (d, which) -> {
                    Trip trip = new Trip("new", title.getText().toString(), country.getText().toString(), description.getText().toString(), date.getText().toString(), 4.5, false, true);
                    viewModel.addTrip(trip);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public void onTripClick(Trip trip) {
        Bundle args = new Bundle();
        args.putString("tripId", trip.getId());
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_travel_to_travelDetail, args);
    }

    @Override
    public void onFavoriteClick(Trip trip) {
        viewModel.toggleFavorite(trip.getId());
    }
}
