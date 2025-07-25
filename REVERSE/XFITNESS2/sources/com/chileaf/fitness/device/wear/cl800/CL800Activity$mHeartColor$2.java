package com.chileaf.fitness.device.wear.cl800;

import android.content.Context;
import com.chileaf.fitness.R$color;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL800Activity.kt */
final class CL800Activity$mHeartColor$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL800Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL800Activity$mHeartColor$2(CL800Activity cL800Activity) {
        super(0);
        this.this$0 = cL800Activity;
    }

    public final int invoke() {
        return androidx.core.content.a.a((Context) this.this$0, (int) R$color.heart_primary);
    }
}
