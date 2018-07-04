package com.mccollins.shishir.mccollins.splash;

import android.content.Intent;

import com.mccollins.shishir.mccollins.splash.home.HomeActivity;
import com.mccollins.shishir.mccollins.splash.login.LoginActivity;
import com.mccollins.shishir.mccollins.splash.registration.RegistrationActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashImpl implements SplashPresenter {

    private SplashView splashView;
    private Timer timer;

    SplashImpl(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void screenToCall() {
        splashView.checkForPermissons();
    }

    @Override
    public void holdScreen(final Class activityToLaunch) {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    splashView.newScreen(activityToLaunch);
                }
            }, 5000);
        }
    }

    @Override
    public void nowContinue() {
        holdScreen(splashView.checkDataExist() ? HomeActivity.class : LoginActivity.class);
    }

    @Override
    public void response(Object o, boolean isSuccess) {

    }
}
