package com.example.tiger.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.tiger.weather.db.WrDb;
import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.County;
import com.example.tiger.weather.model.Province;
import com.example.tiger.weather.model.WeatherInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by tiger on 16/7/18.
 */
public class Utility {

    public static WeatherInfo handleWeatherRespose(Context context, String response) {
        try {
            Log.d(TAG, "handleWeatherRespose: " + response);
            //Logger.json(response);
            List<String> list = new ArrayList<>();
            list.add("a");
            list.add("b");
//            Logger.d(list);
//            Logger.t(1).d(cityName);
            WeatherInfo weatherInfo = new WeatherInfo();
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObjectWeatherInfo = jsonObject.optJSONObject("weatherinfo");
            weatherInfo.weatherinfo = new WeatherInfo.WeatherinfoBean();
            weatherInfo.weatherinfo.city = jsonObjectWeatherInfo.optString("city");
            weatherInfo.weatherinfo.temp1 = jsonObjectWeatherInfo.optString("temp1");
            weatherInfo.weatherinfo.temp2 = jsonObjectWeatherInfo.optString("temp2");
            weatherInfo.weatherinfo.ptime = jsonObjectWeatherInfo.optString("ptime");
            weatherInfo.weatherinfo.weather = jsonObjectWeatherInfo.optString("weather");
            Log.d(TAG, "handleWeatherRespose: " + weatherInfo.weatherinfo.city);
            Gson gson = new Gson();
            JsonArray jArray = new JsonArray();
            jArray.add(234);
            jArray.add("123");
            JsonObject jObject = new JsonObject();
            jObject.add("array", jArray);

            String str = gson.toJson(weatherInfo);
            JsonElement jElement = gson.toJsonTree(str);
            jObject.add("www", jElement);
            Logger.json(jObject.toString());
//            Gson gson = new Gson();
//            WeatherInfo weatherinfo = gson.fromJson(response, WeatherInfo.class);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putBoolean("city_selected", true);
            editor.apply();
            return weatherInfo;
        } catch (Exception e) {
            Logger.e("gson error", e.toString());
        }
        return null;
    }

    public synchronized static boolean handleProvincesResponse(WrDb wrDb, String response) {
        Logger.d(response);
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province provience = new Province();
                    provience.setProId(array[0]);
                    provience.setProName(array[1]);
                    wrDb.saveProvince(provience);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(WrDb wrDb, String response, String proId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities.length > 0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityId(array[0]);
                    city.setProId(proId);
                    city.setCityName(array[1]);
                    wrDb.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCountiesResponse(WrDb wrDb, String response, String cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties.length > 0) {
                for (String p : allCounties) {
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCityId(cityId);
                    county.setCountyId(array[0]);
                    county.setCountyName(array[1]);
                    wrDb.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
