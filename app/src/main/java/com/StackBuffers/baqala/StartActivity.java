package com.StackBuffers.baqala;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.StackBuffers.baqala.HelperClasses.UserSessionManager;

public class StartActivity extends AppCompatActivity {


    Handler handler;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        userSessionManager = new UserSessionManager(StartActivity.this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userSessionManager.isLoggedIn()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                } else {

                    Intent intent = new Intent(StartActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);

    }
}
