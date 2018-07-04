package com.mccollins.shishir.mccollins.splash.home;

import android.location.Location;

import com.mccollins.shishir.mccollins.splash.base.BasePresenter;

public interface HomePresenter extends BasePresenter{
    void doCallApi();
    Location getLocation();
}
