package com.example.tiger.weather.model;

import java.io.Serializable;

/**
 * Created by tiger on 16/7/18.
 */
public class WeatherInfo implements Serializable {
    /**
     * city : 昆山
     * cityid : 101190404
     * temp1 : 21°C
     * temp2 : 9°C
     * weather : 多云转小雨
     * img1 : d1.gif
     * img2 : n7.gif
     * ptime : 11:00
     */

    public WeatherinfoBean weatherinfo;

    public static class WeatherinfoBean {
        public String city;
        public String cityid;
        public String temp1;
        public String temp2;
        public String weather;
        public String img1;
        public String img2;
        public String ptime;
    }
}
