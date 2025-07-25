package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineExceptionHandler.kt */
public final class d0 {
    public static final void a(CoroutineContext coroutineContext, Throwable th) {
        i.b(coroutineContext, "context");
        i.b(th, "exception");
        try {
            CoroutineExceptionHandler coroutineExceptionHandler = (CoroutineExceptionHandler) coroutineContext.get(CoroutineExceptionHandler.c);
            if (coroutineExceptionHandler != null) {
                coroutineExceptionHandler.handleException(coroutineContext, th);
            } else {
                c0.a(coroutineContext, th);
            }
        } catch (Throwable th2) {
            c0.a(coroutineContext, a(th, th2));
        }
    }

    public static final Throwable a(Throwable th, Throwable th2) {
        i.b(th, "originalException");
        i.b(th2, "thrownException");
        if (th == th2) {
            return th;
        }
        RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
        b.a(runtimeException, th);
        return runtimeException;
    }
}
