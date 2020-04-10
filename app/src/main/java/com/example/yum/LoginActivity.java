package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class LoginActivity extends AppCompatActivity {

    private MaterialCardView btn_login;
    private TextView go_back,go_signup,forget_password,alert_box;
    private EditText user_email,user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declare UI
        ui_declare();

        hideSystemUI();

        //go back
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginOrSignUp.class));
            }
        });

        //go signup
        go_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        //forget password dashboard
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
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
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
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

    private void ui_declare() {
        go_back = findViewById(R.id.login_tv_back_select);
        go_signup = findViewById(R.id.login_tv_go_signup);
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
