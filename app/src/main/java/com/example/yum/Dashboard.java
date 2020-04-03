package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {
    LinearLayout single_category,main_dashboard;
    MaterialCardView dashboard_back_button,breakfast;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        single_category = v.findViewById(R.id.single_category);
        main_dashboard = v.findViewById(R.id.main_dashboard);
        dashboard_back_button = v.findViewById(R.id.dashboard_back_button);
        breakfast = v.findViewById(R.id.breakfast);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_dashboard.setVisibility(View.GONE);
                single_category.setVisibility(View.VISIBLE);
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
