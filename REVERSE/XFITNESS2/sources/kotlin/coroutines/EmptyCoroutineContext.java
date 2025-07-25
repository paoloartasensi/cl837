package kotlin.coroutines;

import java.io.Serializable;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContextImpl.kt */
public final class EmptyCoroutineContext implements CoroutineContext, Serializable {
    public static final EmptyCoroutineContext INSTANCE = new EmptyCoroutineContext();
    private static final long serialVersionUID = 0;

    private EmptyCoroutineContext() {
    }

    private final Object readResolve() {
        return INSTANCE;
    }

    public <R> R fold(R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
        i.b(pVar, "operation");
        return r;
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        i.b(bVar, "key");
        return null;
    }

    public int hashCode() {
        return 0;
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        i.b(bVar, "key");
        return this;
    }

    public CoroutineContext plus(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return coroutineContext;
    }

    public String toString() {
        return "EmptyCoroutineContext";
    }
}
