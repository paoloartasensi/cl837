package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: Exceptions.common.kt */
public final class CoroutinesInternalError extends Error {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CoroutinesInternalError(String str, Throwable th) {
        super(str, th);
        i.b(str, "message");
        i.b(th, "cause");
    }
}
