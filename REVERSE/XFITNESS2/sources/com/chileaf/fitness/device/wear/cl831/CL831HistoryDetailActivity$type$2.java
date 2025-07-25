package com.chileaf.fitness.device.wear.cl831;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL831HistoryDetailActivity.kt */
final class CL831HistoryDetailActivity$type$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL831HistoryDetailActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL831HistoryDetailActivity$type$2(CL831HistoryDetailActivity cL831HistoryDetailActivity) {
        super(0);
        this.this$0 = cL831HistoryDetailActivity;
    }

    public final Integer invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return Integer.valueOf(intent.getIntExtra("extra_type", 0));
        }
        return null;
    }
}
