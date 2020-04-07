package com.example.yum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPassword extends Fragment {

    private MaterialCardView btn_update_password;
    private TextView go_back,alert_box;
    private EditText current_password,new_password;

    public ResetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        //UI Declare
        go_back = v.findViewById(R.id.password_reset_back_btn);
        current_password = v.findViewById(R.id.password_reset_new_password);
        new_password = v.findViewById(R.id.password_reset_re_new_password);
        btn_update_password = v.findViewById(R.id.password_reset_update_password);
        alert_box = v.findViewById(R.id.password_reset_tv_alert);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDetails profileDetails = new ProfileDetails();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,profileDetails).commit();
            }
        });

        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = current_password.getText().toString().trim();
                String password_new = new_password.getText().toString().trim();

                if (ValidateUserData.update_password_validate(password,password_new)){

                    if (ValidateUserData.check_password_validate(password,password_new)){

                    }else {
                        alert_box.setText("Password Not Same");
                    }
                }else {
                    alert_box.setText("Please Fill All Details");
                }
            }
        });




        // Inflate the layout for this fragment
        return v;
    }
}
