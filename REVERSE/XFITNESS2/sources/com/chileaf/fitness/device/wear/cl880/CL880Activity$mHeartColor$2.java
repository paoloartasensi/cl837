package com.chileaf.fitness.device.wear.cl880;

import android.content.Context;
import com.chileaf.fitness.R$color;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL880Activity.kt */
final class CL880Activity$mHeartColor$2 extends Lambda implements a<Integer> {
    final /* synthetic */ CL880Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL880Activity$mHeartColor$2(CL880Activity cL880Activity) {
        super(0);
        this.this$0 = cL880Activity;
    }

    public final int invoke() {
        return androidx.core.content.a.a((Context) this.this$0, (int) R$color.heart_primary);
    }
}
