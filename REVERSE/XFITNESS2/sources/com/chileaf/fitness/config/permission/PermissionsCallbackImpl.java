package com.chileaf.fitness.config.permission;

import java.util.List;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: PermissionsCallbackImpl.kt */
public final class PermissionsCallbackImpl implements d {
    private a<l> a = PermissionsCallbackImpl$onGranted$1.INSTANCE;
    private kotlin.jvm.b.l<? super List<String>, l> b = PermissionsCallbackImpl$onDenied$1.INSTANCE;
    private kotlin.jvm.b.l<? super c, l> c = PermissionsCallbackImpl$onShowRationale$1.INSTANCE;
    private kotlin.jvm.b.l<? super List<String>, l> d = PermissionsCallbackImpl$onNeverAskAgain$1.INSTANCE;

    public final void a(a<l> aVar) {
        i.b(aVar, "func");
        this.a = aVar;
    }

    public final void b(kotlin.jvm.b.l<? super List<String>, l> lVar) {
        i.b(lVar, "func");
        this.d = lVar;
    }

    public final void a(kotlin.jvm.b.l<? super List<String>, l> lVar) {
        i.b(lVar, "func");
        this.b = lVar;
    }

    public void b(List<String> list) {
        i.b(list, "permissions");
        this.b.invoke(list);
    }

    public void a() {
        this.a.invoke();
    }

    public void a(c cVar) {
        i.b(cVar, "request");
        this.c.invoke(cVar);
    }

    public void a(List<String> list) {
        i.b(list, "permissions");
        this.d.invoke(list);
    }
}
