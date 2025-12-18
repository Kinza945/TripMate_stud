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
import com.kynzai.tripmate_stud.domain.model.Trip;
import com.kynzai.tripmate_stud.presentation.viewmodel.TripViewModel;

public class TravelDetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_detail, container, false);
        TextView title = view.findViewById(R.id.trip_detail_title);
        TextView subtitle = view.findViewById(R.id.trip_detail_description);
        TextView meta = view.findViewById(R.id.trip_detail_meta);

        String id = getArguments() != null ? getArguments().getString("tripId", "") : "";
        TripViewModel vm = AppGraph.getInstance(requireContext()).provideTripViewModel();
        Trip trip = null;
        if (vm.getTrips().getValue() != null) {
            for (Trip t : vm.getTrips().getValue()) {
                if (t.getId().equals(id)) {
                    trip = t;
                    break;
                }
            }
        }
        if (trip != null) {
            title.setText(trip.getTitle());
            subtitle.setText(trip.getDescription());
            meta.setText(getString(R.string.trip_meta_format, trip.getCountry(), trip.getDate(), trip.getRating()));
        }
        return view;
    }
}
