package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.kynzai.tripmate_stud.AppGraph;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.presentation.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private View loginForm;
    private View profileCard;
    private TextView emailLabel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentprofile, container, false);
        viewModel = AppGraph.getInstance(requireContext()).provideProfileViewModel();
        loginForm = view.findViewById(R.id.login_form);
        profileCard = view.findViewById(R.id.profile_card);
        emailLabel = view.findViewById(R.id.profile_email);

        EditText emailInput = view.findViewById(R.id.input_email);
        EditText passwordInput = view.findViewById(R.id.input_password);
        Button loginButton = view.findViewById(R.id.button_login);
        Button registerButton = view.findViewById(R.id.button_register);
        Button logoutButton = view.findViewById(R.id.button_logout);
        Button openTrips = view.findViewById(R.id.button_my_trips);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                viewModel.login(email, password);
            } else {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                viewModel.register(email, password);
            } else {
                Toast.makeText(requireContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(v -> viewModel.signOut());
        openTrips.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.travel));

        observeSession();
        return view;
    }

    private void observeSession() {
        viewModel.getSession().observe(getViewLifecycleOwner(), session -> {
            if (session != null && session.isAuthenticated()) {
                loginForm.setVisibility(View.GONE);
                profileCard.setVisibility(View.VISIBLE);
                emailLabel.setText(session.getEmail());
            } else {
                loginForm.setVisibility(View.VISIBLE);
                profileCard.setVisibility(View.GONE);
            }
        });
    }
}
