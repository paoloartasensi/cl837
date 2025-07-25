package kotlin;

import kotlin.Result;

/* compiled from: Result.kt */
public final class i {
    public static final Object a(Throwable th) {
        kotlin.jvm.internal.i.b(th, "exception");
        return new Result.Failure(th);
    }

    public static final void a(Object obj) {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
    }
}
