package com.chileaf.fitness.device.cdn;

import android.content.Intent;
import com.chileaf.fitness.model.DiscoveredDevice;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: CDNActivity.kt */
final class CDNActivity$mDevice$2 extends Lambda implements a<DiscoveredDevice> {
    final /* synthetic */ CDNActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CDNActivity$mDevice$2(CDNActivity cDNActivity) {
        super(0);
        this.this$0 = cDNActivity;
    }

    public final DiscoveredDevice invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return (DiscoveredDevice) intent.getParcelableExtra("extra_device");
        }
        return null;
    }
}
