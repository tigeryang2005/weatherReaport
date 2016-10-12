package com.example.tiger.weather.util;

import android.util.Log;

import com.example.tiger.weather.model.WeatherInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static android.content.ContentValues.TAG;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoRetrofit {

    public void testRetrofitHttpGet() {
        //step1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/data/cityinfo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //step2
        DemoServiece serviece = retrofit.create(DemoServiece.class);
        //step3
        Call<WeatherInfo> call = serviece.testHttpGet();
        //step4
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                Log.d(TAG, "onResponse: " + response.body().weatherinfo.city);
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public interface DemoServiece {
        @GET("101010100.html")
        Call<WeatherInfo> testHttpGet();
    }
}
