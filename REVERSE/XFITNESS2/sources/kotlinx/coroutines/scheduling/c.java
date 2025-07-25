package kotlinx.coroutines.scheduling;

import java.util.concurrent.RejectedExecutionException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.b0;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.l0;

/* compiled from: Dispatcher.kt */
public class c extends d1 {
    private CoroutineScheduler e;

    /* renamed from: f  reason: collision with root package name */
    private final int f1831f;

    /* renamed from: g  reason: collision with root package name */
    private final int f1832g;

    /* renamed from: h  reason: collision with root package name */
    private final long f1833h;

    /* renamed from: i  reason: collision with root package name */
    private final String f1834i;

    public c(int i2, int i3, long j2, String str) {
        i.b(str, "schedulerName");
        this.f1831f = i2;
        this.f1832g = i3;
        this.f1833h = j2;
        this.f1834i = str;
        this.e = n();
    }

    private final CoroutineScheduler n() {
        return new CoroutineScheduler(this.f1831f, this.f1832g, this.f1833h, this.f1834i);
    }

    public final b0 a(int i2) {
        if (i2 > 0) {
            return new e(this, i2, TaskMode.PROBABLY_BLOCKING);
        }
        throw new IllegalArgumentException(("Expected positive parallelism level, but have " + i2).toString());
    }

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        try {
            CoroutineScheduler.a(this.e, runnable, (i) null, false, 6, (Object) null);
        } catch (RejectedExecutionException unused) {
            l0.k.dispatch(coroutineContext, runnable);
        }
    }

    public void dispatchYield(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        try {
            CoroutineScheduler.a(this.e, runnable, (i) null, true, 2, (Object) null);
        } catch (RejectedExecutionException unused) {
            l0.k.dispatchYield(coroutineContext, runnable);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(int i2, int i3, String str, int i4, f fVar) {
        this((i4 & 1) != 0 ? k.c : i2, (i4 & 2) != 0 ? k.d : i3, (i4 & 4) != 0 ? "DefaultDispatcher" : str);
    }

    public final void a(Runnable runnable, i iVar, boolean z) {
        i.b(runnable, "block");
        i.b(iVar, "context");
        try {
            this.e.a(runnable, iVar, z);
        } catch (RejectedExecutionException unused) {
            l0.k.a((Runnable) this.e.a(runnable, iVar));
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public c(int i2, int i3, String str) {
        this(i2, i3, k.e, str);
        i.b(str, "schedulerName");
    }
}
