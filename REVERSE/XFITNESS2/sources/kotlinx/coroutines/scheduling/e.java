package kotlinx.coroutines.scheduling;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.d1;

/* compiled from: Dispatcher.kt */
final class e extends d1 implements i, Executor {

    /* renamed from: i  reason: collision with root package name */
    private static final AtomicIntegerFieldUpdater f1835i = AtomicIntegerFieldUpdater.newUpdater(e.class, "inFlightTasks");
    private final ConcurrentLinkedQueue<Runnable> e = new ConcurrentLinkedQueue<>();

    /* renamed from: f  reason: collision with root package name */
    private final c f1836f;

    /* renamed from: g  reason: collision with root package name */
    private final int f1837g;

    /* renamed from: h  reason: collision with root package name */
    private final TaskMode f1838h;
    private volatile int inFlightTasks = 0;

    public e(c cVar, int i2, TaskMode taskMode) {
        i.b(cVar, "dispatcher");
        i.b(taskMode, "taskMode");
        this.f1836f = cVar;
        this.f1837g = i2;
        this.f1838h = taskMode;
    }

    /* JADX WARNING: Removed duplicated region for block: B:4:0x0010  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void a(java.lang.Runnable r3, boolean r4) {
        /*
            r2 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = f1835i
            int r0 = r0.incrementAndGet(r2)
            int r1 = r2.f1837g
            if (r0 > r1) goto L_0x0010
            kotlinx.coroutines.scheduling.c r0 = r2.f1836f
            r0.a(r3, r2, r4)
            return
        L_0x0010:
            java.util.concurrent.ConcurrentLinkedQueue<java.lang.Runnable> r0 = r2.e
            r0.add(r3)
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r3 = f1835i
            int r3 = r3.decrementAndGet(r2)
            int r0 = r2.f1837g
            if (r3 < r0) goto L_0x0020
            return
        L_0x0020:
            java.util.concurrent.ConcurrentLinkedQueue<java.lang.Runnable> r3 = r2.e
            java.lang.Object r3 = r3.poll()
            java.lang.Runnable r3 = (java.lang.Runnable) r3
            if (r3 == 0) goto L_0x002b
            goto L_0x0000
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.e.a(java.lang.Runnable, boolean):void");
    }

    public void close() {
        throw new IllegalStateException("Close cannot be invoked on LimitingBlockingDispatcher".toString());
    }

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        a(runnable, false);
    }

    public void execute(Runnable runnable) {
        i.b(runnable, "command");
        a(runnable, false);
    }

    public TaskMode j() {
        return this.f1838h;
    }

    public void m() {
        Runnable poll = this.e.poll();
        if (poll != null) {
            this.f1836f.a(poll, this, true);
            return;
        }
        f1835i.decrementAndGet(this);
        Runnable poll2 = this.e.poll();
        if (poll2 != null) {
            a(poll2, true);
        }
    }

    public String toString() {
        return super.toString() + "[dispatcher = " + this.f1836f + ']';
    }
}
