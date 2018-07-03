package com.mccollins.shishir.mccollins.splash.registration;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.api.DataManager;
import com.mccollins.shishir.mccollins.splash.home.HomeActivity;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private RegistrationView registrationView;
    private DataManager dataManager;

    RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void doValidate(String firstName,
                           String lastName,
                           String mobileNumber,
                           int gender,
                           String password,
                           String email,
                           String dob) {
        if (!TextUtils.isEmpty(firstName)
                && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(mobileNumber)
                && !TextUtils.isEmpty(password)
                && (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())) {

            doCallApi(firstName, lastName, mobileNumber, gender, password, email, dob);
        } else {
            registrationView.showMessage("Please check the data again, some field might be missing or wrongly formatted!!");
        }
    }

    @Override
    public void doCallApi(String firstName,
                          String lastName,
                          String mobileNumber,
                          int gender,
                          String password,
                          String email,
                          String dob) {

        try {
            registrationView.showProgress();
            dataManager = new DataManager(this);
            requestForRegistration(Utility.REGISTER_URL, firstName, lastName, email, password, mobileNumber, dob, (gender == R.id.male) ? "Male" : "Female");
        } catch (JSONException ex) {
            Log.e("doApiCall : ", ex.toString());
            registrationView.hideProgress();
        }
    }

    public void requestForRegistration(String url,
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

        dataManager.callApi(url, object);
    }

    @Override
    public void onClick(String firstName,
                        String lastName,
                        String mobileNumber,
                        int gender,
                        String password,
                        String email,
                        String dob) {
        doValidate(firstName,
                lastName,
                mobileNumber,
                gender,
                password,
                email,
                dob);
    }

    @Override
    public void response(Object o, boolean isSuccess) {
        registrationView.hideProgress();
        String message = "Login Failed!";

        try {
            if (o != null) {
                JSONObject jsonObject = new JSONObject(o.toString());
                isSuccess = !jsonObject.optString("iserror").equals("Yes");
                message = jsonObject.optString("message");
            }
        } catch (Exception e) {
            Log.e("onSuccess : error", e.toString());
        }

        registrationView.showMessage(message);

        if (isSuccess) {
            registrationView.setData();
            registrationView.nextScreen(HomeActivity.class);
        }

    }
}
