package com.mccollins.shishir.mccollins.splash.registration;

import com.mccollins.shishir.mccollins.splash.base.BasePresenter;

public interface RegistrationPresenter extends BasePresenter {
    void doValidate(String firstName,
                    String lastName,
                    String mobileNumber,
                    int gender,
                    String password,
                    String email,
                    String dob);

    void doCallApi(String firstName,
                   String lastName,
                   String mobileNumber,
                   int gender,
                   String password,
                   String email,
                   String dob);

    void onClick(String firstName,
                 String lastName,
                 String mobileNumber,
                 int gender,
                 String password,
                 String email,
                 String dob);
}
