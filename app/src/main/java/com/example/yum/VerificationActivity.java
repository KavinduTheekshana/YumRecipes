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

public class VerificationActivity extends AppCompatActivity {
    private MaterialCardView verify_btn_verify;
    private TextView verify_tv_back,verify_tv_alert;
    private EditText verify_et_code;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //full Screen
        hideSystemUI();

        //progress
        progressDialog=new Stables().showLoading(this);


        verify_btn_verify = findViewById(R.id.verify_btn_verify);
        verify_tv_back = findViewById(R.id.verify_tv_back);
        verify_tv_alert = findViewById(R.id.verify_tv_alert);
        verify_et_code = findViewById(R.id.verify_et_code);

        verify_tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        verify_btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyCode();
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



    private void VerifyCode() {
        progressDialog.show();

        try {
            SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
            final String url = new Stables().CodeVerification(sharedPreferences.getString("email","0"),verify_et_code.getText().toString().trim());
            RequestQueue requestQueue= Volley.newRequestQueue(VerificationActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if(jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(VerificationActivity.this,
                                            ResetPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    Toast.makeText(VerificationActivity.this, "Great! Now You Can Change Password", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(VerificationActivity.this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(VerificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }

        progressDialog.dismiss();
    }
}
