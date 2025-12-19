package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kynzai.tripmate_stud.databinding.FragmentTravelBinding;
import com.kynzai.tripmate_stud.presentation.adapter.TripAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.AuthViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;
import com.kynzai.tripmate_stud.R;
import androidx.navigation.fragment.NavHostFragment;

public class TravelFragment extends Fragment implements TripAdapter.TripInteractionListener {

    private FragmentTravelBinding binding;
    private TripViewModel tripViewModel;
    private AuthViewModel authViewModel;
    private TripAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTravelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        adapter = new TripAdapter(this);
        binding.tripList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.tripList.setAdapter(adapter);

        tripViewModel.getTrips().observe(getViewLifecycleOwner(), adapter::submit);
        authViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            boolean signedIn = user != null;
            adapter.setActionsEnabled(signedIn);
            adapter.setCurrentUserId(user == null ? null : user.getUid());
            binding.addTripCard.setVisibility(signedIn ? View.VISIBLE : View.GONE);
            binding.guestHint.setVisibility(signedIn ? View.GONE : View.VISIBLE);
        });

        binding.openAddTripButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_travel_to_addTrip));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFavorite(String id) {
        tripViewModel.toggleFavorite(id);
    }

    @Override
    public void onRemove(String id) {
        tripViewModel.removeTrip(id);
    }

    @Override
    public void onEdit(com.kynzai.tripmate_stud.domain.model.Trip trip) {
        Bundle args = new Bundle();
        args.putString("tripId", trip.getId());
        NavHostFragment.findNavController(this).navigate(R.id.action_travel_to_editTrip, args);
    }
}
