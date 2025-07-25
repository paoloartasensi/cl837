package com.chileaf.fitness.device.wear.cl880.external;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsMessage;
import java.util.LinkedHashMap;
import java.util.Map;

public class SMSReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Object[] objArr;
        a aVar = new a();
        aVar.f1222f = NotificationType.SMS;
        aVar.b = SystemClock.currentThreadTimeMillis();
        Bundle extras = intent.getExtras();
        if (extras != null && (objArr = (Object[]) extras.get("pdus")) != null) {
            int length = objArr.length;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            SmsMessage[] smsMessageArr = new SmsMessage[length];
            for (int i2 = 0; i2 < length; i2++) {
                smsMessageArr[i2] = SmsMessage.createFromPdu((byte[]) objArr[i2]);
                String originatingAddress = smsMessageArr[i2].getOriginatingAddress();
                if (!linkedHashMap.containsKey(originatingAddress)) {
                    linkedHashMap.put(originatingAddress, new StringBuilder());
                }
                ((StringBuilder) linkedHashMap.get(originatingAddress)).append(smsMessageArr[i2].getMessageBody());
            }
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                String str = (String) entry.getKey();
                if (str != null) {
                    aVar.c = str;
                    aVar.d = ((StringBuilder) entry.getValue()).toString();
                    c.l().a(aVar);
                }
            }
        }
    }
}
