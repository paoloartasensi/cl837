package com.chileaf.fitness.device.wear.cl880;

import android.view.View;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.a;
import com.afollestad.materialdialogs.lifecycle.LifecycleExtKt;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chileaf.fitness.R$string;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: AlarmSettingActivity.kt */
final class AlarmSettingActivity$initData$2 implements BaseQuickAdapter.OnItemLongClickListener {
    final /* synthetic */ AlarmSettingActivity a;

    AlarmSettingActivity$initData$2(AlarmSettingActivity alarmSettingActivity) {
        this.a = alarmSettingActivity;
    }

    public final boolean onItemLongClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i2) {
        i.b(baseQuickAdapter, "adapter");
        MaterialDialog materialDialog = new MaterialDialog(this.a, (a) null, 2, (f) null);
        MaterialDialog.a(materialDialog, Float.valueOf(8.0f), (Integer) null, 2, (Object) null);
        MaterialDialog.a(materialDialog, (Integer) null, this.a.getString(R$string.confirm_delete), (l) null, 5, (Object) null);
        MaterialDialog materialDialog2 = materialDialog;
        MaterialDialog.c(materialDialog2, (Integer) null, this.a.getString(R$string.confirm), new AlarmSettingActivity$initData$2$$special$$inlined$show$lambda$1(this, baseQuickAdapter, i2), 1, (Object) null);
        MaterialDialog.b(materialDialog2, (Integer) null, this.a.getString(R$string.cancel), (l) null, 5, (Object) null);
        LifecycleExtKt.a(materialDialog, this.a);
        materialDialog.show();
        return true;
    }
}
