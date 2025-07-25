package com.chileaf.fitness.device.wear.cl880;

import com.chileaf.fitness.App;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: HealthSettingsFragment.kt */
final class HealthSettingsFragment$mManager$2 extends Lambda implements a<CL880Manager> {
    public static final HealthSettingsFragment$mManager$2 INSTANCE = new HealthSettingsFragment$mManager$2();

    HealthSettingsFragment$mManager$2() {
        super(0);
    }

    public final CL880Manager invoke() {
        return CL880Manager.a(App.f1144g.a());
    }
}
