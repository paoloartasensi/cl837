package kotlinx.coroutines;

import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.a;

/* compiled from: EventLoop.common.kt */
public abstract class y0 extends b0 {
    private long e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1844f;

    /* renamed from: g  reason: collision with root package name */
    private a<t0<?>> f1845g;

    private final long c(boolean z) {
        return z ? 4294967296L : 1;
    }

    public final void a(t0<?> t0Var) {
        i.b(t0Var, "task");
        a<t0<?>> aVar = this.f1845g;
        if (aVar == null) {
            aVar = new a<>();
            this.f1845g = aVar;
        }
        aVar.a(t0Var);
    }

    public final void b(boolean z) {
        this.e += c(z);
        if (!z) {
            this.f1844f = true;
        }
    }

    /* access modifiers changed from: protected */
    public long n() {
        a<t0<?>> aVar = this.f1845g;
        if (aVar == null || aVar.a()) {
            return Long.MAX_VALUE;
        }
        return 0;
    }

    public final boolean o() {
        return this.e >= c(true);
    }

    public final boolean p() {
        a<t0<?>> aVar = this.f1845g;
        if (aVar != null) {
            return aVar.a();
        }
        return true;
    }

    public final boolean q() {
        t0 b;
        a<t0<?>> aVar = this.f1845g;
        if (aVar == null || (b = aVar.b()) == null) {
            return false;
        }
        b.run();
        return true;
    }

    /* access modifiers changed from: protected */
    public void r() {
    }

    public static /* synthetic */ void a(y0 y0Var, boolean z, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 1) != 0) {
                z = false;
            }
            y0Var.b(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incrementUseCount");
    }

    public final void a(boolean z) {
        long c = this.e - c(z);
        this.e = c;
        if (c <= 0) {
            if (j0.a()) {
                if (!(this.e == 0)) {
                    throw new AssertionError();
                }
            }
            if (this.f1844f) {
                r();
            }
        }
    }
}
