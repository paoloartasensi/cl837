package com.chileaf.fitness.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.i;

/* compiled from: DevicesViewModel.kt */
public final class DevicesViewModel$mBluetoothStateReceiver$1 extends BroadcastReceiver {
    final /* synthetic */ DevicesViewModel a;

    DevicesViewModel$mBluetoothStateReceiver$1(DevicesViewModel devicesViewModel) {
        this.a = devicesViewModel;
    }

    public void onReceive(Context context, Intent intent) {
        i.b(context, "context");
        i.b(intent, "intent");
        int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
        int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
        if (intExtra != 10) {
            if (intExtra == 12) {
                this.a.c().b();
                return;
            } else if (intExtra != 13) {
                return;
            }
        }
        if (intExtra2 != 13 && intExtra2 != 10) {
            this.a.l();
            this.a.c().a();
        }
    }
}
