package com.example.robin.rakalender;

import android.app.Application;
import android.os.StrictMode;

public class KalenderApp extends Application {

    public static KalenderApp appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        appContext = this;

    }



}
