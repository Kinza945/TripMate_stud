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

import com.kynzai.tripmate_stud.MainActivity;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.databinding.FragmentprofileBinding;
import com.kynzai.tripmate_stud.presentation.viewmodel.AuthViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentprofileBinding binding;
    private AuthViewModel authViewModel;
    private ProfileViewModel profileViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentprofileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding.loginButton.setOnClickListener(v -> performAuth(false));
        binding.registerButton.setOnClickListener(v -> performAuth(true));
        binding.logoutButton.setOnClickListener(v -> authViewModel.logout());
        binding.myTripsButton.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).selectTab(R.id.travel);
            }
        });
        binding.favoriteTripsButton.setOnClickListener(v -> {
            if (requireActivity() instanceof MainActivity) {
                ((MainActivity) requireActivity()).selectTab(R.id.love);
            }
        });
        binding.addTripButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_addTrip));

        authViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                binding.profileTitle.setText("Гость");
                binding.profileEmail.setText("");
                binding.logoutButton.setVisibility(View.GONE);
                binding.authBlock.setVisibility(View.VISIBLE);
            } else {
                binding.logoutButton.setVisibility(View.VISIBLE);
                binding.authBlock.setVisibility(View.GONE);
                profileViewModel.getUserById(user.getUid()).observe(getViewLifecycleOwner(), profile -> {
                    if (profile != null && !profile.getDisplayName().isEmpty()) {
                        binding.profileTitle.setText(profile.getDisplayName());
                        binding.profileEmail.setText(profile.getEmail());
                    } else {
                        binding.profileTitle.setText(user.getDisplayName());
                        binding.profileEmail.setText(user.getEmail());
                    }
                });
            }
        });

        authViewModel.getAuthMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                authViewModel.clearAuthMessage();
            }
        });

        authViewModel.getAuthError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                authViewModel.clearAuthError();
            }
        });
    }

    private void performAuth(boolean register) {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        if (register) {
            authViewModel.register(email, password);
        } else {
            authViewModel.login(email, password);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
