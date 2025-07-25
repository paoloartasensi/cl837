package com.chileaf.fitness.device.wear.cl831;

import android.content.Intent;
import com.chileaf.fitness.model.DiscoveredDevice;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL831Activity.kt */
final class CL831Activity$mDevice$2 extends Lambda implements a<DiscoveredDevice> {
    final /* synthetic */ CL831Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL831Activity$mDevice$2(CL831Activity cL831Activity) {
        super(0);
        this.this$0 = cL831Activity;
    }

    public final DiscoveredDevice invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return (DiscoveredDevice) intent.getParcelableExtra("extra_device");
        }
        return null;
    }
}
