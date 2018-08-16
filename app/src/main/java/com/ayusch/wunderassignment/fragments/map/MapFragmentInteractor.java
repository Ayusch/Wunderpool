package com.ayusch.wunderassignment.fragments.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;

import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.callbacks.Callback;
import com.ayusch.wunderassignment.contracts.MapFragmentContract;
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;
import com.ayusch.wunderassignment.task.CarsTask;
import com.ayusch.wunderassignment.utils.ActivityUtils;
import com.ayusch.wunderassignment.utils.AppPrefs;
import com.ayusch.wunderassignment.utils.Const;
import com.ayusch.wunderassignment.utils.FileUtils;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class MapFragmentInteractor implements MapFragmentContract.Interactor, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    MapFragmentContract.Presenter mPresenter;

    public MapFragmentInteractor(MapFragmentContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    List<Marker> markersList = new ArrayList<>();
    GoogleMap mGoogleMap;

    /* ------- FIELDS FOR USER LOCATION ---------- */

    private LocationManager mLocationManager;
    public static final int LOCATION_UPDATE_MIN_DISTANCE = 10;
    public static final int LOCATION_UPDATE_MIN_TIME = 5000;

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Logger.d(String.format("%f, %f", location.getLatitude(), location.getLongitude()));
                drawMarker(location);
                mLocationManager.removeUpdates(mLocationListener);
            } else {
                Logger.d("Location is null");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    /* ------- FIELDS FOR USER LOCATION (END) ---------- */


    /* ------------- FOR PERMISSIONS ------------ */
    @Override
    public void askLocationPermissionI(Activity activity) {
        if (Nammu.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION))
            mPresenter.locationPermissionGrantedP();
        else
            Nammu.askForPermission(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, permissionCallback);
    }

    PermissionCallback permissionCallback = new PermissionCallback() {
        @Override
        public void permissionGranted() {
            mPresenter.locationPermissionGrantedP();
        }

        @Override
        public void permissionRefused() {
            mPresenter.locationPermissionDeniedP();
        }
    };
    /* ------------- PERMISSION TASKS END HERE ------------- */

    @Override
    public void setupMapI(final Activity activity, final GoogleMap googleMap) {

        if (AppPrefs.getIsPersistingData()) {
            String data = FileUtils.read(activity, Const.CAR_DATA_FILE_NAME);
            CarsInfoResponse carsInfoResponse = new Gson().fromJson(data, CarsInfoResponse.class);
            setDataOnMap(activity, googleMap, carsInfoResponse);
        } else {
            mPresenter.showDialogP();
            Toast.makeText(activity, "Data not persisted\nLoading from API", Toast.LENGTH_LONG).show();
            CarsTask.Companion.getCarsInfo(false, new Callback<CarsInfoResponse>() {
                @Override
                public void returnResult(CarsInfoResponse carsInfoResponse) {
                    mPresenter.hideDialogP();
                    Logger.d(carsInfoResponse);
                    setDataOnMap(activity, googleMap, carsInfoResponse);
                }

                @Override
                public void onError(Throwable error) {
                    Logger.e(error.getMessage());
                    mPresenter.hideDialogP();
                    mPresenter.showApiErrorP(error);
                }
            });
        }

    }

    private void setDataOnMap(final Activity activity, final GoogleMap googleMap, CarsInfoResponse carsInfoResponse) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        for (CarsInfoResponse.PlaceMarks marks : carsInfoResponse.getPlacemarks()) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(marks.getCoordinates().get(0), marks.getCoordinates().get(1))).title(marks.getAddress()).snippet(marks.getName()));
            marker.setTag(marks);
            markersList.add(marker);
            builder.include(new LatLng(marks.getCoordinates().get(0), marks.getCoordinates().get(1)));
        }

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = activity.getLayoutInflater()
                        .inflate(R.layout.car_info_inner, null);
                CarsInfoResponse.PlaceMarks placeMarks = (CarsInfoResponse.PlaceMarks) marker.getTag();

                TextView address = view.findViewById(R.id.car_address);
                TextView coordinates = view.findViewById(R.id.car_coordinates);
                TextView engineType = view.findViewById(R.id.car_engineType);
                TextView exterior = view.findViewById(R.id.car_exterior);
                TextView interior = view.findViewById(R.id.car_interior);
                TextView name = view.findViewById(R.id.car_name);
                TextView vin = view.findViewById(R.id.car_vin);
                NumberProgressBar fuel = view.findViewById(R.id.car_fuel);

                address.setText(placeMarks.getAddress());
                coordinates.setText(String.format("%s , %s", String.valueOf(placeMarks.getCoordinates().get(0)), String.valueOf(placeMarks.getCoordinates().get(1))));
                engineType.setText(placeMarks.getEngineType());
                exterior.setText(placeMarks.getExterior());
                fuel.setProgress(placeMarks.getFuel());
                interior.setText(placeMarks.getInterior());
                name.setText(placeMarks.getName());
                vin.setText(placeMarks.getVin());

                return view;
            }
        });


        LatLngBounds bounds = builder.build();
        Logger.i(googleMap.toString());

        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);

    }

    /* ---------------- USER LOCATION TASKS -------------------- */

    @Override
    public void setUserLocationI(Activity activity, GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        initMap(activity);
        getCurrentLocation(activity);
    }


    @SuppressLint("MissingPermission")
    private void initMap(Activity activity) {

        int googlePlayStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (googlePlayStatus != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(googlePlayStatus, activity, -1).show();

        } else {
            if (mGoogleMap != null) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(Activity activity) {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location = null;
        if (!(isGPSEnabled || isNetworkEnabled))
            Toast.makeText(activity, "No Network Connection", Toast.LENGTH_LONG).show();
        else {
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (isGPSEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        if (location != null) {
            Logger.d(String.format("getCurrentLocation(%f, %f)", location.getLatitude(),
                    location.getLongitude()));
            drawMarker(location);
        }
    }

    private void drawMarker(Location location) {
        if (mGoogleMap != null) {
            mGoogleMap.clear();
            LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(gps)
                    .title("Current Position"));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
        }

    }    /* ---------------- USER LOCATION TASKS END -------------------- */


    private boolean showOnlyClicked = true;

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (showOnlyClicked) {
            for (Marker extras : markersList){
                if (!extras.getId().equals(marker.getId()))
                    extras.setVisible(false);
            }
            marker.showInfoWindow();
        } else {
            for (Marker all : markersList) {
                all.setVisible(true);
            }
            marker.hideInfoWindow();
        }

        showOnlyClicked = !showOnlyClicked;

        return true;
    }


    @Override
    public void onMapClick(LatLng latLng) {
        for (Marker extras : markersList)
            if (!extras.isVisible())
                extras.setVisible(true);
    }


}
