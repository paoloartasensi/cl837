package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.c;
import kotlin.jvm.internal.i;

/* compiled from: DebugProbes.kt */
public final class e {
    public static final <T> c<T> a(c<? super T> cVar) {
        i.b(cVar, "completion");
        return cVar;
    }

    public static final void b(c<?> cVar) {
        i.b(cVar, "frame");
    }

    public static final void c(c<?> cVar) {
        i.b(cVar, "frame");
    }
}
