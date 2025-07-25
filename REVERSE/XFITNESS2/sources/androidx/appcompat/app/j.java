package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.core.content.b;
import java.util.Calendar;

/* compiled from: TwilightManager */
class j {
    private static j d;
    private final Context a;
    private final LocationManager b;
    private final a c = new a();

    /* compiled from: TwilightManager */
    private static class a {
        boolean a;
        long b;
        long c;
        long d;
        long e;

        /* renamed from: f  reason: collision with root package name */
        long f73f;

        a() {
        }
    }

    j(Context context, LocationManager locationManager) {
        this.a = context;
        this.b = locationManager;
    }

    static j a(Context context) {
        if (d == null) {
            Context applicationContext = context.getApplicationContext();
            d = new j(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return d;
    }

    @SuppressLint({"MissingPermission"})
    private Location b() {
        Location location = null;
        Location a2 = b.a(this.a, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? a("network") : null;
        if (b.a(this.a, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            location = a("gps");
        }
        return (location == null || a2 == null) ? location != null ? location : a2 : location.getTime() > a2.getTime() ? location : a2;
    }

    private boolean c() {
        return this.c.f73f > System.currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        a aVar = this.c;
        if (c()) {
            return aVar.a;
        }
        Location b2 = b();
        if (b2 != null) {
            a(b2);
            return aVar.a;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i2 = Calendar.getInstance().get(11);
        return i2 < 6 || i2 >= 22;
    }

    private Location a(String str) {
        try {
            if (this.b.isProviderEnabled(str)) {
                return this.b.getLastKnownLocation(str);
            }
            return null;
        } catch (Exception e) {
            Log.d("TwilightManager", "Failed to get last known location", e);
            return null;
        }
    }

    private void a(Location location) {
        long j2;
        a aVar = this.c;
        long currentTimeMillis = System.currentTimeMillis();
        i a2 = i.a();
        i iVar = a2;
        iVar.a(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j3 = a2.a;
        iVar.a(currentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = a2.c == 1;
        long j4 = a2.b;
        long j5 = j3;
        long j6 = a2.a;
        long j7 = j4;
        boolean z2 = z;
        a2.a(86400000 + currentTimeMillis, location.getLatitude(), location.getLongitude());
        long j8 = a2.b;
        if (j7 == -1 || j6 == -1) {
            j2 = 43200000 + currentTimeMillis;
        } else {
            j2 = (currentTimeMillis > j6 ? 0 + j8 : currentTimeMillis > j7 ? 0 + j6 : 0 + j7) + 60000;
        }
        aVar.a = z2;
        aVar.b = j5;
        aVar.c = j7;
        aVar.d = j6;
        aVar.e = j8;
        aVar.f73f = j2;
    }
}
