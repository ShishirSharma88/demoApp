package com.mccollins.shishir.mccollins.splash.home;

import com.mccollins.shishir.mccollins.splash.model.MainData;

import java.util.ArrayList;

public interface HomeView {
    void showProgress();
    void hideProgress();
    void setList(MainData mainData);
    void showMessage(String message);
}
