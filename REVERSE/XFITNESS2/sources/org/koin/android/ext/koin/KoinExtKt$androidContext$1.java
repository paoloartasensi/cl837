package org.koin.android.ext.koin;

import android.content.Context;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import org.koin.core.e.a;
import org.koin.core.scope.Scope;

/* compiled from: KoinExt.kt */
final class KoinExtKt$androidContext$1 extends Lambda implements p<Scope, a, Context> {
    final /* synthetic */ Context $androidContext;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KoinExtKt$androidContext$1(Context context) {
        super(2);
        this.$androidContext = context;
    }

    public final Context invoke(Scope scope, a aVar) {
        i.b(scope, "$receiver");
        i.b(aVar, "it");
        return this.$androidContext;
    }
}
