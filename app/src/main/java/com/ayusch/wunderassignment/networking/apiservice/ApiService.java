package com.ayusch.wunderassignment.networking.apiservice;

import com.ayusch.wunderassignment.models.cars.CarsInfoResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("wunderbucket/locations.json")
    Observable<CarsInfoResponse> getCarsInfo();
}
