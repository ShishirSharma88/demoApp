package com.mccollins.shishir.mccollins.splash;

import com.mccollins.shishir.mccollins.splash.base.BasePresenter;

public interface SplashPresenter extends BasePresenter {
    void screenToCall();
    void holdScreen(Class activityToLaunch);
    void nowContinue();
}
