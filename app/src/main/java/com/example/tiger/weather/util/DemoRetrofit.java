package com.example.tiger.weather.util;

import android.util.Log;

import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.testResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static android.content.ContentValues.TAG;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoRetrofit {

    public void testRetrofitHttpGet(String cityId, String path) {
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
//                Log.d(TAG, "onResponse: " + response.body().a);
//                Log.d(TAG, "onResponse: " + response.body().b);
//                Logger.d(response.body().c);
                //Retrofit 的 Response 还有一个方法叫做 raw()，调用该方法就可以把 Retrofit 的Response
                // 转换为原生的 OkHttp 当中的 Response。而现在我们就很容器实现 header 的读取了吧。
                Log.d(TAG, "onResponse: throgh raw to get Date is: " + response.raw().header("Date"));
            }

            @Override
            public void onFailure(Call<testResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: get " + t.getMessage());
            }
        });

        //testPost
        DemoServiecePost serviecePost = retrofit.create(DemoServiecePost.class);
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

        DemoServiecePostField serviecePostField = retrofit.create(DemoServiecePostField.class);
        String str = "application/x-www-form-urlencode;charset=UTF-8";
        Call<RequestBody> callPostField = serviecePostField.testHttpPostField("张得帅", 18, hashMap, str);
        callPostField.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.headers() != null) {
                    Log.d(TAG, "onResponse: postfield" + response.headers().toString());
                } else {
                    Log.d(TAG, "onResponse: postfield header is null");
                }
                if (response.errorBody() != null) {
                    Log.d(TAG, "onResponse: postfield" + response.errorBody().toString());
                } else {
                    Log.d(TAG, "onResponse: postfield  errorbody is null");
                }
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: postfield" + response.body().toString());
                } else {
                    Log.d(TAG, "onResponse: postfield  body is null");
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d(TAG, "onFailure: postfield" + t.getMessage());
            }
        });

        //test upload file
//        String string = Environment.getExternalStorageDirectory().getAbsolutePath();
//        Logger.d(string);
        // Logger.d(path);
        File file = new File(path, "45952915.urlimage");
        DemoServieceUploadFile demoServieceUploadFile = retrofit.create(DemoServieceUploadFile.class);
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        Call<RequestBody> callUploadFile = demoServieceUploadFile.testMultiPart(image);
        callUploadFile.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.headers() != null) {
                    Log.d(TAG, "onResponse: postfile" + response.headers().toString());
                } else {
                    Log.d(TAG, "onResponse: postfile header is null");
                }
                if (response.errorBody() != null) {
                    Log.d(TAG, "onResponse: postfile" + response.errorBody().toString());
                } else {
                    Log.d(TAG, "onResponse: postfile  errorbody is null");
                }
                if (response.body() != null) {
                    Log.d(TAG, "onResponse: postfile" + response.body().toString());
                } else {
                    Log.d(TAG, "onResponse: postfile  body is null");
                }
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.d(TAG, "onFailure: postfile" + t.getMessage());
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

    public interface DemoServiecePostField {
        //@Headers("Content-type:application/x-www-form-urlencode;charset=UTF-8")
        @FormUrlEncoded
        @POST("hello")
        Call<RequestBody> testHttpPostField(@Field("username") String name, @Field("age") int age
                , @FieldMap Map<String, String> options, @Header("Content-type") String contentType);
    }

    public interface DemoServieceUploadFile {
        @Multipart()
        @POST("uploadfile")
        Call<RequestBody> testMultiPart(@Part("file\";filename=\"45952915.urlimage") RequestBody picFile);
    }
}
