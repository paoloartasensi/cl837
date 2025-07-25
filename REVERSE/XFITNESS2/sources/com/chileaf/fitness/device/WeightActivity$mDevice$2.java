package com.chileaf.fitness.device;

import android.content.Intent;
import com.chileaf.fitness.model.DiscoveredDevice;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: WeightActivity.kt */
final class WeightActivity$mDevice$2 extends Lambda implements a<DiscoveredDevice> {
    final /* synthetic */ WeightActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WeightActivity$mDevice$2(WeightActivity weightActivity) {
        super(0);
        this.this$0 = weightActivity;
    }

    public final DiscoveredDevice invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return (DiscoveredDevice) intent.getParcelableExtra("extra_device");
        }
        return null;
    }
}
