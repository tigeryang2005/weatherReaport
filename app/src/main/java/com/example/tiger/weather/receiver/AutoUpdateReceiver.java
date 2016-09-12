package com.example.tiger.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tiger.weather.service.AutoUpdataService;
import com.orhanobut.logger.Logger;

/**
 * Created by tiger on 16/9/8.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.d(intent.getStringExtra("123"));
        Logger.d("broadcastreceiver" + intent.getAction());
        Intent i = new Intent(context, AutoUpdataService.class);
        Logger.d("broadcastreceiver new intent" + i.getAction());
        context.startService(i);
    }
}
