package com.standardyze.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.standardyze.MainActivity;
import com.standardyze.R;
import com.standardyze.utils.SharedPrefManager;

public class splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    SharedPrefManager sharedPrefManager;
    boolean isLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPrefManager = new SharedPrefManager(this);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing Splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                isLoggedIn =sharedPrefManager.isLoggedIn();
                if (isLoggedIn){
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent i = new Intent(splash.this, login.class);
                    startActivity(i);
                    // close this activity

                }
                splash.this.finish();

            }
        }, SPLASH_TIME_OUT);

    }

}

