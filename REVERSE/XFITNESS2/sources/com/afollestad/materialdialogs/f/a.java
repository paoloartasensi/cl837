package com.afollestad.materialdialogs.f;

import com.afollestad.materialdialogs.MaterialDialog;
import java.util.List;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: DialogCallbackExt.kt */
public final class a {
    public static final void a(List<l<MaterialDialog, kotlin.l>> list, MaterialDialog materialDialog) {
        i.b(list, "$this$invokeAll");
        i.b(materialDialog, "dialog");
        for (l<MaterialDialog, kotlin.l> invoke : list) {
            invoke.invoke(materialDialog);
        }
    }
}
