package com.android.chileaf.bluetooth.scanner;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: PendingIntentExecutor */
class i extends j {
    private final PendingIntent a;
    private Context b;
    private Context c;
    private long d;
    private long e;

    i(PendingIntent pendingIntent, ScanSettings scanSettings) {
        this.a = pendingIntent;
        this.e = scanSettings.k();
    }

    /* access modifiers changed from: package-private */
    public void a(Context context) {
        this.b = context;
    }

    public void a(int i2, ScanResult scanResult) {
        Context context = this.b;
        if (context == null) {
            context = this.c;
        }
        if (context != null) {
            try {
                Intent intent = new Intent();
                intent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", i2);
                intent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList(Collections.singletonList(scanResult)));
                this.a.send(context, 0, intent);
            } catch (PendingIntent.CanceledException unused) {
            }
        }
    }

    i(PendingIntent pendingIntent, ScanSettings scanSettings, Service service) {
        this.a = pendingIntent;
        this.e = scanSettings.k();
        this.c = service;
    }

    public void a(List<ScanResult> list) {
        Context context = this.b;
        if (context == null) {
            context = this.c;
        }
        if (context != null) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (this.d <= (elapsedRealtime - this.e) + 5) {
                this.d = elapsedRealtime;
                try {
                    Intent intent = new Intent();
                    intent.putExtra("android.bluetooth.le.extra.CALLBACK_TYPE", 1);
                    intent.putParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT", new ArrayList(list));
                    intent.setExtrasClassLoader(ScanResult.class.getClassLoader());
                    this.a.send(context, 0, intent);
                } catch (PendingIntent.CanceledException unused) {
                }
            }
        }
    }

    public void a(int i2) {
        Context context = this.b;
        if (context == null) {
            context = this.c;
        }
        if (context != null) {
            try {
                Intent intent = new Intent();
                intent.putExtra("android.bluetooth.le.extra.ERROR_CODE", i2);
                this.a.send(context, 0, intent);
            } catch (PendingIntent.CanceledException unused) {
            }
        }
    }
}
