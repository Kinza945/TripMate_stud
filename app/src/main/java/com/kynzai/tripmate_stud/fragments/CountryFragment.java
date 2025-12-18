package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.AppGraph;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.presentation.adapter.CountryAdapter;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;

import java.util.List;

public class CountryFragment extends Fragment implements CountryAdapter.CountryClickListener {

    private CountryViewModel viewModel;
    private CountryAdapter adapter;
    private TextView remoteCurrency;
    private TextView remoteWeather;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        viewModel = AppGraph.getInstance(requireContext()).provideCountryViewModel();
        adapter = new CountryAdapter(this);

        RecyclerView recyclerView = view.findViewById(R.id.country_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        remoteCurrency = view.findViewById(R.id.remote_currency);
        remoteWeather = view.findViewById(R.id.remote_weather);

        observeData();
        return view;
    }

    private void observeData() {
        viewModel.getCountries().observe(getViewLifecycleOwner(), this::renderCountries);
        viewModel.getRemoteInfo().observe(getViewLifecycleOwner(), info -> {
            if (info != null) {
                remoteCurrency.setText(info.getCurrencyRate());
                remoteWeather.setText(info.getWeather());
            }
        });
    }

    private void renderCountries(List<Country> countries) {
        adapter.submit(countries);
    }

    @Override
    public void onCountryClicked(Country country) {
        Bundle args = new Bundle();
        args.putString("countryId", country.getId());
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_country_to_countryDetail, args);
    }

    @Override
    public void onFavoriteClicked(Country country) {
        viewModel.toggleFavorite(country.getId());
    }
}
