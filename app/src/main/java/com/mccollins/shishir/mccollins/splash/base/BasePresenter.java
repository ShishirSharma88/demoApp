package com.mccollins.shishir.mccollins.splash.base;

public interface BasePresenter<T> {
    void response(T t, boolean isSuccess);
}
