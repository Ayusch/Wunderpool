package com.ayusch.wunderassignment.task

import com.ayusch.WunderApplication
import com.ayusch.wunderassignment.callbacks.Callback
import com.ayusch.wunderassignment.models.cars.CarsInfoResponse
import com.ayusch.wunderassignment.networking.utils.NetworkUtils
import com.ayusch.wunderassignment.utils.Const
import com.ayusch.wunderassignment.utils.FileUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.schedulers.Schedulers

public class CarsTask {
    companion object {

        fun getCarsInfo(shouldSaveData: Boolean, callback: Callback<CarsInfoResponse>): Subscription {
            val carsService = NetworkUtils.getCarInfoServiceInvokerInstance()
            return carsService.carsInfo
                    .map { carsInfoResponse ->
                        if (shouldSaveData)
                            saveData(carsInfoResponse)
                        carsInfoResponse;
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<CarsInfoResponse>() {
                        override fun onCompleted() {

                        }

                        override fun onError(e: Throwable) {
                            Logger.e(e.message.toString())
                            callback.onError(e)
                        }

                        override fun onNext(carsInfoResponse: CarsInfoResponse) {
                            Logger.d(carsInfoResponse)
                            callback.returnResult(carsInfoResponse)
                        }
                    })

        }

        private fun saveData(carsInfoResponse: CarsInfoResponse) {
            val gson = Gson()
            val type = object : TypeToken<CarsInfoResponse>() {

            }.type
            val json = gson.toJson(carsInfoResponse, type)
            FileUtils.create(WunderApplication.getInstance(), Const.CAR_DATA_FILE_NAME, json)
        }
    }
}