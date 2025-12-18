package com.kynzai.tripmate_stud.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kynzai.tripmate_stud.adapters.AdapterListTravels;
import com.kynzai.tripmate_stud.adapters.TravelItem;
import com.kynzai.tripmate_stud.R;

import java.util.ArrayList;


public class TravelFragment extends Fragment {

    RecyclerView recyclesLos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_travel, container, false);

        recyclesLos = view.findViewById(R.id.recycle);

          ArrayList<TravelItem> travelItemList = new ArrayList<>();

          TravelItem a = new TravelItem(
          0, "Village", "30, Oxford Street Cambridge losdf 32",
                  3.1f, false, true, "loadOne");

          TravelItem b = new TravelItem(
                1, "Прогулка", "Всеволожские проспект, 15Б ",
                5.0f, true, false, "loadTwo");


          travelItemList.add(a);
          travelItemList.add(b);

       // RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//

       // recyclesLos.setLayoutManager(new LinearLayoutManager(lp));
       // recyclesLos.setLayoutParams(lp);
        AdapterListTravels adapter = new AdapterListTravels(travelItemList);
        recyclesLos.setAdapter(adapter);
        recyclesLos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setAutoMeasureEnabled(false);
        recyclesLos.setLayoutManager(llm);

        return view;

        // return super.onCreateView(inflater, container, savedInstanceState);

    }

}
