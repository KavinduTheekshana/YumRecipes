package com.example.yum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPassword extends Fragment {

    private MaterialCardView btn_update_password;
    private TextView go_back,alert_box;
    private EditText new_password,re_new_password;

    public ResetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);

        //UI Declare
        go_back = v.findViewById(R.id.password_reset_back_btn);
        new_password = v.findViewById(R.id.password_reset_new_password);
        re_new_password = v.findViewById(R.id.password_reset_re_new_password);
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

                String password_re = re_new_password.getText().toString().trim();
                String password_new = new_password.getText().toString().trim();

                if (ValidateUserData.update_password_validate(password_re,password_new)){

                    if (ValidateUserData.check_password_validate(password_re,password_new)){

                        reserPassword();

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

    private void reserPassword() {

        if (!re_new_password.getText().toString().isEmpty() && !new_password.getText().toString().isEmpty()){

            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().UpdatePasswordController(sharedPreferences.getString("user_id","0"),new_password.getText().toString().trim(),re_new_password.getText().toString().trim()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading
                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){

                            Account account = new Account();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                            fragmentTransaction.replace(R.id.container,account).commit();

                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                        }else if(jsonObject.getString("code").equals("0")){
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }


                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }



    }
}
