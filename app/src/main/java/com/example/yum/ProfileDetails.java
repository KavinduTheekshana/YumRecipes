package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileDetails extends Fragment {
    MaterialCardView profile_details_update_password,profile_details_update_details;

    private EditText user_name,user_email;
    private TextView go_back,alert_box;
    private ImageView user_profile_pic;

    public ProfileDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_details, container, false);

        //UI Declare
        profile_details_update_password=v.findViewById(R.id.profile_details_update_password);
        user_name = v.findViewById(R.id.profile_details_edit_user_name);
        user_email = v.findViewById(R.id.profile_details_edit_user_email);
        profile_details_update_details = v.findViewById(R.id.profile_details_edit_edit_details);
        go_back = v.findViewById(R.id.profile_details_edit_back);
        user_profile_pic = v.findViewById(R.id.profile_details_edit_user_name_image);
        alert_box = v.findViewById(R.id.profile_details_tv_alert);


        //Current Password
        profile_details_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentPassword currentPassword = new CurrentPassword();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,currentPassword).commit();
            }
        });

        //Account
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,account).commit();

            }
        });

        profile_details_update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = user_name.getText().toString().trim();
                String email = user_email.getText().toString().trim();

                if (ValidateUserData.update_profile_validate(name,email)){

                }else{
                    alert_box.setText("Please Fill All Details");
                }
            }
        });

        return v;

    }
}
