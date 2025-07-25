package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: Exceptions.kt */
public final class CompletionHandlerException extends RuntimeException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CompletionHandlerException(String str, Throwable th) {
        super(str, th);
        i.b(str, "message");
        i.b(th, "cause");
    }
}
