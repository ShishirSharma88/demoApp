package com.mccollins.shishir.mccollins.splash.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

public class RegistrationActivity extends Activity implements RegistrationView, View.OnClickListener {

    private EditText firstNameEditText;
    private EditText lastnameEditText;
    private EditText mobileNumberEditText;
    private EditText dateOfBirthEditText;
    private RadioGroup genderRadioGroup;
    private EditText emailEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private RegistrationPresenterImpl registrationpresenterImpl;
    private Button registerButton;
    private DateInputMask dateInputMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firstNameEditText = (EditText) findViewById(R.id.edittext_firstname);
        lastnameEditText = (EditText) findViewById(R.id.edittext_lastname);
        mobileNumberEditText = (EditText) findViewById(R.id.edittext_mobile_number);
        dateOfBirthEditText = (EditText) findViewById(R.id.edittext_date_of_birth);
        dateInputMask = new DateInputMask(dateOfBirthEditText);
        genderRadioGroup = (RadioGroup) findViewById(R.id.radiobutton_gender);
        emailEditText = (EditText) findViewById(R.id.edittext_email);
        passwordEditText = (EditText) findViewById(R.id.edittext_password);

        registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(this);

        registrationpresenterImpl = new RegistrationPresenterImpl(this);
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
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void nextScreen(Class nextClass) {
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void setData() {
        Utility.getInstance().setPrefs("email",
                emailEditText.getText().toString().trim(),
                this);
        Utility.getInstance().setPrefs("password",
                passwordEditText.getText().toString().trim(),
                this);
    }

    @Override
    public void onClick(View v) {
        registrationpresenterImpl.onClick(firstNameEditText.getText().toString().trim(),
                lastnameEditText.getText().toString().trim(),
                mobileNumberEditText.getText().toString().trim(),
                genderRadioGroup.getCheckedRadioButtonId(),
                passwordEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                dateOfBirthEditText.getText().toString().trim());
    }
}
