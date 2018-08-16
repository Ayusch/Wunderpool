package com.ayusch.wunderassignment.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.ayusch.wunderassignment.R;
import com.ayusch.wunderassignment.fragments.cars.CarsFragment;
import com.ayusch.wunderassignment.fragments.map.MapFragment;
import com.ayusch.wunderassignment.utils.ActivityUtils;
import com.ayusch.wunderassignment.utils.Const;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import pl.tajchert.nammu.Nammu;

public class MainActivity extends BaseBottomNavigationActivity {


    Fragment fragment;
    FragmentManager supportFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Unbinder from base activity
        unbinder = ButterKnife.bind(this);

        supportFragmentManager = getSupportFragmentManager();
        fragment = supportFragmentManager.findFragmentById(R.id.content_frame);

        if (fragment == null) {
            fragment = CarsFragment.getInstance();
            ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.content_frame, Const.CARS_FRAGMENT);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onMapsFragmentSelected(MenuItem item) {
        fragment = MapFragment.getInstance();
         ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.content_frame, Const.MAP_FRAGMENT);
        //ActivityUtils.viewFragment(item.getItemId(),navigation,supportFragmentManager,fragment,Const.MAP_FRAGMENT);
    }

    @Override
    protected void onCarsFragmentSelected(MenuItem item) {
        fragment = CarsFragment.getInstance();
         ActivityUtils.addFragmentToActivity(supportFragmentManager, fragment, R.id.content_frame, Const.CARS_FRAGMENT);
        //ActivityUtils.viewFragment(item.getItemId(),navigation,supportFragmentManager,fragment,Const.CARS_FRAGMENT);
    }

    @Override
    public void onBackPressed() {
        int entryCount = supportFragmentManager.getBackStackEntryCount();
        if (entryCount == 1) {
            finish();
        } else
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
