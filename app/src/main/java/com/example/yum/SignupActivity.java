package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {

    private MaterialCardView btn_signup;
    private TextView go_back, alert_box;
    private EditText user_name,user_email,user_password,user_re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //UI Declare
        ui_declare();

        hideSystemUI();

        //go back
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_name.getText().toString();
                String email = user_email.getText().toString();
                String password = user_password.getText().toString().trim();
                String re_password = user_re_password.getText().toString().trim();

                if (ValidateUserData.signup_validate(name,email,password,re_password))
                {
                    if (ValidateUserData.isValidmail(email))
                    {
                        if (ValidateUserData.check_password_validate(password,re_password))
                        {
                            signUp();
                        }else{
                            alert_box.setText("Your Password isn't Same");
                        }
                    }else {
                        alert_box.setText("Please Check Your Email");
                    }
                }else{
                    alert_box.setText("Please Complete All Details");
                }
                //startActivity(new Intent(getApplicationContext(),WellDoneActivity.class));
            }
        });
    }

    private void signUp() {

        try {

            String url = new Stables().SignUp(user_name.getText().toString().trim(),user_email.getText().toString().trim(),user_password.getText().toString().trim());
            System.out.println(url);
            RequestQueue requestQueue= Volley.newRequestQueue(SignupActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(SignupActivity.this,LoginOrSignUp.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(SignupActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                }else if(jsonObject.getString("code").equals("0")){
                                    Toast.makeText(SignupActivity.this, "Email Address is Already Used.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void ui_declare() {
        go_back = findViewById(R.id.signup_tv_back);
        btn_signup = findViewById(R.id.signup_btn_signup);

        user_name = findViewById(R.id.signup_et_user_name);
        user_email = findViewById(R.id.signup_et_user_email);
        user_password = findViewById(R.id.signup_et_user_password);
        user_re_password = findViewById(R.id.signup_et_user_re_password);
        alert_box = findViewById(R.id.signup_tv_alert);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


}
