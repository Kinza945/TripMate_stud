package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kynzai.tripmate_stud.AppGraph;
import com.kynzai.tripmate_stud.R;
import com.kynzai.tripmate_stud.domain.model.Country;
import com.kynzai.tripmate_stud.presentation.viewmodel.CountryViewModel;

public class CountryDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_detail, container, false);
        TextView title = view.findViewById(R.id.detail_title);
        TextView subtitle = view.findViewById(R.id.detail_subtitle);
        TextView highlights = view.findViewById(R.id.detail_highlights);

        String id = getArguments() != null ? getArguments().getString("countryId", "") : "";
        CountryViewModel vm = AppGraph.getInstance(requireContext()).provideCountryViewModel();
        Country country = null;
        if (vm.getCountries().getValue() != null) {
            for (Country c : vm.getCountries().getValue()) {
                if (c.getId().equals(id)) {
                    country = c;
                    break;
                }
            }
        }
        if (country != null) {
            title.setText(country.getName());
            subtitle.setText(country.getDescription());
            highlights.setText(country.getHighlights().toString());
        }
        return view;
    }
}
