package com.example.tiger.weather.convertFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoConverterFactory extends Converter.Factory {

    private DemoConverterFactory() {
        super();
    }

    public static DemoConverterFactory create() {
        return new DemoConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DemoRespnseBodyConverter<>();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new DemoRequestBodyConverter<>();
    }
}
