package com.jeremyliao.liveeventbus.utils;

import android.annotation.SuppressLint;
import android.app.Application;

public final class AppUtils {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile Application sApplication;

    public static Application getApplicationContext() {
        if (sApplication == null) {
            synchronized (AppUtils.class) {
                if (sApplication == null) {
                    try {
                        sApplication = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke((Object) null, (Object[]) null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sApplication;
    }
}
