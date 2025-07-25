package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlinx.coroutines.j0;

/* compiled from: Atomic.kt */
public abstract class c<T> extends o {
    private static final AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "_consensus");
    private volatile Object _consensus = b.a;

    private final Object d(Object obj) {
        return c(obj) ? obj : this._consensus;
    }

    public final Object a(Object obj) {
        Object obj2 = this._consensus;
        if (obj2 == b.a) {
            obj2 = d(b(obj));
        }
        a(obj, obj2);
        return obj2;
    }

    public abstract void a(T t, Object obj);

    public abstract Object b(T t);

    public final boolean c(Object obj) {
        if (j0.a()) {
            if (!(obj != b.a)) {
                throw new AssertionError();
            }
        }
        return a.compareAndSet(this, b.a, obj);
    }
}
