package com.chileaf.fitness.device.wear.cl880;

import android.content.Intent;
import com.chileaf.fitness.model.DiscoveredDevice;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL880Activity.kt */
final class CL880Activity$mDevice$2 extends Lambda implements a<DiscoveredDevice> {
    final /* synthetic */ CL880Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL880Activity$mDevice$2(CL880Activity cL880Activity) {
        super(0);
        this.this$0 = cL880Activity;
    }

    public final DiscoveredDevice invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return (DiscoveredDevice) intent.getParcelableExtra("extra_device");
        }
        return null;
    }
}
