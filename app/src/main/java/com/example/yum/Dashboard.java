package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment {
    MaterialCardView breakfast;
    private EditText search_box;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //UI Declaration
        breakfast = v.findViewById(R.id.breakfast);
        search_box = v.findViewById(R.id.dashboard_et_search);


        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleCategory singleCategory = new SingleCategory();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,singleCategory).commit();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
