package com.chileaf.fitness.device.wear.cl820;

import android.content.Context;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL820HistoryActivity.kt */
final class CL820HistoryActivity$mManager$2 extends Lambda implements a<CL820Manager> {
    final /* synthetic */ CL820HistoryActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL820HistoryActivity$mManager$2(CL820HistoryActivity cL820HistoryActivity) {
        super(0);
        this.this$0 = cL820HistoryActivity;
    }

    public final CL820Manager invoke() {
        return CL820Manager.a((Context) this.this$0);
    }
}
