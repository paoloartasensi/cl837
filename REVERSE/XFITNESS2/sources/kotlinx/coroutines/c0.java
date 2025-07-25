package kotlinx.coroutines;

import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineExceptionHandlerImpl.kt */
public final class c0 {
    private static final List<CoroutineExceptionHandler> a;

    static {
        Iterator a2 = a.a();
        i.a((Object) a2, "ServiceLoader.load(\n    ….classLoader\n).iterator()");
        a = h.b(f.a(a2));
    }

    public static final void a(CoroutineContext coroutineContext, Throwable th) {
        i.b(coroutineContext, "context");
        i.b(th, "exception");
        for (CoroutineExceptionHandler handleException : a) {
            try {
                handleException.handleException(coroutineContext, th);
            } catch (Throwable th2) {
                Thread currentThread = Thread.currentThread();
                i.a((Object) currentThread, "currentThread");
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, d0.a(th, th2));
            }
        }
        Thread currentThread2 = Thread.currentThread();
        i.a((Object) currentThread2, "currentThread");
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th);
    }
}
