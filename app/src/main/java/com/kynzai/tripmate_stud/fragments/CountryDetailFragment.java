package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.kynzai.tripmate_stud.databinding.FragmentCountryDetailBinding;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;

public class CountryDetailFragment extends Fragment {

    private FragmentCountryDetailBinding binding;
    private CountryViewModel countryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countryViewModel = new ViewModelProvider(requireActivity()).get(CountryViewModel.class);
        if (getArguments() != null) {
            Country country = (Country) getArguments().getSerializable("country");
            if (country != null) {
                bindCountry(country);
            }
        }
        countryViewModel.getCurrencyInfo().observe(getViewLifecycleOwner(), info -> {
            if (info == null) {
                binding.detailRateValue.setText("—");
            } else {
                String text = info.getBase() + " → " + info.getTarget() + ": " + info.getRate();
                binding.detailRateValue.setText(text);
            }
        });
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
        String weatherValue = formatTemperature(country.getTemperature());
        binding.detailTemperature.setText(weatherValue);
        binding.detailWeatherValue.setText(weatherValue);
        if (binding.detailRateValue.getText() == null || binding.detailRateValue.getText().length() == 0) {
            binding.detailRateValue.setText("—");
        }
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
