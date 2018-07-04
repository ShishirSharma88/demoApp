package com.mccollins.shishir.mccollins.splash;

public interface SplashView {
    void newScreen(Class nextClass);
    boolean checkDataExist();
    void checkForPermissions();
}
