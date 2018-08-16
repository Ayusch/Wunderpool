package com.ayusch.wunderassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ayusch.WunderApplication;

public final class Connectivity {

    /**
     * Get the network info
     *
     * @return Network info
     */
    public static NetworkInfo getNetworkInfo() {
        final Context context = WunderApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @return Has network connected or not
     */
    public static boolean isConnected() {
        NetworkInfo info = getNetworkInfo();
        return info != null && info.isConnected();
    }

}