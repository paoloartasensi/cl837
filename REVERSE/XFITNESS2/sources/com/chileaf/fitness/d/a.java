package com.chileaf.fitness.d;

import android.content.Context;
import kotlin.jvm.internal.i;

/* compiled from: AppUtil.kt */
public final class a {
    public static final a a = new a();

    private a() {
    }

    public final synchronized String a(Context context) {
        i.b(context, "context");
        try {
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    }
}
