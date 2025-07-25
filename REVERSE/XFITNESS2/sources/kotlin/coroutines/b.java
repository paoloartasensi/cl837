package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.a;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContextImpl.kt */
public abstract class b<B extends CoroutineContext.a, E extends B> implements CoroutineContext.b<E> {
    private final CoroutineContext.b<?> a;
    private final l<CoroutineContext.a, E> b;

    public final E a(CoroutineContext.a aVar) {
        i.b(aVar, "element");
        return (CoroutineContext.a) this.b.invoke(aVar);
    }

    public final boolean a(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        return bVar == this || this.a == bVar;
    }
}
