package com.afollestad.materialdialogs.lifecycle;

import android.content.Context;
import androidx.lifecycle.LifecycleOwner;
import com.afollestad.materialdialogs.MaterialDialog;
import kotlin.jvm.internal.i;

/* compiled from: LifecycleExt.kt */
public final class LifecycleExtKt {
    public static final MaterialDialog a(MaterialDialog materialDialog, LifecycleOwner lifecycleOwner) {
        i.b(materialDialog, "$this$lifecycleOwner");
        DialogLifecycleObserver dialogLifecycleObserver = new DialogLifecycleObserver(new LifecycleExtKt$lifecycleOwner$observer$1(materialDialog));
        if (lifecycleOwner == null) {
            Context f2 = materialDialog.f();
            if (!(f2 instanceof LifecycleOwner)) {
                f2 = null;
            }
            lifecycleOwner = (LifecycleOwner) f2;
            if (lifecycleOwner == null) {
                throw new IllegalStateException(materialDialog.f() + " is not a LifecycleOwner.");
            }
        }
        lifecycleOwner.getLifecycle().addObserver(dialogLifecycleObserver);
        return materialDialog;
    }
}
