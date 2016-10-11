package com.example.tiger.weather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.tiger.weather.util.HttpCallbackListener;
import com.example.tiger.weather.util.HttpUtil;
import com.example.tiger.weather.util.Utility;
import com.orhanobut.logger.Logger;

/**
 * Created by tiger on 16/9/8.
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("on create executed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("on destroy executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Context context = this.getApplicationContext();
        final Handler mhandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(context, "print every minte", Toast.LENGTH_SHORT).show();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
                Logger.e("print every minite");
                Message message = new Message();
                message.what = 1;
                mhandler.sendMessage(message);
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 1000 * 60 * 60 * 8;
        long triggerAtTime = System.currentTimeMillis() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
            Logger.d("set exactly");
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        String weatherCode = "101030200";
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utility.handleWeatherRespose(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
