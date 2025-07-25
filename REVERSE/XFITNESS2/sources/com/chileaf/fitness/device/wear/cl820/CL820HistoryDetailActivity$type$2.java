package com.chileaf.fitness.device.wear.cl820;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL820HistoryDetailActivity.kt */
final class CL820HistoryDetailActivity$type$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL820HistoryDetailActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL820HistoryDetailActivity$type$2(CL820HistoryDetailActivity cL820HistoryDetailActivity) {
        super(0);
        this.this$0 = cL820HistoryDetailActivity;
    }

    public final Integer invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return Integer.valueOf(intent.getIntExtra("extra_type", 0));
        }
        return null;
    }
}
