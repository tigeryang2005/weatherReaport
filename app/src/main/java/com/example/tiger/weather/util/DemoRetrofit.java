package com.example.tiger.weather.util;

import android.util.Log;

import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.testResponse;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static android.content.ContentValues.TAG;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoRetrofit {

    public void testRetrofitHttpGet(String cityId) {
        //step1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:2000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //step2
        DemoServiece serviece = retrofit.create(DemoServiece.class);
        //step3
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("name", "bob");
        hashMap.put("age", "22");
        Call<testResponse> call = serviece.testHttpGet(cityId, "mvalue", hashMap);
        //step4
        call.enqueue(new Callback<testResponse>() {
            @Override
            public void onResponse(Call<testResponse> call, Response<testResponse> response) {
                Log.d(TAG, "onResponse: " + response.code() + response.message() + response.raw().toString() + response.isSuccessful());
                if (response.headers() != null) {
                    Log.d(TAG, "onResponse: " + response.headers().toString());
                } else {
                    Log.d(TAG, "onResponse: header is null");
                }
                if (response.errorBody() != null) {
                    Log.d(TAG, "onResponse: " + response.errorBody().toString());
                } else {
                    Log.d(TAG, "onResponse: errorbody is null");
                }
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }
                Log.d(TAG, "onResponse: " + response.body().a);
                Log.d(TAG, "onResponse: " + response.body().b);
                Logger.d(response.body().c);
            }

            @Override
            public void onFailure(Call<testResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: get " + t.getMessage());
            }
        });

        //testPost
        Retrofit retrofitPost = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:2000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DemoServiecePost serviecePost = retrofitPost.create(DemoServiecePost.class);
        City city = new City();
        city.setCityId("10001010");
        city.setCityName("北京");
        city.setProId("100010");
        Call<RequestBody> callPost = serviecePost.testHttpPost(city);
        callPost.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.d(TAG, "onResponse: " + response.code() + response.message() + response.raw().toString() + response.isSuccessful());
                if (response.headers() != null) {
                    Log.d(TAG, "onResponse: " + response.headers().toString());
                } else {
                    Log.d(TAG, "onResponse: header is null");
                }
                if (response.errorBody() != null) {
                    Log.d(TAG, "onResponse: " + response.errorBody().toString());
                } else {
                    Log.d(TAG, "onResponse: errorbody is null");
                }
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                } else {
                    Log.d(TAG, "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d(TAG, "onFailure: post " + t.getMessage());
            }
        });
    }


    public interface DemoServiece {
        @GET("{cityId}")
        Call<testResponse> testHttpGet(@Path("cityId") String cityId, @Query("param") String value, @QueryMap Map<String, String> options);
    }

    public interface DemoServiecePost {
        @POST("hello")
        Call<RequestBody> testHttpPost(@Body City city);
    }
}
