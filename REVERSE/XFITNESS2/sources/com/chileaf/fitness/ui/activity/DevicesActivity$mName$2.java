package com.chileaf.fitness.ui.activity;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: DevicesActivity.kt */
final class DevicesActivity$mName$2 extends Lambda implements a<String> {
    final /* synthetic */ DevicesActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DevicesActivity$mName$2(DevicesActivity devicesActivity) {
        super(0);
        this.this$0 = devicesActivity;
    }

    public final String invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return intent.getStringExtra("extra_name");
        }
        return null;
    }
}
