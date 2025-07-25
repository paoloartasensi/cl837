package com.afollestad.materialdialogs.internal.list;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.j.b;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.reflect.d;

/* compiled from: DialogRecyclerView.kt */
final /* synthetic */ class DialogRecyclerView$attach$1 extends FunctionReference implements p<Boolean, Boolean, l> {
    DialogRecyclerView$attach$1(MaterialDialog materialDialog) {
        super(2, materialDialog);
    }

    public final String getName() {
        return "invalidateDividers";
    }

    public final d getOwner() {
        return k.a(b.class, "core");
    }

    public final String getSignature() {
        return "invalidateDividers(Lcom/afollestad/materialdialogs/MaterialDialog;ZZ)V";
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue());
        return l.a;
    }

    public final void invoke(boolean z, boolean z2) {
        b.a((MaterialDialog) this.receiver, z, z2);
    }
}
