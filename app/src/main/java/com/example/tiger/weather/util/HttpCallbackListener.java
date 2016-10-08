package com.example.tiger.weather.util;

/**
 * Created by tiger on 16/7/15.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
