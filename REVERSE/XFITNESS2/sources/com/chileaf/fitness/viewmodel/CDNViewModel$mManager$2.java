package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.content.Context;
import com.chileaf.fitness.device.cdn.CDNManager;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CDNViewModel.kt */
final class CDNViewModel$mManager$2 extends Lambda implements a<CDNManager> {
    final /* synthetic */ Application $application;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CDNViewModel$mManager$2(Application application) {
        super(0);
        this.$application = application;
    }

    public final CDNManager invoke() {
        return CDNManager.a((Context) this.$application);
    }
}
