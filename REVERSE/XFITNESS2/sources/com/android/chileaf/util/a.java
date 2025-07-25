package com.android.chileaf.util;

import java.util.Calendar;

/* compiled from: DateUtil */
public class a {
    public static long a() {
        Calendar instance = Calendar.getInstance();
        instance.add(14, instance.get(15) + instance.get(16));
        return instance.getTimeInMillis() / 1000;
    }

    public static long a(long j2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j2 * 1000);
        instance.add(14, -(instance.get(15) + instance.get(16)));
        return instance.getTimeInMillis();
    }
}
