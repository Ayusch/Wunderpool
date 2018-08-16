package com.ayusch.wunderassignment.callbacks;

public interface Callback<T> {
    void returnResult(T t);

    void onError(Throwable error);
}
