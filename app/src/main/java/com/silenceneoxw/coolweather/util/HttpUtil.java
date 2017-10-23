package com.silenceneoxw.coolweather.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @Name : HttpUtil
 * @Date : 2017/10/23 19:14
 * @Author : Silenceneo
 * @E-mail : silenceneoxw@gmail.com
 * @Link : https://github.com/silenceneo-xw
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
