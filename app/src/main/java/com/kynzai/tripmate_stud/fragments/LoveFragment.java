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

import com.kynzai.tripmate_stud.databinding.FragmentLoveBinding;
import com.kynzai.tripmate_stud.presentation.adapter.TripAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.AuthViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;

public class LoveFragment extends Fragment implements TripAdapter.TripInteractionListener {

    private FragmentLoveBinding binding;
    private TripViewModel tripViewModel;
    private AuthViewModel authViewModel;
    private TripAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        adapter = new TripAdapter(this);
        binding.favoriteList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.favoriteList.setAdapter(adapter);

        tripViewModel.getFavoriteTrips().observe(getViewLifecycleOwner(), adapter::submit);
        authViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            boolean signedIn = user != null;
            adapter.setActionsEnabled(signedIn);
            adapter.setCurrentUserId(user == null ? null : user.getUid());
            binding.guestHint.setVisibility(signedIn ? View.GONE : View.VISIBLE);
        });
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
        // Editing favorites list is disabled; trip edit is available in "Мои поездки".
    }
}
