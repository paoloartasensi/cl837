package com.chileaf.fitness.config;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.h;

/* compiled from: ForegroundService.kt */
public final class ForegroundService extends Service {

    /* compiled from: ForegroundService.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        Bundle extras;
        if (intent == null || (extras = intent.getExtras()) == null || !extras.containsKey("extra_notification") || !extras.getBoolean("extra_notification") || Build.VERSION.SDK_INT < 26) {
            return 2;
        }
        startForeground(1, new h.b(this, "foreground_id").a());
        return 2;
    }
}
