package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class SignupActivity extends AppCompatActivity {

    private MaterialCardView btn_signup;
    private TextView go_back,go_login, alert_box;
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
                startActivity(new Intent(getApplicationContext(),LoginOrSignUp.class));
            }
        });

        //go login
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
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
                            startActivity(new Intent(getApplicationContext(),WellDoneActivity.class));
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

    private void ui_declare() {
        go_back = findViewById(R.id.signup_tv_back);
        go_login = findViewById(R.id.signup_tv_login);
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
