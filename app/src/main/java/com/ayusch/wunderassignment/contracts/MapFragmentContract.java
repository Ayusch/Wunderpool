package com.ayusch.wunderassignment.contracts;

import android.app.Activity;

import com.google.android.gms.maps.GoogleMap;

public interface MapFragmentContract {
    interface View {
        void initMapV();

        void locationPermissionGrantedV();

        void locationPermissionDeniedV();

        void showApiErrorV(String error);

        void showDialogV();

        void hideDialogV();
    }

    interface Presenter {
        void initMapP();

        void setupMapP(Activity activity, GoogleMap googleMap);

        void setUserLocationP(Activity activity, GoogleMap googleMap);

        void askLocationPermissionP(Activity activity);

        void locationPermissionGrantedP();

        void locationPermissionDeniedP();

        void showApiErrorP(Throwable error);

        void showDialogP();

        void hideDialogP();
    }

    interface Interactor {

        void setupMapI(Activity activity, GoogleMap googleMap);

        void setUserLocationI(Activity activity, GoogleMap googleMap);

        void askLocationPermissionI(Activity activity);

    }
}
