package com.example.tiger.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tiger.weather.util.HttpCallbackListener;
import com.example.tiger.weather.util.HttpUtil;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtil.sendHttpRequest("http://www.weather.com.cn/data/list3/city.xml", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                //Logger.d(response);
                String[] strings = response.split(",");
                //Logger.d(strings);
                for (String s : strings){
                    //Logger.d(s);
                    String[] strings1 = s.split("\\|");//“.”和“|”都是转义字符，必须得加"\";
                    for (String s1 : strings1){
                        Logger.d(s1);
                    }
//                    Logger.d(strings1);
                }
            }

            @Override
            public void onError(Exception e) {
                Logger.d(e);
            }
        });
    }
}
