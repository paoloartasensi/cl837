package androidx.lifecycle;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.b0;

/* compiled from: PausingDispatcher.kt */
public final class PausingDispatcher extends b0 {
    public final DispatchQueue dispatchQueue = new DispatchQueue();

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        this.dispatchQueue.runOrEnqueue(runnable);
    }
}
