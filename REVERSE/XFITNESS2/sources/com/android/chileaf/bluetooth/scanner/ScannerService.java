package com.android.chileaf.bluetooth.scanner;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.android.chileaf.bluetooth.scanner.ScanSettings;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ScannerService extends Service {
    private final Object e = new Object();

    /* renamed from: f  reason: collision with root package name */
    private HashMap<PendingIntent, j> f1117f;

    /* renamed from: g  reason: collision with root package name */
    private Handler f1118g;

    private void a(List<ScanFilter> list, ScanSettings scanSettings, PendingIntent pendingIntent) {
        i iVar = new i(pendingIntent, scanSettings, this);
        synchronized (this.e) {
            this.f1117f.put(pendingIntent, iVar);
        }
        try {
            a.a().a(list, scanSettings, iVar, this.f1118g);
        } catch (Exception e2) {
            Log.w("ScannerService", "Starting scanning failed", e2);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.f1117f = new HashMap<>();
        this.f1118g = new Handler();
    }

    public void onDestroy() {
        a a = a.a();
        for (j a2 : this.f1117f.values()) {
            try {
                a.a(a2);
            } catch (Exception unused) {
            }
        }
        this.f1117f.clear();
        this.f1117f = null;
        this.f1118g = null;
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        boolean containsKey;
        boolean isEmpty;
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("com.android.chileaf.bluetooth.EXTRA_PENDING_INTENT");
        boolean booleanExtra = intent.getBooleanExtra("com.android.chileaf.bluetooth.EXTRA_START", false);
        boolean z = !booleanExtra;
        if (pendingIntent == null) {
            synchronized (this.e) {
                isEmpty = this.f1117f.isEmpty();
            }
            if (isEmpty) {
                stopSelf();
            }
            return 2;
        }
        synchronized (this.e) {
            containsKey = this.f1117f.containsKey(pendingIntent);
        }
        if (booleanExtra && !containsKey) {
            List parcelableArrayListExtra = intent.getParcelableArrayListExtra("com.android.chileaf.bluetooth.EXTRA_FILTERS");
            ScanSettings scanSettings = (ScanSettings) intent.getParcelableExtra("com.android.chileaf.bluetooth.EXTRA_SETTINGS");
            if (parcelableArrayListExtra == null) {
                parcelableArrayListExtra = Collections.emptyList();
            }
            if (scanSettings == null) {
                scanSettings = new ScanSettings.b().a();
            }
            a(parcelableArrayListExtra, scanSettings, pendingIntent);
        } else if (z && containsKey) {
            a(pendingIntent);
        }
        return 2;
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
    }

    private void a(PendingIntent pendingIntent) {
        j remove;
        boolean isEmpty;
        synchronized (this.e) {
            remove = this.f1117f.remove(pendingIntent);
            isEmpty = this.f1117f.isEmpty();
        }
        if (remove != null) {
            try {
                a.a().a(remove);
            } catch (Exception e2) {
                Log.w("ScannerService", "Stopping scanning failed", e2);
            }
            if (isEmpty) {
                stopSelf();
            }
        }
    }
}
