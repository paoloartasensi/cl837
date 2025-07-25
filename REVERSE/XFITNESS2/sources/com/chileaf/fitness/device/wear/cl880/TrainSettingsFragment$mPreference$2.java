package com.chileaf.fitness.device.wear.cl880;

import android.content.SharedPreferences;
import androidx.preference.j;
import com.chileaf.fitness.App;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: TrainSettingsFragment.kt */
final class TrainSettingsFragment$mPreference$2 extends Lambda implements a<SharedPreferences> {
    public static final TrainSettingsFragment$mPreference$2 INSTANCE = new TrainSettingsFragment$mPreference$2();

    TrainSettingsFragment$mPreference$2() {
        super(0);
    }

    public final SharedPreferences invoke() {
        return j.a(App.f1144g.a());
    }
}
