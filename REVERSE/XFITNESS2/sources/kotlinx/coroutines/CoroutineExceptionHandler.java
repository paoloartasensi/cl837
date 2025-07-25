package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: CoroutineExceptionHandler.kt */
public interface CoroutineExceptionHandler extends CoroutineContext.a {
    public static final a c = a.a;

    /* compiled from: CoroutineExceptionHandler.kt */
    public static final class a implements CoroutineContext.b<CoroutineExceptionHandler> {
        static final /* synthetic */ a a = new a();

        private a() {
        }
    }

    void handleException(CoroutineContext coroutineContext, Throwable th);
}
