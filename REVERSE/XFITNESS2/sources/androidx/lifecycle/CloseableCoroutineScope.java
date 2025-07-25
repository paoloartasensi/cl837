package androidx.lifecycle;

import java.io.Closeable;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.g0;

/* compiled from: ViewModel.kt */
public final class CloseableCoroutineScope implements Closeable, g0 {
    private final CoroutineContext coroutineContext;

    public CloseableCoroutineScope(CoroutineContext coroutineContext2) {
        i.b(coroutineContext2, "context");
        this.coroutineContext = coroutineContext2;
    }

    public void close() {
        o1.a(getCoroutineContext(), (CancellationException) null, 1, (Object) null);
    }

    public CoroutineContext getCoroutineContext() {
        return this.coroutineContext;
    }
}
