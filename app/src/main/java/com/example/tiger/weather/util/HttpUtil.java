package com.example.tiger.weather.util;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tiger on 16/7/15.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(500);
                    connection.setReadTimeout(500);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Accept-Encoding", "identity");
                    int responseCode = connection.getResponseCode();
                    InputStream in = null;
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        in = connection.getInputStream();
                    } else if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                        in = connection.getErrorStream();
                    } else {
                        new Throwable("特殊状况异常");
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();
                    reader.close();
                    connection.disconnect();
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
