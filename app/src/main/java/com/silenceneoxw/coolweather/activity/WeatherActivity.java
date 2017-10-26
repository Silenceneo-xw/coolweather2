package com.silenceneoxw.coolweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.silenceneoxw.coolweather.R;
import com.silenceneoxw.coolweather.gson.Forecast;
import com.silenceneoxw.coolweather.gson.Weather;
import com.silenceneoxw.coolweather.service.AutoUpdateService;
import com.silenceneoxw.coolweather.util.ApiUtil;
import com.silenceneoxw.coolweather.util.HttpUtil;
import com.silenceneoxw.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView windDirText;
    private TextView windScText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView qltyText;
    private TextView airText;
    private TextView comfortText;
    private TextView carWashText;
    private TextView dressText;
    private TextView fluText;
    private TextView sportText;
    private TextView travelText;
    private TextView ultravioletText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;
    public DrawerLayout drawerLayout;
    private Button navButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 使背景图和状态栏融合到一起
        fuseBackground();
        setContentView(R.layout.activity_weather);
        // 初始化各控件
        initViews();
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        // 根据缓存天气数据进行处理
        dealWithWeatherString(weatherString);
        String bingPic = prefs.getString("bing_pic", null);
        // 根据缓存背景图片数据进行处理
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
        // 为相关组件设置监听器
        setListener();
    }

    private void fuseBackground() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initViews() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        windDirText = (TextView) findViewById(R.id.wind_dir_text);
        windScText = (TextView) findViewById(R.id.wind_sc_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        qltyText = (TextView) findViewById(R.id.qlty_text);
        airText = (TextView) findViewById(R.id.air_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        dressText = (TextView) findViewById(R.id.dress_text);
        fluText = (TextView) findViewById(R.id.flu_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        travelText = (TextView) findViewById(R.id.travel_text);
        ultravioletText = (TextView) findViewById(R.id.ultraviolet_text);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
    }

    private void dealWithWeatherString(String weatherString) {
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
    }

    /**
     * 处理并展示 Weather 实体类中的数据
     * @param weather
     */
    private void showWeatherInfo(Weather weather) {
        // 展现当前天气状况
        showCurrentWeather(weather);
        // 展现天气预报
        showForecast(weather);
        // 展现空气质量状况
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
            qltyText.setText(weather.aqi.city.qlty);
        }
        // 展示生活建议
        showLifeSuggestions(weather);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    private void showCurrentWeather(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        String windDir = weather.now.wind.dir;
        String windSc = weather.now.wind.sc;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        windDirText.setText(windDir);
        windScText.setText(windSc);
    }

    private void showForecast(Weather weather) {
        forecastLayout.removeAllViews();
        for (Forecast forecast :
                weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            TextView dirText = (TextView) view.findViewById(R.id.dir_text);
            TextView scText = (TextView) view.findViewById(R.id.sc_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max + "℃");
            minText.setText(forecast.temperature.min + "℃");
            dirText.setText(forecast.wind.dir);
            scText.setText(forecast.wind.sc);
            forecastLayout.addView(view);
        }
    }

    private void showLifeSuggestions(Weather weather) {
        String air = "空气状况：" + weather.suggestion.air.info;
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车建议：" + weather.suggestion.carWash.info;
        String dress = "穿衣建议：" + weather.suggestion.dress.info;
        String flu = "感冒状况：" + weather.suggestion.flu.info;
        String sport = "运动建议：" + weather.suggestion.sport.info;
        String travel = "旅游建议：" + weather.suggestion.travel.info;
        String ultraviolet = "紫外线状况：" + weather.suggestion.ultraviolet.info;
        airText.setText(air);
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        dressText.setText(dress);
        fluText.setText(flu);
        sportText.setText(sport);
        travelText.setText(travel);
        ultravioletText.setText(ultraviolet);
    }

    /**
     * 根据天气 id 请求城市天气信息
     * @param weatherId
     */
    public void requestWeather(String weatherId) {
        String weatherUrl = ApiUtil.getCityId() + weatherId + ApiUtil.getHeKey();
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        HttpUtil.sendOkHttpRequest(ApiUtil.getRequestBingPic(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    private void setListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
