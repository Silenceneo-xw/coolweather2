package com.silenceneoxw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Name : AQI
 * @Date : 2017/10/24 13:44
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class AQI {

    @SerializedName("city")
    public AQICity city;

    public class AQICity {

        @SerializedName("aqi")
        public String aqi;

        @SerializedName("pm25")
        public String pm25;

        @SerializedName("qlty")
        public String qlty;
    }
}
