package com.ayusch.wunderassignment.fragments.map;

import android.app.Activity;

import com.ayusch.wunderassignment.contracts.MapFragmentContract;
import com.ayusch.wunderassignment.utils.Connectivity;
import com.google.android.gms.maps.GoogleMap;

public class MapFragmentPresenter implements MapFragmentContract.Presenter {
    MapFragmentContract.View mView;
    MapFragmentContract.Interactor mInteractor;

    public MapFragmentPresenter(MapFragmentContract.View mView) {
        this.mView = mView;
        mInteractor = new MapFragmentInteractor(this);
    }

    @Override
    public void initMapP() {
        if (Connectivity.isConnected())
            mView.initMapV();
        else
            mView.showApiErrorV("Please check your internet connection !!");
    }

    @Override
    public void setupMapP(Activity activity, GoogleMap googleMap) {
        mInteractor.setupMapI(activity, googleMap);
    }

    @Override
    public void setUserLocationP(Activity activity, GoogleMap googleMap) {
        mInteractor.setUserLocationI(activity, googleMap);
    }

    @Override
    public void askLocationPermissionP(Activity activity) {
        mInteractor.askLocationPermissionI(activity);
    }

    @Override
    public void locationPermissionGrantedP() {
        mView.locationPermissionGrantedV();
    }

    @Override
    public void locationPermissionDeniedP() {
        mView.locationPermissionDeniedV();
    }

    @Override
    public void showApiErrorP(Throwable error) {
        mView.showApiErrorV(error.getMessage());
    }

    @Override
    public void showDialogP() {
        mView.showDialogV();
    }

    @Override
    public void hideDialogP() {
        mView.hideDialogV();
    }
}
