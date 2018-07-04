package com.mccollins.shishir.mccollins.splash.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mccollins.shishir.mccollins.R;
import com.mccollins.shishir.mccollins.splash.api.DataManager;
import com.mccollins.shishir.mccollins.splash.model.MainData;
import com.mccollins.shishir.mccollins.splash.model.SingleShotLocationProvider;
import com.mccollins.shishir.mccollins.splash.utility.Utility;

import java.util.ArrayList;

public class HomeActivity extends Activity implements HomeView {

    private RecyclerView touristPlaceList;
    private HomePresenterImpl homePresenterImpl;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        touristPlaceList = (RecyclerView) findViewById(R.id.recycler_placelist);
        layoutManager = new LinearLayoutManager(this);
        touristPlaceList.setLayoutManager(layoutManager);

        progressBar = (ProgressBar) findViewById(R.id.progress_tourlist);

        homePresenterImpl = new HomePresenterImpl(this);
        homePresenterImpl.doCallApi();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    public String getEmail() {
        return Utility.getInstance().getPrefs("email", this);
    }

    @Override
    public void setList(MainData mainData) {
        touristPlaceList.setAdapter(new TourListAdapter(mainData, this, homePresenterImpl));
    }

    @Override
    public void prepareForCurrentLocation(DataManager dataManager) {
        SingleShotLocationProvider.requestSingleUpdate(this, dataManager);
    }
}
