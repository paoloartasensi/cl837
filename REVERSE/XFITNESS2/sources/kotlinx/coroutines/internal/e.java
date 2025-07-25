package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.g0;

/* compiled from: Scopes.kt */
public final class e implements g0 {
    private final CoroutineContext e;

    public e(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        this.e = coroutineContext;
    }

    public CoroutineContext getCoroutineContext() {
        return this.e;
    }
}
