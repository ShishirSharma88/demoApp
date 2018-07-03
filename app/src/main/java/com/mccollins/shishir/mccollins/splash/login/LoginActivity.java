package com.mccollins.shishir.mccollins.splash.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    private ProgressBar progressBar;
    private LoginPresenterImpl loginPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.edittext_username);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);

        registerTextView = (TextView) findViewById(R.id.textview_registration_page);
        registerTextView.setOnClickListener(this);

        loginPresenterImpl = new LoginPresenterImpl(this);
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void callNextScreen(Class nextScreen) {
        Intent intent = new Intent(this, nextScreen);
        startActivity(intent);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(JSONObject jsonObject) {
        // Reason for doing this here is we need context to save data
        // Here we can improve by implementing in other way but cause time is limited
        // leaving as of now
        try {
            Utility utility = Utility.getInstance();
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                utility.setPrefs(key, jsonObject.getString(key), this);
            }
        }catch (JSONException e) {
            Log.i("setData : error-> ", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if (Utility.getInstance().isOnline(this)) {
            loginPresenterImpl.onClick(v,
                    userNameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        } else {
            showMessage("No Internet!");
        }
    }
}
