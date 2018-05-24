package com.bigzindustries.brochefbakercompanion;

import android.app.Application;

import com.flurry.android.FlurryAgent;

public class BroChefApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "XQFY3N69K6G925T7D2CY");
    }
}
