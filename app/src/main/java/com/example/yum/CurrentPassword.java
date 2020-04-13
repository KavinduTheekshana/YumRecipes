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
public class CurrentPassword extends Fragment {

    private MaterialCardView current_password_submit;
    private TextView go_back,alert_box;
    private EditText current_password;


    public CurrentPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_current_password, container, false);

        //UI Declare
        current_password_submit=v.findViewById(R.id.current_password_submit);
        go_back = v.findViewById(R.id.current_password_tv_back_btn);
        current_password = v.findViewById(R.id.current_password_et_password);
        alert_box = v.findViewById(R.id.current_password_tv_alert);


        //submit password
        current_password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = current_password.getText().toString();

                if (!password.isEmpty()){

                    CheckCurrentPassword();

                }else {
                    alert_box.setText("Please Enter Your Password");
                }
            }
        });


        //go back profile
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDetails profileDetails = new ProfileDetails();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                fragmentTransaction.replace(R.id.container,profileDetails).commit();
            }
        });



        return v;
    }

    private void CheckCurrentPassword() {


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
            RequestQueue requestQueue= Volley.newRequestQueue(getContext());
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().CurrentPassword(sharedPreferences.getString("user_id","0"),current_password.getText().toString().trim()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading
                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){
                            ResetPassword resetPassword = new ResetPassword();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
                            fragmentTransaction.replace(R.id.container,resetPassword).commit();
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();


                        }
                        else{
                            alert_box.setText(jsonObject.getString("msg"));
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    //hide loading
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);


    }
}
