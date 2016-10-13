package com.example.tiger.weather.util;

import android.util.Log;

import com.example.tiger.weather.convertFactory.DemoConverterFactory;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.ContentValues.TAG;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoRetrofit {

    public void testRetrofitHttpGet(String cityId) {
        //step1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:2000/")
                .addConverterFactory(DemoConverterFactory.create())
                .build();
        //step2
        DemoServiece serviece = retrofit.create(DemoServiece.class);
        //step3
        Call<RequestBody> call = serviece.testHttpGet(cityId, "mvalue");
        //step4
        call.enqueue(new Callback<RequestBody>() {
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
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    public interface DemoServiece {
        @GET("{cityId}")
        Call<RequestBody> testHttpGet(@Path("cityId") String cityId, @Query("param") String value);
    }
}
