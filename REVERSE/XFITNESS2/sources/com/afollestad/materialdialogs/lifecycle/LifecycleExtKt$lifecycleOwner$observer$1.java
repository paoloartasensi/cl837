package com.afollestad.materialdialogs.lifecycle;

import com.afollestad.materialdialogs.MaterialDialog;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.reflect.d;

/* compiled from: LifecycleExt.kt */
final /* synthetic */ class LifecycleExtKt$lifecycleOwner$observer$1 extends FunctionReference implements a<l> {
    LifecycleExtKt$lifecycleOwner$observer$1(MaterialDialog materialDialog) {
        super(0, materialDialog);
    }

    public final String getName() {
        return "dismiss";
    }

    public final d getOwner() {
        return k.a(MaterialDialog.class);
    }

    public final String getSignature() {
        return "dismiss()V";
    }

    public final void invoke() {
        ((MaterialDialog) this.receiver).dismiss();
    }
}
