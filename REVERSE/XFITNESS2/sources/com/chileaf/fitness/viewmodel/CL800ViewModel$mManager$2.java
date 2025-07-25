package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.wear.cl800.CL800Manager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL800ViewModel.kt */
final class CL800ViewModel$mManager$2 extends Lambda implements a<CL800Manager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL800ViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CL800Manager invoke() {
        return CL800Manager.a((Context) this.$application);
    }
}
