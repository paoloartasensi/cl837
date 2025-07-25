package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: Unconfined.kt */
public final class g2 extends b0 {
    public static final g2 e = new g2();

    private g2() {
    }

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        throw new UnsupportedOperationException();
    }

    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return false;
    }

    public String toString() {
        return "Unconfined";
    }
}
