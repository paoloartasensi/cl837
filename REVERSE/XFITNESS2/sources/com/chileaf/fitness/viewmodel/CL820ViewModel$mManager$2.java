package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.wear.cl820.CL820Manager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL820ViewModel.kt */
final class CL820ViewModel$mManager$2 extends Lambda implements a<CL820Manager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL820ViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CL820Manager invoke() {
        return CL820Manager.a((Context) this.$application);
    }
}
