package com.chileaf.fitness.device.cdn;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.g;
import androidx.preference.j;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.R$xml;
import java.util.HashMap;
import kotlin.jvm.internal.i;

/* compiled from: CDNSettingsFragment.kt */
public final class d extends g implements SharedPreferences.OnSharedPreferenceChangeListener {
    private HashMap k0;

    /* compiled from: CDNSettingsFragment.kt */
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

    private final void w0() {
        PreferenceScreen r0 = r0();
        j q0 = q0();
        i.a((Object) q0, "preferenceManager");
        String string = q0.h().getString("settings_wheel_size", String.valueOf(2340));
        Preference c = r0.c((CharSequence) "settings_wheel_size");
        if (c != null) {
            c.a((CharSequence) a((int) R$string.csc_settings_wheel_diameter_summary, string));
        }
    }

    public /* synthetic */ void T() {
        super.T();
        v0();
    }

    public void V() {
        super.V();
        PreferenceScreen r0 = r0();
        i.a((Object) r0, "preferenceScreen");
        r0.n().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void W() {
        super.W();
        PreferenceScreen r0 = r0();
        i.a((Object) r0, "preferenceScreen");
        r0.n().registerOnSharedPreferenceChangeListener(this);
    }

    public void a(Bundle bundle, String str) {
        e((int) R$xml.settings_csc);
        w0();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        i.b(sharedPreferences, "sharedPreferences");
        i.b(str, "key");
        if (i.a((Object) "settings_wheel_size", (Object) str)) {
            w0();
        }
    }

    public void v0() {
        HashMap hashMap = this.k0;
        if (hashMap != null) {
            hashMap.clear();
        }
    }
}
