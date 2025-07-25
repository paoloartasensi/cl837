package com.chileaf.fitness.device.wear.cl831;

import android.content.Context;
import com.chileaf.fitness.R$color;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL831Activity.kt */
final class CL831Activity$mHeartColor$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL831Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL831Activity$mHeartColor$2(CL831Activity cL831Activity) {
        super(0);
        this.this$0 = cL831Activity;
    }

    public final int invoke() {
        return androidx.core.content.a.a((Context) this.this$0, (int) R$color.heart_primary);
    }
}
