package kotlinx.coroutines;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.i;

/* compiled from: DefaultExecutor.kt */
public final class l0 extends z0 implements Runnable {
    private static volatile Thread _thread;
    private static volatile int debugStatus;

    /* renamed from: j  reason: collision with root package name */
    private static final long f1807j;
    public static final l0 k;

    static {
        Long l;
        l0 l0Var = new l0();
        k = l0Var;
        y0.a(l0Var, false, 1, (Object) null);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        i.a((Object) l, "try {\n            java.l…AULT_KEEP_ALIVE\n        }");
        f1807j = timeUnit.toNanos(l.longValue());
    }

    private l0() {
    }

    private final synchronized boolean A() {
        if (z()) {
            return false;
        }
        debugStatus = 1;
        notifyAll();
        return true;
    }

    private final synchronized void x() {
        if (z()) {
            debugStatus = 3;
            w();
            notifyAll();
        }
    }

    private final synchronized Thread y() {
        Thread thread;
        thread = _thread;
        if (thread == null) {
            thread = new Thread(this, "kotlinx.coroutines.DefaultExecutor");
            _thread = thread;
            thread.setDaemon(true);
            thread.start();
        }
        return thread;
    }

    private final boolean z() {
        int i2 = debugStatus;
        return i2 == 2 || i2 == 3;
    }

    public void run() {
        d2.b.a(this);
        e2 a = f2.a();
        if (a != null) {
            a.b();
        }
        try {
            if (A()) {
                long j2 = Long.MAX_VALUE;
                while (true) {
                    Thread.interrupted();
                    long v = v();
                    if (v == Long.MAX_VALUE) {
                        if (j2 == Long.MAX_VALUE) {
                            e2 a2 = f2.a();
                            long e = a2 != null ? a2.e() : System.nanoTime();
                            if (j2 == Long.MAX_VALUE) {
                                j2 = f1807j + e;
                            }
                            long j3 = j2 - e;
                            if (j3 <= 0) {
                                _thread = null;
                                x();
                                e2 a3 = f2.a();
                                if (a3 != null) {
                                    a3.a();
                                }
                                if (!u()) {
                                    s();
                                    return;
                                }
                                return;
                            }
                            v = f.b(v, j3);
                        } else {
                            v = f.b(v, f1807j);
                        }
                    }
                    if (v > 0) {
                        if (z()) {
                            _thread = null;
                            x();
                            e2 a4 = f2.a();
                            if (a4 != null) {
                                a4.a();
                            }
                            if (!u()) {
                                s();
                                return;
                            }
                            return;
                        }
                        e2 a5 = f2.a();
                        if (a5 != null) {
                            a5.a(this, v);
                        } else {
                            LockSupport.parkNanos(this, v);
                        }
                    }
                }
            }
        } finally {
            _thread = null;
            x();
            e2 a6 = f2.a();
            if (a6 != null) {
                a6.a();
            }
            if (!u()) {
                s();
            }
        }
    }

    /* access modifiers changed from: protected */
    public Thread s() {
        Thread thread = _thread;
        return thread != null ? thread : y();
    }
}
