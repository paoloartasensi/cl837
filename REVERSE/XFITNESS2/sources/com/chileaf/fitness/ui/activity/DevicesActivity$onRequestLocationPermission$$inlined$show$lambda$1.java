package com.chileaf.fitness.ui.activity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chileaf.fitness.d.b;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: DevicesActivity.kt */
final class DevicesActivity$onRequestLocationPermission$$inlined$show$lambda$1 extends Lambda implements l<MaterialDialog, kotlin.l> {
    final /* synthetic */ DevicesActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DevicesActivity$onRequestLocationPermission$$inlined$show$lambda$1(DevicesActivity devicesActivity) {
        super(1);
        this.this$0 = devicesActivity;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((MaterialDialog) obj);
        return kotlin.l.a;
    }

    public final void invoke(MaterialDialog materialDialog) {
        i.b(materialDialog, "it");
        b.b.d(this.this$0);
    }
}
