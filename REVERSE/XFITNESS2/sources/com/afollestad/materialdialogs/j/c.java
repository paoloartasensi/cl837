package com.afollestad.materialdialogs.j;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import kotlin.jvm.internal.i;

/* compiled from: Dimens.kt */
public final class c {
    public static final float a(View view, int i2) {
        i.b(view, "$this$dp");
        Resources resources = view.getResources();
        i.a((Object) resources, "resources");
        return TypedValue.applyDimension(1, (float) i2, resources.getDisplayMetrics());
    }
}
