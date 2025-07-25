package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: ThreadContext.kt */
final class ThreadContextKt$updateState$1 extends Lambda implements p<z, CoroutineContext.a, z> {
    public static final ThreadContextKt$updateState$1 INSTANCE = new ThreadContextKt$updateState$1();

    ThreadContextKt$updateState$1() {
        super(2);
    }

    public final z invoke(z zVar, CoroutineContext.a aVar) {
        i.b(zVar, "state");
        i.b(aVar, "element");
        if (aVar instanceof c2) {
            zVar.a(((c2) aVar).a(zVar.a()));
        }
        return zVar;
    }
}
