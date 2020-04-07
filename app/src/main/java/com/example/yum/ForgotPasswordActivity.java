package com.example.yum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class ForgotPasswordActivity extends AppCompatActivity {

    private MaterialCardView btn_reset_password;
    private TextView go_back,alert_box;
    private EditText user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //UI Declare
        ui_declare();

        hideSystemUI();

        //go back
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        //submit data
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString().trim();

                if (!email.isEmpty())
                {

                }else {
                    alert_box.setText("Please Enter Your Email");
                }
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
