package com.example.tiger.weather.util;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by tiger on 16/11/10.
 */

public interface TestService {
    @GET()
    Observable<String> getResponse(@Url String s);
}
