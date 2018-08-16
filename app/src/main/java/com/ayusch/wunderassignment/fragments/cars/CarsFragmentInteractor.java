package com.ayusch.wunderassignment.fragments.cars;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.callbacks.Callback;
import com.ayusch.wunderassignment.contracts.CarsFragmentContract;
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;

import com.ayusch.wunderassignment.task.CarsTask;
import com.ayusch.wunderassignment.utils.AppPrefs;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import rx.Subscription;

public class CarsFragmentInteractor implements CarsFragmentContract.Interactor {
    private CarsFragmentContract.Presenter mPresenter;
    private Subscription subscription;

    public CarsFragmentInteractor(CarsFragmentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void askWritePermissionI(Activity activity) {
        if (Nammu.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            mPresenter.writePermissionGrantedP();
        else {
            Nammu.askForPermission(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionCallback() {
                @Override
                public void permissionGranted() {
                    mPresenter.writePermissionGrantedP();
                }

                @Override
                public void permissionRefused() {
                    mPresenter.writePermissionDeniedP();
                }
            });
        }
    }


    @Override
    public void loadCarsI(boolean shouldSaveData) {
        mPresenter.showDialogP();
        subscription = CarsTask.Companion.getCarsInfo(shouldSaveData, new Callback<CarsInfoResponse>() {
            @Override
            public void returnResult(CarsInfoResponse carsInfoResponse) {
                mPresenter.hideDialogP();
                mPresenter.showCarsInfoP(carsInfoResponse);

            }

            @Override
            public void onError(Throwable error) {
                mPresenter.hideDialogP();
                mPresenter.showApiErrorP(error);
            }
        });
    }

    @Override
    public void unsubscribeI() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void showTutorialI(Activity activity, RecyclerView rvCars, View fragmentView) {
        TapTargetView.showFor(activity,                 // `this` is an Activity
                TapTarget.forView(fragmentView.findViewById(R.id.rv_cars), "Click the dropdown arrow to view complete information about the car / Click on the maps section to view map", "Designed and developed by Ayusch")
                        // All options below are optional
                        .outerCircleColor(R.color.colorAccent)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(android.R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(android.R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(android.R.color.black)  // Specify the color of the description text
                        .textColor(android.R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(android.R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(ContextCompat.getDrawable(activity, R.drawable.ic_wunder_logo))                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        AppPrefs.setShouldShowTutorial(false);
                    }
                });
    }


}
