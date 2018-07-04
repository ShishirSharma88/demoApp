package com.mccollins.shishir.mccollins.splash.base;

import android.location.Location;

import com.mccollins.shishir.mccollins.splash.listener.TaskListener;

import org.json.JSONObject;

public interface BaseInteractor {
    void callApi(String url, JSONObject object);
    Location getCurrentLocation();
}
