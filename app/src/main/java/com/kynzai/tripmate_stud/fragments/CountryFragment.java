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

public class CountryFragment extends Fragment implements CountryAdapter.CountryClickListener {

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
        adapter = new CountryAdapter(this);
        binding.countryList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.countryList.setAdapter(adapter);

        viewModel.getCountries().observe(getViewLifecycleOwner(), adapter::submit);
        binding.countryFilterGroup.check(R.id.filter_mountain);
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
}
