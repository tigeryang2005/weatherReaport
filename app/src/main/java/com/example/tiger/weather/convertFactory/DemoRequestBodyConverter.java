package com.example.tiger.weather.convertFactory;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by tiger on 16/10/12.
 */

public class DemoRequestBodyConverter<T> implements Converter<T, RequestBody> {
    @Override
    public RequestBody convert(T value) throws IOException {
        return null;
    }
}
