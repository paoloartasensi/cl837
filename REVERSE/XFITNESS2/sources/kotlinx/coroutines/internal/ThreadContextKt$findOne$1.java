package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: ThreadContext.kt */
final class ThreadContextKt$findOne$1 extends Lambda implements p<c2<?>, CoroutineContext.a, c2<?>> {
    public static final ThreadContextKt$findOne$1 INSTANCE = new ThreadContextKt$findOne$1();

    ThreadContextKt$findOne$1() {
        super(2);
    }

    public final c2<?> invoke(c2<?> c2Var, CoroutineContext.a aVar) {
        i.b(aVar, "element");
        if (c2Var != null) {
            return c2Var;
        }
        if (!(aVar instanceof c2)) {
            aVar = null;
        }
        return (c2) aVar;
    }
}
