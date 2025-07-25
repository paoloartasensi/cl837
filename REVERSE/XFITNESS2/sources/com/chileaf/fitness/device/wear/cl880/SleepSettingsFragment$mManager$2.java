package com.chileaf.fitness.device.wear.cl880;

import com.chileaf.fitness.App;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: SleepSettingsFragment.kt */
final class SleepSettingsFragment$mManager$2 extends Lambda implements a<CL880Manager> {
    public static final SleepSettingsFragment$mManager$2 INSTANCE = new SleepSettingsFragment$mManager$2();

    SleepSettingsFragment$mManager$2() {
        super(0);
    }

    public final CL880Manager invoke() {
        return CL880Manager.a(App.f1144g.a());
    }
}
