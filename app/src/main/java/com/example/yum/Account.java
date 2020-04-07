package com.example.yum;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import static com.example.yum.R.id.bottom_navigation_view;
import static com.example.yum.R.id.gone;


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment{

    private ImageView profile_edit,user_profile_pic;
    private TextView user_name,user_email,user_recipes_count;


    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_account, container, false);

        //UI Declare
        profile_edit = v.findViewById(R.id.btn_go_profilr_rdite);
        user_profile_pic = v.findViewById(R.id.account_user_profile_pic);

        user_name = v.findViewById(R.id.account_tv_user_name);
        user_email = v.findViewById(R.id.account_tv_user_email);
        user_recipes_count = v.findViewById(R.id.account_tv_recipes_count);






        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDetails profileDetails = new ProfileDetails();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,profileDetails).commit();
            }
        });




        // Inflate the layout for this fragment
        return v;
    }

}
