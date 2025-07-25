package com.chileaf.fitness.ui.activity;

import android.content.Intent;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: DeviceSettingsActivity.kt */
final class DeviceSettingsActivity$mSetting$2 extends Lambda implements a<String> {
    final /* synthetic */ DeviceSettingsActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DeviceSettingsActivity$mSetting$2(DeviceSettingsActivity deviceSettingsActivity) {
        super(0);
        this.this$0 = deviceSettingsActivity;
    }

    public final String invoke() {
        Intent intent = this.this$0.getIntent();
        if (intent != null) {
            return intent.getStringExtra("extra_fragment");
        }
        return null;
    }
}
