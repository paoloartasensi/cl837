package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: EventLoop.kt */
public final class c extends z0 {

    /* renamed from: j  reason: collision with root package name */
    private final Thread f1790j;

    public c(Thread thread) {
        i.b(thread, "thread");
        this.f1790j = thread;
    }

    /* access modifiers changed from: protected */
    public Thread s() {
        return this.f1790j;
    }
}
