package kotlinx.coroutines.scheduling;

import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.internal.u;

/* compiled from: Tasks.kt */
public final class k {
    public static final long a = w.a("kotlinx.coroutines.scheduler.resolution.ns", 100000, 0, 0, 12, (Object) null);
    public static final int b = w.a("kotlinx.coroutines.scheduler.offload.threshold", 96, 0, 128, 4, (Object) null);
    public static final int c = w.a("kotlinx.coroutines.scheduler.core.pool.size", f.a(u.a(), 2), 1, 0, 8, (Object) null);
    public static final int d = w.a("kotlinx.coroutines.scheduler.max.pool.size", f.a(u.a() * 128, c, 2097150), 0, 2097150, 4, (Object) null);
    public static final long e = TimeUnit.SECONDS.toNanos(w.a("kotlinx.coroutines.scheduler.keep.alive.sec", 5, 0, 0, 12, (Object) null));

    /* renamed from: f  reason: collision with root package name */
    public static l f1842f = f.a;

    static {
        int unused = w.a("kotlinx.coroutines.scheduler.blocking.parallelism", 16, 0, 0, 12, (Object) null);
    }
}
