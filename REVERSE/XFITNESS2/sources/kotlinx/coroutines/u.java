package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.i;

/* compiled from: CompletedExceptionally.kt */
public class u {
    private static final AtomicIntegerFieldUpdater b = AtomicIntegerFieldUpdater.newUpdater(u.class, "_handled");
    private volatile int _handled;
    public final Throwable a;

    public u(Throwable th, boolean z) {
        i.b(th, "cause");
        this.a = th;
        this._handled = z ? 1 : 0;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [boolean, int] */
    public final boolean a() {
        return this._handled;
    }

    public final boolean b() {
        return b.compareAndSet(this, 0, 1);
    }

    public String toString() {
        return k0.a((Object) this) + '[' + this.a + ']';
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ u(Throwable th, boolean z, int i2, f fVar) {
        this(th, (i2 & 2) != 0 ? false : z);
    }
}
