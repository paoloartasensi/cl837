package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.wear.cl880.CL880Manager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL880ViewModel.kt */
final class CL880ViewModel$mManager$2 extends Lambda implements a<CL880Manager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL880ViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CL880Manager invoke() {
        return CL880Manager.a((Context) this.$application);
    }
}
