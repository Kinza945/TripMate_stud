package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kynzai.tripmate_stud.databinding.FragmentCountryDetailBinding;
import com.kynzai.tripmate_stud.domain.model.Country;

public class CountryDetailFragment extends Fragment {

    private FragmentCountryDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            Country country = (Country) getArguments().getSerializable("country");
            if (country != null) {
                bindCountry(country);
            }
        }
    }

    private void bindCountry(@NonNull Country country) {
        Glide.with(requireContext())
                .load(country.getImageUrl())
                .placeholder(com.kynzai.tripmate_stud.R.drawable.ic_launcher_foreground)
                .into(binding.detailImage);
        binding.detailName.setText(country.getName());
        binding.detailDescription.setText(country.getDescription());
        binding.detailCapital.setText(country.getCapital());
        binding.detailCurrency.setText(country.getCurrency());
        binding.detailTemperature.setText(formatTemperature(country.getTemperature()));
    }

    private String formatTemperature(String value) {
        if (value == null || value.isEmpty()) {
            return "—";
        }
        try {
            double parsed = Double.parseDouble(value);
            if (parsed > 60 || parsed < -60) {
                parsed = 25;
            }
            long rounded = Math.round(parsed);
            return rounded + "°C";
        } catch (NumberFormatException e) {
            return "—";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
