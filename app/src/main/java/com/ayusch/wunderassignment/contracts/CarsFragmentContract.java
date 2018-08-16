package com.ayusch.wunderassignment.contracts;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;

public interface CarsFragmentContract {
    interface View {
        //void start();
        void showCarsInfoV(CarsInfoResponse carsInfoResponse);

        void showApiErrorV(String error);

        void showDialogV();

        void hideDialogV();
        void writePermissionGrantedV();

        void writePermissionDeniedV();
    }

    interface Presenter {
        void loadCarsP(boolean shouldSaveData);

        void showCarsInfoP(CarsInfoResponse carsInfoResponse);

        void showApiErrorP(Throwable error);

        void showDialogP();

        void hideDialogP();

        void unsubscribeP();

        void showTutorialP(Activity activity, RecyclerView rvCars, android.view.View mView);

        void askWritePermissionP(Activity activity);

        void writePermissionGrantedP();

        void writePermissionDeniedP();
    }

    interface Interactor {
        void loadCarsI(boolean shouldSaveData);
        void unsubscribeI();
        void showTutorialI(Activity activity, RecyclerView rvCars, android.view.View mView);
        void askWritePermissionI(Activity activity);


    }
}
