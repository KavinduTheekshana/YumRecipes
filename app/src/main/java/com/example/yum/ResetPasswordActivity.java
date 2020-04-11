package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class ResetPasswordActivity extends AppCompatActivity {
    private MaterialCardView password_reset_update_password_login;
    private TextView password_reset_back_btn_login,password_reset_tv_alert_login;
    private EditText password_reset_new_password_login,password_reset_re_new_password_login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        //full screen
        hideSystemUI();


        //progress
        progressDialog=new Stables().showLoading(this);


        password_reset_update_password_login = findViewById(R.id.password_reset_update_password_login);
        password_reset_back_btn_login = findViewById(R.id.password_reset_back_btn_login);
        password_reset_tv_alert_login = findViewById(R.id.password_reset_tv_alert_login);
        password_reset_new_password_login = findViewById(R.id.password_reset_new_password_login);
        password_reset_re_new_password_login = findViewById(R.id.password_reset_re_new_password_login);

        password_reset_update_password_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password_re = password_reset_re_new_password_login.getText().toString().trim();
                String password_new = password_reset_new_password_login.getText().toString().trim();

                if (ValidateUserData.update_password_validate(password_re,password_new)){

                    if (ValidateUserData.check_password_validate(password_re,password_new)){

                        resetPassword();

                    }else {
                        password_reset_tv_alert_login.setText("Password Not Same");
                    }
                }else {
                    password_reset_tv_alert_login.setText("Please Fill All Details");
                }

            }
        });


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




    private void resetPassword() {
        progressDialog.show();

        try {
            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            final String url = new Stables().ResetPassword(sharedPreferences.getString("email","0"),password_reset_new_password_login.getText().toString().trim());
            RequestQueue requestQueue= Volley.newRequestQueue(ResetPasswordActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println(url);
                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(ResetPasswordActivity.this,
                                            LoginOrSignUp.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(ResetPasswordActivity.this, "Password Change Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ResetPasswordActivity.this, "Password Changing Process is Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ResetPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
        progressDialog.dismiss();

    }
}
