package com.chileaf.fitness.device.wear.cl880.external;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

public class PhoneCallReceiver extends BroadcastReceiver {
    private String a;
    private int b = 0;

    public void a(int i2, String str) {
        int i3 = this.b;
        if (i3 != i2) {
            int i4 = 1;
            if (i2 != 2) {
                if (i2 == 1) {
                    this.a = str;
                } else {
                    i4 = i2 == 0 ? 0 : -1;
                }
                if (i4 != -1) {
                    b bVar = new b();
                    bVar.a = this.a;
                    bVar.c = i4;
                    c.l().a(bVar);
                }
            } else if (i3 != 1) {
                a aVar = new a();
                aVar.d = str;
                NotificationType notificationType = NotificationType.MISSEDCALL;
                aVar.f1222f = notificationType;
                aVar.c = notificationType.title;
                aVar.b = SystemClock.currentThreadTimeMillis();
                c.l().a(aVar);
                this.a = str;
            }
            this.b = i2;
        }
    }

    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String action = intent.getAction();
            if (action != null && action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
                this.a = extras.getString("android.intent.extra.PHONE_NUMBER");
            } else if (intent.hasExtra("incoming_number")) {
                a(telephonyManager.getCallState(), extras.getString("incoming_number"));
            }
        }
    }
}
