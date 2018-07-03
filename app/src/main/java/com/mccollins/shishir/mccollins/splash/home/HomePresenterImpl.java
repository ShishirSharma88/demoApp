package com.mccollins.shishir.mccollins.splash.home;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    public void doCallApi(Context context) {
        dataManager = new DataManager(this);
        requestForTourList(Utility.TOUR_LIST, Utility.getInstance().getPrefs("email", context));
    }

    public void requestForTourList(String url,
                                   String email) {
        homeView.showProgress();
        try {
            JSONObject object = new JSONObject();
            object.put("email", email);
            dataManager.callApi(url, object);
        } catch (JSONException ex) {
            homeView.hideProgress();
            Log.e("requestForTourList : ", ex.toString());
        }
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
