package com.chileaf.fitness.device.wear.cl880;

import android.content.Intent;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import kotlin.TypeCastException;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: AlarmEditActivity.kt */
final class AlarmEditActivity$mAlarm$2 extends Lambda implements a<AlarmConfig> {
    final /* synthetic */ AlarmEditActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    AlarmEditActivity$mAlarm$2(AlarmEditActivity alarmEditActivity) {
        super(0);
        this.this$0 = alarmEditActivity;
    }

    public final AlarmConfig invoke() {
        Object obj;
        Intent intent = this.this$0.getIntent();
        if (intent == null || (obj = intent.getSerializableExtra("EXTRA_ALARM")) == null) {
            obj = new AlarmConfig();
        }
        if (obj != null) {
            return (AlarmConfig) obj;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.chileaf.fitness.device.wear.cl880.model.AlarmConfig");
    }
}
