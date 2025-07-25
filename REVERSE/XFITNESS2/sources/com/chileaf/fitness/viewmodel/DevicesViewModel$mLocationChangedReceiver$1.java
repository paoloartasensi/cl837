package com.chileaf.fitness.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.chileaf.fitness.d.b;
import kotlin.jvm.internal.i;

/* compiled from: DevicesViewModel.kt */
public final class DevicesViewModel$mLocationChangedReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ DevicesViewModel a;

    DevicesViewModel$mLocationChangedReceiver$1(DevicesViewModel devicesViewModel) {
        this.a = devicesViewModel;
    }

    public void onReceive(Context context, Intent intent) {
        i.b(context, "context");
        i.b(intent, "intent");
        this.a.c().a(b.b.a(context));
    }
}
