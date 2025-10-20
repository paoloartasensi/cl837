package com.chileaf.cl831.sample;

import android.app.Application;
import android.os.Build;

import no.nordicsemi.android.dfu.DfuServiceInitiator;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DfuServiceInitiator.createDfuNotificationChannel(this);
        }
    }
}
