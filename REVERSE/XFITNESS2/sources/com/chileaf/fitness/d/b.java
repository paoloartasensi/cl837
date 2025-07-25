package com.chileaf.fitness.d;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.content.a;
import kotlin.jvm.internal.i;

/* compiled from: Utils.kt */
public final class b {
    private static final boolean a = (Build.VERSION.SDK_INT >= 23);
    public static final b b = new b();

    private b() {
    }

    public final boolean a() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter != null && defaultAdapter.isEnabled();
    }

    public final boolean b() {
        return a;
    }

    public final void c(Context context) {
        i.b(context, "context");
        context.startActivity(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
    }

    public final void d(Context context) {
        i.b(context, "context");
        context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    public final void e(Context context) {
        i.b(context, "context");
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(268468224);
        context.startActivity(intent);
    }

    public final boolean b(Context context) {
        i.b(context, "context");
        return a.a(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public final boolean a(Context context) {
        int i2;
        i.b(context, "context");
        if (!a) {
            return true;
        }
        try {
            i2 = Settings.Secure.getInt(context.getContentResolver(), "location_mode");
        } catch (Settings.SettingNotFoundException unused) {
            i2 = 0;
        }
        if (i2 != 0) {
            return true;
        }
        return false;
    }
}
