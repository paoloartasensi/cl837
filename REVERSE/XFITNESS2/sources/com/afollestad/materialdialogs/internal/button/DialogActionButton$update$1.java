package com.afollestad.materialdialogs.internal.button;

import android.content.Context;
import com.afollestad.materialdialogs.R$attr;
import com.afollestad.materialdialogs.j.e;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;

/* compiled from: DialogActionButton.kt */
final class DialogActionButton$update$1 extends Lambda implements a<Integer> {
    final /* synthetic */ Context $appContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DialogActionButton$update$1(Context context) {
        super(0);
        this.$appContext = context;
    }

    public final int invoke() {
        return e.a(e.a, this.$appContext, (Integer) null, Integer.valueOf(R$attr.colorPrimary), (a) null, 10, (Object) null);
    }
}
