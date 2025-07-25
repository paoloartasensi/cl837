package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: EventLoop.kt */
public final class b1 {
    public static final y0 a() {
        Thread currentThread = Thread.currentThread();
        i.a((Object) currentThread, "Thread.currentThread()");
        return new c(currentThread);
    }
}
