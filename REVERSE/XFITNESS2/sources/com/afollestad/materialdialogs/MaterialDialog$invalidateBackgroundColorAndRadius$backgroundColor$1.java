package com.afollestad.materialdialogs;

import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: MaterialDialog.kt */
final class MaterialDialog$invalidateBackgroundColorAndRadius$backgroundColor$1 extends Lambda implements a<Integer> {
    final /* synthetic */ MaterialDialog this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MaterialDialog$invalidateBackgroundColorAndRadius$backgroundColor$1(MaterialDialog materialDialog) {
        super(0);
        this.this$0 = materialDialog;
    }

    public final int invoke() {
        return com.afollestad.materialdialogs.j.a.a(this.this$0, (Integer) null, Integer.valueOf(R$attr.colorBackgroundFloating), (a) null, 5, (Object) null);
    }
}
