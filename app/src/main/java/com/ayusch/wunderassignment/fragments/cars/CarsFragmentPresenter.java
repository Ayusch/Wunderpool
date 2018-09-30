package com.ayusch.wunderassignment.fragments.cars;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ayusch.wunderassignment.contracts.CarsFragmentContract;
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;
import com.ayusch.wunderassignment.utils.Connectivity;

public class CarsFragmentPresenter implements CarsFragmentContract.Presenter {

    private CarsFragmentContract.View mView;
    private CarsFragmentContract.Interactor mInteractor;

    public CarsFragmentPresenter(CarsFragmentContract.View view) {
        mInteractor = new CarsFragmentInteractor(this);
        mView = view;
    }


    @Override
    public void loadCarsP(boolean shouldSaveData) {
        if (Connectivity.isConnected())
            mInteractor.loadCarsI(shouldSaveData);
        else
            mView.showApiErrorV("Please check your internet connection !!");

    }

    @Override
    public void showCarsInfoP(CarsInfoResponse carsInfoResponse) {
        mView.showCarsInfoV(carsInfoResponse);
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

    @Override
    public void unsubscribeP() {
        mInteractor.unsubscribeI();
    }

    @Override
    public void showTutorialP(Activity activity, RecyclerView rvCars, View mView) {
        mInteractor.showTutorialI(activity, rvCars, mView);
    }

    @Override
    public void askWritePermissionP(Activity activity) {
        mInteractor.askWritePermissionI(activity);
    }

    @Override
    public void writePermissionGrantedP() {
        mView.writePermissionGrantedV();
    }

    @Override
    public void writePermissionDeniedP() {
        mView.writePermissionDeniedV();
    }
}
