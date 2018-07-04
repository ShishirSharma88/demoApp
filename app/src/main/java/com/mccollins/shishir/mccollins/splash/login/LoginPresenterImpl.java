package com.mccollins.shishir.mccollins.splash.login;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.api.DataManager;
import com.mccollins.shishir.mccollins.splash.home.HomeActivity;
import com.mccollins.shishir.mccollins.splash.registration.RegistrationActivity;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onClick(View view, String username, String password) {
        switch (view.getId()) {

            case R.id.button_login:
                doValidate(username, password);
                break;
            case R.id.textview_registration_page:
                loginView.callNextScreen(RegistrationActivity.class);
                break;

            default:
                loginView.showMessage("Did not recognize this button");
                break;
        }
    }

    @Override
    public void doLogin(String username, String password) {
        loginView.showProgress();
        try {
            JSONObject object = new JSONObject();
            object.put("email", username);
            object.put("password", password);

            DataManager dataManager = new DataManager(this);
            dataManager.callApi(Utility.LOGIN_URL, object);

        } catch (JSONException ex) {
            loginView.hideProgress();
            Log.e("doLogin : ", ex.toString());
        }
    }

    @Override
    public void doValidate(String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            doLogin(username, password);
        } else {
            loginView.showMessage("Please check your credentials!!");
        }
    }

    @Override
    public void response(Object object, boolean isSuccess) {
        loginView.hideProgress();

        String message = "Login Failed!";
        JSONObject data = null;
        try {
            if (object != null) {
                JSONObject jsonObject = new JSONObject(object.toString());
                isSuccess = !jsonObject.optString("iserror").equals("Yes");
                message = jsonObject.optString("message");
                data = jsonObject.getJSONArray("data").getJSONObject(0);
            }
        } catch (Exception e) {
            Log.e("onSuccess : error", e.toString());
        }

        if (isSuccess) {
            loginView.setData(data);
            loginView.showMessage("Login Successful!");
            loginView.callNextScreen(HomeActivity.class);
        } else {
            loginView.showMessage(message);
        }
    }
}
