package com.silenceneoxw.coolweather.util;

/**
 * @Name : ApiUtil
 * @Date : 2017/10/25 22:42
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class ApiUtil {

    private static final String CITY_ID = "http://guolin.tech/api/weather?cityid=";
    private static final String HE_KEY = "&key=734fcd2f2cf44b4cbe318c1bafa5ae4d";
    private static final String REQUEST_BING_PIC = "http://guolin.tech/api/bing_pic";
    private static final String BASE_ADDRESS = "http://guolin.tech/api/china";

    public static String getCityId() {
        return CITY_ID;
    }

    public static String getHeKey() {
        return HE_KEY;
    }

    public static String getRequestBingPic() {
        return REQUEST_BING_PIC;
    }

    public static String getBaseAddress() {
        return BASE_ADDRESS;
    }
}
