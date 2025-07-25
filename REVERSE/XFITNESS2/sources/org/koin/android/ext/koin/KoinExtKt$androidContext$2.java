package org.koin.android.ext.koin;

import android.app.Application;
import android.content.Context;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import org.koin.core.e.a;
import org.koin.core.scope.Scope;

/* compiled from: KoinExt.kt */
final class KoinExtKt$androidContext$2 extends Lambda implements p<Scope, a, Application> {
    final /* synthetic */ Context $androidContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KoinExtKt$androidContext$2(Context context) {
        super(2);
        this.$androidContext = context;
    }

    public final Application invoke(Scope scope, a aVar) {
        i.b(scope, "$receiver");
        i.b(aVar, "it");
        return (Application) this.$androidContext;
    }
}
