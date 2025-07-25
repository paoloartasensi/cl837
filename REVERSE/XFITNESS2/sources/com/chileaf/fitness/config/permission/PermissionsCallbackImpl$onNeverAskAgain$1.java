package com.chileaf.fitness.config.permission;

import java.util.List;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: PermissionsCallbackImpl.kt */
final class PermissionsCallbackImpl$onNeverAskAgain$1 extends Lambda implements l<List<? extends String>, kotlin.l> {
    public static final PermissionsCallbackImpl$onNeverAskAgain$1 INSTANCE = new PermissionsCallbackImpl$onNeverAskAgain$1();

    PermissionsCallbackImpl$onNeverAskAgain$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((List<String>) (List) obj);
        return kotlin.l.a;
    }

    public final void invoke(List<String> list) {
        i.b(list, "it");
    }
}
