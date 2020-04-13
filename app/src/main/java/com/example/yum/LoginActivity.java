package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LoginActivity extends AppCompatActivity {

    private MaterialCardView btn_login;
    private TextView go_back,forget_password,alert_box;
    private EditText user_email,user_password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declare UI
        ui_declare();

        //full screen
        hideSystemUI();

        //progress
        progressDialog=new Stables().showLoading(this);

        //go back
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //forget password dashboard
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        //complete login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();

                if (ValidateUserData.login_validate(email,password))
                {
                    if (ValidateUserData.isValidmail(email))
                    {
                        ViewLoginActivity();
                    }else
                    {
                        alert_box.setText("Please Enter Valid Email");
                    }

                }else{
                    //Toast.makeText(LoginActivity.this,"Please Enter Details",Toast.LENGTH_SHORT).show();
                    alert_box.setText("Please Enter Details");
                }
            }
        });
    }

    private void ViewLoginActivity() {

        progressDialog.show();

        if (!user_email.getText().toString().isEmpty() && !user_password.getText().toString().isEmpty()){

            RequestQueue requestQueue= Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getLoginController(user_email.getText().toString().trim(),user_password.getText().toString().trim()), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //hide loading
                    try {
                        JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getString("code").equals("1")){
                            Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                            JSONObject userObj=jsonObject.getJSONObject("user");
                            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("user_id",userObj.getString("id"));
                            editor.putString("name",userObj.getString("name"));
                            editor.putString("email",userObj.getString("email"));
                            editor.putString("profile_pic",userObj.getString("profile_pic"));
                            editor.commit();

                            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);

                        }else if(jsonObject.getString("code").equals("2")){
                            Toast.makeText(LoginActivity.this, "Your Blocked", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    //hide loading
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
        }

        progressDialog.dismiss();

    }

    private void ui_declare() {
        go_back = findViewById(R.id.login_tv_back_select);
        forget_password = findViewById(R.id.login_tv_forget_password);
        btn_login=findViewById(R.id.login_mc_login_btn);
        user_email = findViewById(R.id.login_et_user_email);
        user_password = findViewById(R.id.login_et_user_password);
        alert_box = findViewById(R.id.login_tv_alert);
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
