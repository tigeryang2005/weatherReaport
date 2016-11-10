package com.example.tiger.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tiger.weather.R;
import com.example.tiger.weather.model.WeatherInfo;
import com.example.tiger.weather.util.DemoRetrofit;
import com.example.tiger.weather.util.HttpCallbackListener;
import com.example.tiger.weather.util.HttpUtil;
import com.example.tiger.weather.util.Utility;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by tiger on 16/8/29.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDataText;
    private WeatherInfo weatherinfo;
    private String mWeatherCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDataText = (TextView) findViewById(R.id.current_date);
        Button switchCity = (Button) findViewById(R.id.switch_city);
        Button refreshWeather = (Button) findViewById(R.id.refresh_weather);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            if (weatherinfo != null && weatherinfo.weatherinfo != null) {
                showWeather(weatherinfo.weatherinfo);
            }
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChooseAreaActivity.class);
        intent.putExtra("from_weather_activity", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                if (!TextUtils.isEmpty(mWeatherCode)) {
                    queryWeatherInfo(mWeatherCode);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 查询县级代号所对应的天气代号。
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" +
                countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气。
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" +
                weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array.length == 2) {
                            String weatherCode = array[1];
                            mWeatherCode = weatherCode;
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    weatherinfo = Utility.handleWeatherRespose(WeatherActivity.this, response);
                    DemoRetrofit demoRetrofit = new DemoRetrofit();
                    String str = getFilesDir().getAbsolutePath() + "/inad_src/img_flu/";
                    demoRetrofit.testRetrofitHttpGet("123", str);
                    Runnable task = () -> showWeather(weatherinfo.weatherinfo);
                    runOnUiThread(task);
                }
            }

            @Override
            public void onError(Exception e) {
                Logger.e(e.toString());
                Runnable task = () -> publishText.setText("同步失败");
                runOnUiThread(task);
            }
        });
    }


    private void showWeather(WeatherInfo.WeatherinfoBean weatherinfoBean) {
        if (weatherinfoBean != null) {
            cityNameText.setText(weatherinfoBean.city);
            temp1Text.setText(weatherinfoBean.temp1);
            temp2Text.setText(weatherinfoBean.temp2);
            weatherDespText.setText(weatherinfoBean.weather);
            publishText.setText(getString(R.string.pubulish_text, weatherinfoBean.ptime));
            currentDataText.setText(DateFormat.getDateInstance().format(new Date()));
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
            //Intent intent = new Intent(this, AutoUpdateService.class);
            //startService(intent);
        } else {
            Logger.d("weatherinfoBean is null");
        }
    }
}
