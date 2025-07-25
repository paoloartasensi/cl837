package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: Delay.kt */
public final class p0 {
    public static final Object a(long j2, c<? super l> cVar) {
        if (j2 <= 0) {
            return l.a;
        }
        i iVar = new i(IntrinsicsKt__IntrinsicsJvmKt.a(cVar), 1);
        a(iVar.getContext()).a(j2, iVar);
        Object d = iVar.d();
        if (d == b.a()) {
            e.c(cVar);
        }
        return d;
    }

    public static final o0 a(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "$this$delay");
        CoroutineContext.a aVar = coroutineContext.get(d.b);
        if (!(aVar instanceof o0)) {
            aVar = null;
        }
        o0 o0Var = (o0) aVar;
        return o0Var != null ? o0Var : m0.a();
    }
}
