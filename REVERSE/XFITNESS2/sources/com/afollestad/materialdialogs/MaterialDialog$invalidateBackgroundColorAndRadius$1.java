package com.afollestad.materialdialogs;

import android.content.Context;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: MaterialDialog.kt */
final class MaterialDialog$invalidateBackgroundColorAndRadius$1 extends Lambda implements a<Float> {
    final /* synthetic */ MaterialDialog this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MaterialDialog$invalidateBackgroundColorAndRadius$1(MaterialDialog materialDialog) {
        super(0);
        this.this$0 = materialDialog;
    }

    public final float invoke() {
        Context context = this.this$0.getContext();
        i.a((Object) context, "context");
        return context.getResources().getDimension(R$dimen.md_dialog_default_corner_radius);
    }
}
