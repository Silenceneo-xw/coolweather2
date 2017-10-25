package com.silenceneoxw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Name : Weather
 * @Date : 2017/10/24 14:09
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class Weather {

    @SerializedName("status")
    public String status;

    @SerializedName("basic")
    public Basic basic;

    @SerializedName("aqi")
    public AQI aqi;

    @SerializedName("now")
    public Now now;

    @SerializedName("suggestion")
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
