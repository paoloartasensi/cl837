package org.koin.core.b;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;
import org.koin.core.KoinApplication;

/* compiled from: GlobalContext.kt */
public final class b {
    public static final KoinApplication a(l<? super KoinApplication, kotlin.l> lVar) {
        i.b(lVar, "appDeclaration");
        KoinApplication a = KoinApplication.c.a();
        a.a(a);
        lVar.invoke(a);
        a.a();
        return a;
    }
}
