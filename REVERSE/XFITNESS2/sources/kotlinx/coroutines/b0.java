package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.a;
import kotlin.coroutines.c;
import kotlin.coroutines.d;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineDispatcher.kt */
public abstract class b0 extends a implements d {
    public b0() {
        super(d.b);
    }

    public abstract void dispatch(CoroutineContext coroutineContext, Runnable runnable);

    public void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        dispatch(coroutineContext, runnable);
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        i.b(bVar, "key");
        return d.a.a((d) this, bVar);
    }

    public final <T> c<T> interceptContinuation(c<? super T> cVar) {
        i.b(cVar, "continuation");
        return new q0(this, cVar);
    }

    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return true;
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        return d.a.b(this, bVar);
    }

    public final b0 plus(b0 b0Var) {
        i.b(b0Var, "other");
        return b0Var;
    }

    public void releaseInterceptedContinuation(c<?> cVar) {
        i.b(cVar, "continuation");
        d.a.a((d) this, cVar);
    }

    public String toString() {
        return k0.a((Object) this) + '@' + k0.b(this);
    }
}
