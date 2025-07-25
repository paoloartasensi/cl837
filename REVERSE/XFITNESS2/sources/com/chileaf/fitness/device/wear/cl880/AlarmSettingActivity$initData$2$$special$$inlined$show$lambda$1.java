package com.chileaf.fitness.device.wear.cl880;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: AlarmSettingActivity.kt */
final class AlarmSettingActivity$initData$2$$special$$inlined$show$lambda$1 extends Lambda implements l<MaterialDialog, kotlin.l> {
    final /* synthetic */ BaseQuickAdapter $adapter$inlined;
    final /* synthetic */ int $position$inlined;
    final /* synthetic */ AlarmSettingActivity$initData$2 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    AlarmSettingActivity$initData$2$$special$$inlined$show$lambda$1(AlarmSettingActivity$initData$2 alarmSettingActivity$initData$2, BaseQuickAdapter baseQuickAdapter, int i2) {
        super(1);
        this.this$0 = alarmSettingActivity$initData$2;
        this.$adapter$inlined = baseQuickAdapter;
        this.$position$inlined = i2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((MaterialDialog) obj);
        return kotlin.l.a;
    }

    public final void invoke(MaterialDialog materialDialog) {
        i.b(materialDialog, "it");
        this.$adapter$inlined.remove(this.$position$inlined);
        this.this$0.a.s();
    }
}
