package com.silenceneoxw.coolweather.gson;

/**
 * @Name : AQI
 * @Date : 2017/10/24 13:44
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
