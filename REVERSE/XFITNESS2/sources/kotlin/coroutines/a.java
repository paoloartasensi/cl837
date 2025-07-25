package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContextImpl.kt */
public abstract class a implements CoroutineContext.a {
    private final CoroutineContext.b<?> key;

    public a(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        this.key = bVar;
    }

    public <R> R fold(R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
        i.b(pVar, "operation");
        return CoroutineContext.a.C0089a.a(this, r, pVar);
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        i.b(bVar, "key");
        return CoroutineContext.a.C0089a.a((CoroutineContext.a) this, bVar);
    }

    public CoroutineContext.b<?> getKey() {
        return this.key;
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        return CoroutineContext.a.C0089a.b(this, bVar);
    }

    public CoroutineContext plus(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return CoroutineContext.a.C0089a.a((CoroutineContext.a) this, coroutineContext);
    }
}
