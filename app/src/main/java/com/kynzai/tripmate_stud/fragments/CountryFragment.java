package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.databinding.FragmentCountryBinding;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.presentation.adapter.CountryAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;
import com.kynzai.tripmate_stud.presentation.viewmodel.AuthViewModel;
import android.widget.Toast;

public class CountryFragment extends Fragment implements CountryAdapter.CountryClickListener {

    private FragmentCountryBinding binding;
    private CountryViewModel viewModel;
    private AuthViewModel authViewModel;
    private CountryAdapter adapter;
    private java.util.List<Country> allCountries = new java.util.ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
        adapter = new CountryAdapter(this);
        binding.countryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.countryList.setAdapter(adapter);

        viewModel.getCountries().observe(getViewLifecycleOwner(), countries -> {
            allCountries = countries == null ? new java.util.ArrayList<>() : countries;
            applyFilter(binding.countryFilterGroup.getCheckedButtonId());
        });
        viewModel.getFavoriteCountryIds().observe(getViewLifecycleOwner(), adapter::setFavoriteIds);
        binding.countryFilterGroup.check(R.id.filter_mountain);
        binding.countryFilterGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                applyFilter(checkedId);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCountryClicked(Country country) {
        Bundle args = new Bundle();
        args.putString("countryId", country.getId());
        NavHostFragment.findNavController(this).navigate(R.id.action_country_to_countryDetail, args);
    }

    @Override
    public void onFavoriteClicked(Country country) {
        if (authViewModel.getCurrentUser().getValue() == null) {
            Toast.makeText(requireContext(), "Войдите, чтобы добавить в избранное", Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.toggleFavoriteCountry(country.getId());
    }

    private void applyFilter(int checkedId) {
        String type = null;
        if (checkedId == R.id.filter_mountain) {
            type = "mountain";
        } else if (checkedId == R.id.filter_beach) {
            type = "beach";
        } else if (checkedId == R.id.filter_city) {
            type = "city";
        }
        if (type == null) {
            adapter.submit(allCountries);
            return;
        }
        java.util.List<Country> filtered = new java.util.ArrayList<>();
        for (Country country : allCountries) {
            String countryType = country.getType() == null ? "" : country.getType().toLowerCase();
            if (countryType.isEmpty() || countryType.contains(type)) {
                filtered.add(country);
            }
        }
        adapter.submit(filtered);
    }
}
