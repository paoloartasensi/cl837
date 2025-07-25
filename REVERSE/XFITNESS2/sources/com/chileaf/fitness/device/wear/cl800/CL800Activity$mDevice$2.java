package com.chileaf.fitness.device.wear.cl800;

import android.content.Intent;
import com.chileaf.fitness.model.DiscoveredDevice;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CL800Activity.kt */
final class CL800Activity$mDevice$2 extends Lambda implements a<DiscoveredDevice> {
    final /* synthetic */ CL800Activity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CL800Activity$mDevice$2(CL800Activity cL800Activity) {
        super(0);
        this.this$0 = cL800Activity;
    }

    public final DiscoveredDevice invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return (DiscoveredDevice) intent.getParcelableExtra("extra_device");
        }
        return null;
    }
}
