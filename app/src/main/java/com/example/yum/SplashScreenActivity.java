package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yum.Common.Stables;

import org.json.JSONObject;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        //full Screen Mode
        hideSystemUI();



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.TYPE_INPUT_METHOD_DIALOG);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);

                if(sharedPreferences!=null){
                    if(sharedPreferences.getString("user_id","0").equals("0")){
                        //Login
                        Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
                        startActivity(homeIntent);
                        finish();
                    }else{
                        //Check user id availabl in db
                        RequestQueue requestQueue= Volley.newRequestQueue(SplashScreenActivity.this);
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, new Stables().getCheckLoginController(sharedPreferences.getString("user_id","0")), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //hide loading
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    if(jsonObject.getString("code").equals("1")){
                                        Intent homeIntent = new Intent(SplashScreenActivity.this,DashboardActivity.class);
                                        startActivity(homeIntent);
                                        finish();

                                    }else if(jsonObject.getString("code").equals("2")){
                                        //blocked user
                                        Toast.makeText(SplashScreenActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                        Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
                                        startActivity(homeIntent);
                                        finish();


                                    }
                                    else{
                                        Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
                                        startActivity(homeIntent);
                                        finish();
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //hide loading
                                Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
                                startActivity(homeIntent);
                                finish();
                            }
                        });

                        requestQueue.add(stringRequest);
                    }

                }else{

                    //Welcome
                    Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
                    startActivity(homeIntent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);



//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.TYPE_INPUT_METHOD_DIALOG);
//        setContentView(R.layout.activity_splash_screen);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent homeIntent = new Intent(SplashScreenActivity.this,LoginOrSignUp.class);
//                startActivity(homeIntent);
//                finish();
//            }
//        },SPLASH_TIME_OUT);


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
