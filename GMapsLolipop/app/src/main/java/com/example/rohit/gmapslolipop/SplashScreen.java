package com.example.rohit.gmapslolipop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        goToMainActivity object  = new goToMainActivity();
        object.start();
        setContentView(R.layout.activity_splash_screen);
    }

    private class goToMainActivity extends Thread {
        public void run() {
            try {
                sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            SplashScreen.this.finish();
        }
    }
}
