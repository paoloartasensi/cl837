package com.chileaf.fitness.ui.activity;

import android.content.Intent;
import com.chileaf.fitness.R$string;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: DeviceSettingsActivity.kt */
final class DeviceSettingsActivity$mTitle$2 extends Lambda implements a<String> {
    final /* synthetic */ DeviceSettingsActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DeviceSettingsActivity$mTitle$2(DeviceSettingsActivity deviceSettingsActivity) {
        super(0);
        this.this$0 = deviceSettingsActivity;
    }

    public final String invoke() {
        String stringExtra;
        Intent intent = this.this$0.getIntent();
        if (intent != null && (stringExtra = intent.getStringExtra("extra_title")) != null) {
            return stringExtra;
        }
        String string = this.this$0.getString(R$string.setting);
        i.a((Object) string, "getString(R.string.setting)");
        return string;
    }
}
