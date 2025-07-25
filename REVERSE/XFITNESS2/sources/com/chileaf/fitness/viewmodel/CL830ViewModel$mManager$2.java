package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.wear.cl830.CL830Manager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL830ViewModel.kt */
final class CL830ViewModel$mManager$2 extends Lambda implements a<CL830Manager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL830ViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CL830Manager invoke() {
        return CL830Manager.a((Context) this.$application);
    }
}
