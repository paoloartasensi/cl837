package com.chileaf.fitness.device.wear.cl820;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL820HistoryActivity.kt */
final class CL820HistoryActivity$mType$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL820HistoryActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL820HistoryActivity$mType$2(CL820HistoryActivity cL820HistoryActivity) {
        super(0);
        this.this$0 = cL820HistoryActivity;
    }

    public final Integer invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return Integer.valueOf(intent.getIntExtra("extra_history", 0));
        }
        return null;
    }
}
