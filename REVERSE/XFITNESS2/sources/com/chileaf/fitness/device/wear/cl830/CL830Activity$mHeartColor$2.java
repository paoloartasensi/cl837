package com.chileaf.fitness.device.wear.cl830;

import android.content.Context;
import com.chileaf.fitness.R$color;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL830Activity.kt */
final class CL830Activity$mHeartColor$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL830Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL830Activity$mHeartColor$2(CL830Activity cL830Activity) {
        super(0);
        this.this$0 = cL830Activity;
    }

    public final int invoke() {
        return androidx.core.content.a.a((Context) this.this$0, (int) R$color.heart_primary);
    }
}
