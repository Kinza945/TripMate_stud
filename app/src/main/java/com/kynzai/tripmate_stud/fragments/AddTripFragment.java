package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.databinding.FragmentAddTripBinding;
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.presentation.viewmodel.AuthViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;

public class AddTripFragment extends Fragment {

    private FragmentAddTripBinding binding;
    private TripViewModel tripViewModel;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTripBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        binding.backButton.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        binding.saveTripButton.setOnClickListener(v -> {
            if (authViewModel.getCurrentUser().getValue() == null) {
                Toast.makeText(requireContext(), "Авторизуйтесь, чтобы создавать поездки", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigateUp();
                return;
            }
            String title = binding.tripTitleInput.getText().toString();
            String location = binding.tripLocationInput.getText().toString();
            String desc = binding.tripDescInput.getText().toString();
            String image = binding.tripImageInput.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(location)) {
                Toast.makeText(requireContext(), "Заполните название и локацию", Toast.LENGTH_SHORT).show();
                return;
            }
            Trip trip = new Trip(null, title, desc, image, location, null, false);
            tripViewModel.addTrip(trip);
            Toast.makeText(requireContext(), "Поездка сохранена", Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
