package com.example.robin.rakalender.Utils;

import android.content.SharedPreferences;

import com.example.robin.rakalender.KalenderApp;


public class ApiUtils {

    private static  ApiUtils _instance;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    public static final String Base_URL = "https://ra-kalender.herokuapp.com";

    public static KalenderService getKalender() {
        return RetrofitClient.getClient(Base_URL).create(KalenderService.class);

    }

    private ApiUtils() {

        loadData();

    }

    public void loadData(){

        KalenderApp app = KalenderApp.appContext;
        preferences = app.getSharedPreferences("kalenderPref", app.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public static ApiUtils getInstance() {
        if (_instance==null) {
            _instance = new ApiUtils();
        }

        return _instance;
    }
}
