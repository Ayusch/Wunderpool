package com.ayusch.wunderassignment.fragments.cars;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ayusch.WunderApplication;
import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.adapters.CarInfoAdapter;
import com.ayusch.wunderassignment.contracts.CarsFragmentContract;
import com.ayusch.wunderassignment.fragments.base.BaseFragment;
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;
import com.ayusch.wunderassignment.utils.AppPrefs;
import com.ayusch.wunderassignment.utils.Const;
import com.ayusch.wunderassignment.utils.FileUtils;
import com.orhanobut.logger.Logger;
import com.roger.catloadinglibrary.CatLoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarsFragment extends BaseFragment implements CarsFragmentContract.View {

    private CarsFragmentContract.Presenter presenter;

    @BindView(R.id.rv_cars)
    RecyclerView rvCars;

    CarInfoAdapter adapter;
    CatLoadingView catLoadingView;
    View mView;


    public static CarsFragment getInstance() {
        return new CarsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        unbinder = ButterKnife.bind(this, view);
        catLoadingView = new CatLoadingView();
        catLoadingView.setCanceledOnTouchOutside(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        presenter = new CarsFragmentPresenter(this);
        rvCars.setLayoutManager(new LinearLayoutManager(activity));
        presenter.askWritePermissionP(activity);
    }

    /* ------- PERMISSION RESULTS ---------- */

    @Override
    public void writePermissionGrantedV() {
        // if permission is granted then persist data
        // app is persisting data  ****** false by default *******

        AppPrefs.setIsPersistingData(true);
        presenter.loadCarsP(true);
    }

    @Override
    public void writePermissionDeniedV() {
        AppPrefs.setIsPersistingData(false);
        Toast.makeText(activity, "Will not persist data", Toast.LENGTH_LONG).show();
        presenter.loadCarsP(false);
    }


    // called after data is received from api
    @Override
    public void showCarsInfoV(CarsInfoResponse carsInfoResponse) {
        adapter = new CarInfoAdapter(carsInfoResponse.getPlacemarks(), carClickListener, activity);
        rvCars.setAdapter(adapter);

        // tutorial is shown----only once
        if (AppPrefs.getShouldShowTutorial())
            presenter.showTutorialP(activity, rvCars, mView);
    }

    private CarInfoAdapter.CarClickedListener carClickListener = new CarInfoAdapter.CarClickedListener() {
        @Override
        public void onCarClicked(CarsInfoResponse.PlaceMarks placeMark) {
            Toast.makeText(activity, placeMark.getName(), Toast.LENGTH_LONG).show();
        }
    };

    // for errors in api calls
    @Override
    public void showApiErrorV(String error) {
        Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
    }


    // initial dialog
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
    public void onStop() {
        super.onStop();
        presenter.unsubscribeP();
    }
}
