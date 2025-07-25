package kotlinx.coroutines;

import kotlin.Result;
import kotlin.i;

/* compiled from: CompletedExceptionally.kt */
public final class v {
    public static final <T> Object a(Object obj) {
        if (Result.m7isSuccessimpl(obj)) {
            i.a(obj);
            return obj;
        }
        Throwable r4 = Result.m4exceptionOrNullimpl(obj);
        if (r4 != null) {
            return new u(r4, false, 2, (f) null);
        }
        kotlin.jvm.internal.i.a();
        throw null;
    }
}
