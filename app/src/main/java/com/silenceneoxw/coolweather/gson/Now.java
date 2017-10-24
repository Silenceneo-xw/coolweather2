package com.silenceneoxw.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @Name : Now
 * @Date : 2017/10/24 13:47
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;
    }
}
