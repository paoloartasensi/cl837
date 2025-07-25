package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.z0;

/* compiled from: EventLoop.kt */
public abstract class a1 extends y0 {
    /* access modifiers changed from: protected */
    public final void a(long j2, z0.b bVar) {
        i.b(bVar, "delayedTask");
        if (j0.a()) {
            if (!(this != l0.k)) {
                throw new AssertionError();
            }
        }
        l0.k.b(j2, bVar);
    }

    /* access modifiers changed from: protected */
    public abstract Thread s();

    /* access modifiers changed from: protected */
    public final void t() {
        Thread s = s();
        if (Thread.currentThread() != s) {
            e2 a = f2.a();
            if (a != null) {
                a.a(s);
            } else {
                LockSupport.unpark(s);
            }
        }
    }
}
