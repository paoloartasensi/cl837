package org.koin.core.scope;

import kotlin.jvm.b.a;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.c;

/* compiled from: Scope.kt */
final class Scope$get$$inlined$synchronized$lambda$1 extends Lambda implements a<T> {
    final /* synthetic */ c $clazz$inlined;
    final /* synthetic */ a $parameters$inlined;
    final /* synthetic */ org.koin.core.f.a $qualifier$inlined;
    final /* synthetic */ Scope this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    Scope$get$$inlined$synchronized$lambda$1(Scope scope, c cVar, org.koin.core.f.a aVar, a aVar2) {
        super(0);
        this.this$0 = scope;
        this.$clazz$inlined = cVar;
        this.$qualifier$inlined = aVar;
        this.$parameters$inlined = aVar2;
    }

    public final T invoke() {
        return this.this$0.a(this.$qualifier$inlined, (c<?>) this.$clazz$inlined, (a<org.koin.core.e.a>) this.$parameters$inlined);
    }
}
