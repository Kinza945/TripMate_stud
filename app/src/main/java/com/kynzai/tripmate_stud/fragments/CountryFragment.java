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

import com.kynzai.tripmate_stud.databinding.FragmentCountryBinding;
import com.kynzai.tripmate_stud.presentation.adapter.CountryAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;

public class CountryFragment extends Fragment {

    private FragmentCountryBinding binding;
    private CountryViewModel viewModel;
    private CountryAdapter adapter;

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
        adapter = new CountryAdapter();
        binding.countryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.countryList.setAdapter(adapter);

        viewModel.getCountries().observe(getViewLifecycleOwner(), adapter::submit);
        viewModel.getCurrencyInfo().observe(getViewLifecycleOwner(), info -> {
            if (info != null) {
                binding.currencyInfo.setText("Курс " + info.getBase() + "/" + info.getTarget() + ": " + info.getRate());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
