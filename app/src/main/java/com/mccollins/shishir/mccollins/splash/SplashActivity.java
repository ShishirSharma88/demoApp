package com.mccollins.shishir.mccollins.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

public class SplashActivity extends Activity implements SplashView {

    private SplashImpl splashImplementaion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        splashImplementaion = new SplashImpl(this);
    }

    @Override
    public void newScreen(Class activityToLaunch) {
        Intent intent = new Intent(this, activityToLaunch);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean checkDataExist() {
        return Utility.getInstance().getPrefs("email", this) != null;
    }

    @Override
    public void onBackPressed() {

    }
}
