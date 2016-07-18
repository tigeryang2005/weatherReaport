package com.example.tiger.weather.util;

import android.text.TextUtils;

import com.example.tiger.weather.db.WrDb;
import com.example.tiger.weather.model.City;
import com.example.tiger.weather.model.County;
import com.example.tiger.weather.model.Province;
import com.orhanobut.logger.Logger;

/**
 * Created by tiger on 16/7/18.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(WrDb wrDb, String response){
        Logger.d(response);
        if (!TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            //Logger.d(allProvinces);
            if (allProvinces != null && allProvinces.length > 0){
                for (String p: allProvinces) {
                    String[] array = p.split("\\|");
                    //Logger.d(array);
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

    public synchronized static boolean handleCitiesResponse(WrDb wrDb, String response, String proId){
        if (!TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0){
                for (String p : allCities){
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

    public synchronized static boolean handleCountiesResponse(WrDb wrDb, String respose, String cityId){
        if (!TextUtils.isEmpty(respose)){
            String[] allCounties = respose.split(",");
            if (allCounties != null && allCounties.length > 0){
                for (String p : allCounties){
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
