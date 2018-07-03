package com.mccollins.shishir.mccollins.splash.registration;

public interface RegistrationView {
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void nextScreen(Class nextClass);
    void setData();
}
