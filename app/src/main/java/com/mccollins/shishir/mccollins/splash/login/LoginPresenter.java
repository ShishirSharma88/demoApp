package com.mccollins.shishir.mccollins.splash.login;

import android.view.View;

import com.mccollins.shishir.mccollins.splash.base.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void onClick(View view, String username, String password);
    void doLogin(String username, String password);
    void doValidate(String username,String password);
}
