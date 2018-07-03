package com.mccollins.shishir.mccollins.splash.login;

import org.json.JSONArray;
import org.json.JSONObject;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void callNextScreen(Class nextScreen);
    void showMessage(String text);
    void setData(JSONObject data);
}
