package com.silenceneoxw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Name : Forecast
 * @Date : 2017/10/24 14:01
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class Forecast {

    @SerializedName("date")
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    @SerializedName("wind")
    public Wind wind;

    public class Temperature {

        @SerializedName("max")
        public String max;

        @SerializedName("min")
        public String min;
    }

    public class More {

        @SerializedName("txt_d")
        public String info;
    }

    public class Wind {

        @SerializedName("dir")
        public String dir;

        @SerializedName("sc")
        public String sc;
    }
}
