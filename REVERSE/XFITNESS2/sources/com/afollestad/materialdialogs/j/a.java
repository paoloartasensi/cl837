package com.afollestad.materialdialogs.j;

import android.graphics.Color;
import com.afollestad.materialdialogs.MaterialDialog;
import kotlin.jvm.internal.i;

/* compiled from: Colors.kt */
public final class a {
    public static /* synthetic */ int a(MaterialDialog materialDialog, Integer num, Integer num2, kotlin.jvm.b.a aVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            num2 = null;
        }
        if ((i2 & 4) != 0) {
            aVar = null;
        }
        return a(materialDialog, num, num2, aVar);
    }

    public static final int a(MaterialDialog materialDialog, Integer num, Integer num2, kotlin.jvm.b.a<Integer> aVar) {
        i.b(materialDialog, "$this$resolveColor");
        return e.a.a(materialDialog.f(), num, num2, aVar);
    }

    public static final int a(int i2, float f2) {
        return Color.argb((int) (((float) 255) * f2), Color.red(i2), Color.green(i2), Color.blue(i2));
    }
}
