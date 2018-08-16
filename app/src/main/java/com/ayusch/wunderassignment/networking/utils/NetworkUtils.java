package com.ayusch.wunderassignment.networking.utils;

import com.ayusch.wunderassignment.networking.adapter.RetrofitAdapter;
import com.ayusch.wunderassignment.networking.apiservice.ApiService;

public class NetworkUtils {

    public static ApiService carInfoService;

    public static ApiService getCarInfoServiceInvokerInstance() {
        if (carInfoService == null)
            carInfoService = RetrofitAdapter.getInstance().create(ApiService.class);
        return carInfoService;
    }

}
