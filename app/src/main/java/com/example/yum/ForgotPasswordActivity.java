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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgotPasswordActivity extends AppCompatActivity {

    private MaterialCardView btn_reset_password;
    private TextView go_back,alert_box;
    private EditText user_email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //UI Declare
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

        //submit data
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString().trim();

                if (!email.isEmpty())
                {

                    verification();


                }else {
                    alert_box.setText("Please Enter Your Email");
                }
            }
        });
    }

    private void verification() {

        progressDialog.show();

            final String url = new Stables().EmailVerification(user_email.getText().toString().trim());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader reader = null;
                    try {
                        URL urlObj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
                        con.setRequestMethod("GET");

                        StringBuilder sb = new StringBuilder();
                        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }

                        System.out.println(sb.toString());

                                JSONObject jsonObject=new JSONObject(sb.toString());

                                if(jsonObject.getString("code").equals("1")){

                                    SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("email",user_email.getText().toString().trim());
                                    editor.commit();

                                    doProcessInUiThread("Please Check Your Email Address");

                                    progressDialog.dismiss();


                                    startActivity(new Intent(ForgotPasswordActivity.this,
                                            VerificationActivity.class));
                                }
                                else{
                                    doProcessInUiThread("Cant Found Email");
                                }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();




//            RequestQueue requestQueue= Volley.newRequestQueue(ForgotPasswordActivity.this);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            System.out.println("asdsadasdasdasdasdasdasdasdasd");
//
//                            }catch(Exception e){
//                                e.printStackTrace();
//                            }
//
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(ForgotPasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            });
//            requestQueue.add(stringRequest);

    }

    void doProcessInUiThread(final String message){
        ForgotPasswordActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ui_declare() {
        user_email = findViewById(R.id.forgetpass_et_email);
        go_back = findViewById(R.id.forgetpass_tv_back);
        btn_reset_password = findViewById(R.id.forgetpass_btn_reset);
        alert_box = findViewById(R.id.forgetpass_tv_alert);
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
