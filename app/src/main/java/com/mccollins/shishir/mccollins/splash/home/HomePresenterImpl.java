package com.mccollins.shishir.mccollins.splash.home;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.mccollins.shishir.mccollins.splash.api.DataManager;
import com.mccollins.shishir.mccollins.splash.model.MainData;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class HomePresenterImpl implements HomePresenter {

    private HomeView homeView;
    private DataManager dataManager;

    HomePresenterImpl(HomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void doCallApi() {
        homeView.showProgress();
        dataManager = new DataManager(this);
        homeView.prepareForCurrentLocation(dataManager);
        try {
            dataManager.callApi(Utility.TOUR_LIST, new JSONObject().put("email", homeView.getEmail()));
        } catch (JSONException e) {
            Log.e("doCallApi", e.toString());
        }
    }

    @Override
    public Location getLocation() {
        return dataManager.getCurrentLocation();
    }

    @Override
    public void response(Object object, boolean isSuccess) {
        homeView.hideProgress();

        if (object != null && isSuccess) {
            MainData mainData = new Gson().fromJson(object.toString(), MainData.class);
            homeView.setList(mainData);
        } else {
            homeView.showMessage("Sorry No Data!");
            Log.e("response : ", "some error occured for list arraction api");
        }
    }
}
