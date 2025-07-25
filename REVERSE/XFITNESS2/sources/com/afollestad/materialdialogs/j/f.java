package com.afollestad.materialdialogs.j;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kotlin.jvm.internal.i;

/* compiled from: Views.kt */
public final class f {
    public static /* synthetic */ Object a(ViewGroup viewGroup, int i2, ViewGroup viewGroup2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            viewGroup2 = viewGroup;
        }
        return a(viewGroup, i2, viewGroup2);
    }

    public static final <T extends View> boolean b(T t) {
        i.b(t, "$this$isRtl");
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        Resources resources = t.getResources();
        i.a((Object) resources, "resources");
        Configuration configuration = resources.getConfiguration();
        i.a((Object) configuration, "resources.configuration");
        return configuration.getLayoutDirection() == 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x002e A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T extends android.view.View> boolean c(T r3) {
        /*
            java.lang.String r0 = "$this$isVisible"
            kotlin.jvm.internal.i.b(r3, r0)
            boolean r0 = r3 instanceof android.widget.Button
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0028
            android.widget.Button r3 = (android.widget.Button) r3
            int r0 = r3.getVisibility()
            if (r0 != 0) goto L_0x002f
            java.lang.CharSequence r3 = r3.getText()
            java.lang.String r0 = "this.text"
            kotlin.jvm.internal.i.a((java.lang.Object) r3, (java.lang.String) r0)
            java.lang.CharSequence r3 = kotlin.s.m.d(r3)
            boolean r3 = kotlin.s.l.a(r3)
            r3 = r3 ^ r2
            if (r3 == 0) goto L_0x002f
            goto L_0x002e
        L_0x0028:
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L_0x002f
        L_0x002e:
            r1 = 1
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.j.f.c(android.view.View):boolean");
    }

    public static final <T> T a(ViewGroup viewGroup, int i2, ViewGroup viewGroup2) {
        i.b(viewGroup, "$this$inflate");
        return LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup2, false);
    }

    public static final void b(TextView textView) {
        i.b(textView, "$this$setGravityStartCompat");
        if (Build.VERSION.SDK_INT >= 17) {
            textView.setTextAlignment(5);
        }
        textView.setGravity(8388627);
    }

    public static final <T extends View> boolean a(T t) {
        i.b(t, "$this$isNotVisible");
        return !c(t);
    }

    public static final void a(TextView textView) {
        i.b(textView, "$this$setGravityEndCompat");
        if (Build.VERSION.SDK_INT >= 17) {
            textView.setTextAlignment(6);
        }
        textView.setGravity(8388629);
    }
}
