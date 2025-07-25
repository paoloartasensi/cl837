package org.koin.core;

import java.util.List;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.l;

/* compiled from: KoinApplication.kt */
final class KoinApplication$modules$duration$1 extends Lambda implements a<l> {
    final /* synthetic */ List $modules;
    final /* synthetic */ KoinApplication this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KoinApplication$modules$duration$1(KoinApplication koinApplication, List list) {
        super(0);
        this.this$0 = koinApplication;
        this.$modules = list;
    }

    public final void invoke() {
        this.this$0.a((Iterable<org.koin.core.d.a>) this.$modules);
    }
}
