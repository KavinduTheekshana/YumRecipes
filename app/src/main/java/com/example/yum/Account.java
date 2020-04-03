package com.example.yum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.yum.R.id.bottom_navigation_view;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment{
    BottomNavigationView account_navigation_view;

    private LinearLayout profile_view;
    private LinearLayout profile_edit_view;
    private ImageView profile_edite;

    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        profile_view = v.findViewById(R.id.profile_layout);
        profile_edite = v.findViewById(R.id.btn_go_profilr_rdite);
        profile_edit_view = v.findViewById(R.id.profile_edite_layout);

        profile_edit_view.setVisibility(View.GONE);


        profile_edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_view.setVisibility(View.GONE);
                profile_edit_view.setVisibility(View.VISIBLE);
            }
        });


        // Inflate the layout for this fragment
        return v;


    }


}
