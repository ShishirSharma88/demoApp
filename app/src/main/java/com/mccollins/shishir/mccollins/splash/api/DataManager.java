package com.mccollins.shishir.mccollins.splash.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mccollins.shishir.mccollins.splash.base.BaseInteractor;
import com.mccollins.shishir.mccollins.splash.base.BasePresenter;
import com.mccollins.shishir.mccollins.splash.listener.TaskListener;

import org.json.JSONException;
import org.json.JSONObject;

public class DataManager implements BaseInteractor, TaskListener {

    private BasePresenter basePresenter;
    private RequestExecutor requestExecutor;
    private TaskListener taskListener;

    public DataManager(BasePresenter basePresenter) {
        this.basePresenter = basePresenter;
        taskListener = this;
    }

    public void requestForUpdateProfile(String url,
                                        String firstName,
                                        String lastName,
                                        String email,
                                        String password,
                                        String mobileNumber,
                                        String dateOfBirth,
                                        String gender) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("fname", firstName);
        object.put("lname", lastName);
        object.put("email", email);
        object.put("mobile", mobileNumber);
        object.put("password", password);
        object.put("cpassword", password);
        object.put("dob", dateOfBirth);
        object.put("gender", gender);


    }

    public void requestForGetProfile(String url,
                                     String email) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("email", email);

        requestExecutor = new RequestExecutor(url, taskListener, object);
    }

    @Override
    public void onSuccess(@NonNull Object result) {
        basePresenter.response(result, true);
    }

    @Override
    public void onFailure(int errorMessageResource) {
        basePresenter.response(null, false);
    }

    @Override
    public void callApi(String url, JSONObject object) {
        requestExecutor = new RequestExecutor(url, taskListener, object);
        requestExecutor.execute();
    }
}
