package com.ayusch.wunderassignment.fragments.map;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.contracts.MapFragmentContract;
import com.ayusch.wunderassignment.fragments.base.BaseFragment;
import com.ayusch.wunderassignment.utils.AppPrefs;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.orhanobut.logger.Logger;
import com.roger.catloadinglibrary.CatLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment implements MapFragmentContract.View, OnMapReadyCallback {

    @BindView(R.id.map)
    MapView mMapView;

    MapFragmentContract.Presenter mPresenter;

    GoogleMap mMap;
    View mView;
    CatLoadingView catLoadingView;

    public static MapFragment getInstance() {
        return new MapFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, mView);
        catLoadingView = new CatLoadingView();
        catLoadingView.setCanceledOnTouchOutside(false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new MapFragmentPresenter(this);
        mPresenter.askLocationPermissionP(activity);
    }

    @Override
    public void locationPermissionGrantedV() {
        mPresenter.initMapP();
    }

    @Override
    public void locationPermissionDeniedV() {
        mPresenter.askLocationPermissionP(activity);
    }

    @Override
    public void showApiErrorV(String error) {
        Toast.makeText(activity, "Some error ocurred\n" + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialogV() {
        if (catLoadingView != null) {
            catLoadingView.show(getFragmentManager(), "");
        }
    }

    @Override
    public void hideDialogV() {
        if (catLoadingView != null) {
            catLoadingView.dismiss();
        }
    }

    @Override
    public void initMapV() {
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mPresenter.showDialogP();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Logger.i("MAP IS READY");
        mPresenter.hideDialogP();
        MapsInitializer.initialize(activity);
        mMap = googleMap;
        mPresenter.setupMapP(activity, googleMap);
        mPresenter.setUserLocationP(activity, googleMap);
    }
}
