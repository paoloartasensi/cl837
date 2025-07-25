package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: ThreadContext.kt */
final class ThreadContextKt$restoreState$1 extends Lambda implements p<z, CoroutineContext.a, z> {
    public static final ThreadContextKt$restoreState$1 INSTANCE = new ThreadContextKt$restoreState$1();

    ThreadContextKt$restoreState$1() {
        super(2);
    }

    public final z invoke(z zVar, CoroutineContext.a aVar) {
        i.b(zVar, "state");
        i.b(aVar, "element");
        if (aVar instanceof c2) {
            ((c2) aVar).a(zVar.a(), zVar.c());
        }
        return zVar;
    }
}
