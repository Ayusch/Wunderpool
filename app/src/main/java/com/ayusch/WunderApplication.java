package com.ayusch;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.ayusch.wunderassignment.utils.AppPrefs;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import pl.tajchert.nammu.Nammu;

public class WunderApplication extends MultiDexApplication {
    public static WunderApplication wunderApplication;

    public static WunderApplication getInstance(){
        return wunderApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wunderApplication = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        AppPrefs.initPref(wunderApplication);
        Nammu.init(wunderApplication);
    }
}
