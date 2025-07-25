package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.wear.cl831.CL831Manager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL831ViewModel.kt */
final class CL831ViewModel$mManager$2 extends Lambda implements a<CL831Manager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL831ViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CL831Manager invoke() {
        return CL831Manager.a((Context) this.$application);
    }
}
