package com.mccollins.shishir.mccollins.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.widget.Toast;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

public class SplashActivity extends Activity implements SplashView, ActivityCompat.OnRequestPermissionsResultCallback {

    private SplashImpl splashImplementaion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        splashImplementaion = new SplashImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashImplementaion.screenToCall();
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
    public void checkForPermissons() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        } else {
            splashImplementaion.nowContinue();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allGranted = false;

        for (int permission : grantResults) {
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }
        }

        if (allGranted) {
            splashImplementaion.nowContinue();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
