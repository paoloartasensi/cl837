package com.afollestad.materialdialogs.j;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.core.content.c.f;
import com.afollestad.materialdialogs.MaterialDialog;
import kotlin.jvm.internal.i;

/* compiled from: Fonts.kt */
public final class d {
    public static /* synthetic */ Typeface a(MaterialDialog materialDialog, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            num = null;
        }
        if ((i2 & 2) != 0) {
            num2 = null;
        }
        return a(materialDialog, num, num2);
    }

    public static final Typeface a(MaterialDialog materialDialog, Integer num, Integer num2) {
        i.b(materialDialog, "$this$font");
        e.a.a("font", (Object) num2, num);
        if (num != null) {
            return a(materialDialog.f(), num.intValue());
        }
        if (num2 != null) {
            TypedArray obtainStyledAttributes = materialDialog.f().getTheme().obtainStyledAttributes(new int[]{num2.intValue()});
            try {
                int resourceId = obtainStyledAttributes.getResourceId(0, 0);
                if (resourceId == 0) {
                    return null;
                }
                Typeface a = a(materialDialog.f(), resourceId);
                obtainStyledAttributes.recycle();
                return a;
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
    }

    private static final Typeface a(Context context, int i2) {
        try {
            return f.a(context, i2);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
