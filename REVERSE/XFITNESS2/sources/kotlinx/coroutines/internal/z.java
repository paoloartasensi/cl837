package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: ThreadContext.kt */
final class z {
    private Object[] a;
    private int b;
    private final CoroutineContext c;

    public z(CoroutineContext coroutineContext, int i2) {
        i.b(coroutineContext, "context");
        this.c = coroutineContext;
        this.a = new Object[i2];
    }

    public final CoroutineContext a() {
        return this.c;
    }

    public final void b() {
        this.b = 0;
    }

    public final Object c() {
        Object[] objArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        return objArr[i2];
    }

    public final void a(Object obj) {
        Object[] objArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        objArr[i2] = obj;
    }
}
