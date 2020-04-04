package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetails extends Fragment {
    MaterialCardView profile_details_update_password;

    public ProfileDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_details, container, false);

        profile_details_update_password=v.findViewById(R.id.profile_details_update_password);
        profile_details_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPassword currentPassword = new CurrentPassword();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,currentPassword).commit();
            }
        });

        return v;

    }
}
