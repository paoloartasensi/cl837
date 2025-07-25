package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* compiled from: AsyncTimeout */
public class a extends s {

    /* renamed from: h  reason: collision with root package name */
    private static final long f2086h = TimeUnit.SECONDS.toMillis(60);

    /* renamed from: i  reason: collision with root package name */
    private static final long f2087i = TimeUnit.MILLISECONDS.toNanos(f2086h);

    /* renamed from: j  reason: collision with root package name */
    static a f2088j;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private a f2089f;

    /* renamed from: g  reason: collision with root package name */
    private long f2090g;

    /* renamed from: okio.a$a  reason: collision with other inner class name */
    /* compiled from: AsyncTimeout */
    class C0097a implements q {
        final /* synthetic */ q e;

        C0097a(q qVar) {
            this.e = qVar;
        }

        public void a(c cVar, long j2) {
            t.a(cVar.f2094f, 0, j2);
            while (true) {
                long j3 = 0;
                if (j2 > 0) {
                    o oVar = cVar.e;
                    while (true) {
                        if (j3 >= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                            break;
                        }
                        j3 += (long) (oVar.c - oVar.b);
                        if (j3 >= j2) {
                            j3 = j2;
                            break;
                        }
                        oVar = oVar.f2109f;
                    }
                    a.this.g();
                    try {
                        this.e.a(cVar, j3);
                        j2 -= j3;
                        a.this.a(true);
                    } catch (IOException e2) {
                        throw a.this.a(e2);
                    } catch (Throwable th) {
                        a.this.a(false);
                        throw th;
                    }
                } else {
                    return;
                }
            }
        }

        public void close() {
            a.this.g();
            try {
                this.e.close();
                a.this.a(true);
            } catch (IOException e2) {
                throw a.this.a(e2);
            } catch (Throwable th) {
                a.this.a(false);
                throw th;
            }
        }

        public s d() {
            return a.this;
        }

        public void flush() {
            a.this.g();
            try {
                this.e.flush();
                a.this.a(true);
            } catch (IOException e2) {
                throw a.this.a(e2);
            } catch (Throwable th) {
                a.this.a(false);
                throw th;
            }
        }

        public String toString() {
            return "AsyncTimeout.sink(" + this.e + ")";
        }
    }

    /* compiled from: AsyncTimeout */
    class b implements r {
        final /* synthetic */ r e;

        b(r rVar) {
            this.e = rVar;
        }

        public long b(c cVar, long j2) {
            a.this.g();
            try {
                long b = this.e.b(cVar, j2);
                a.this.a(true);
                return b;
            } catch (IOException e2) {
                throw a.this.a(e2);
            } catch (Throwable th) {
                a.this.a(false);
                throw th;
            }
        }

        public void close() {
            a.this.g();
            try {
                this.e.close();
                a.this.a(true);
            } catch (IOException e2) {
                throw a.this.a(e2);
            } catch (Throwable th) {
                a.this.a(false);
                throw th;
            }
        }

        public s d() {
            return a.this;
        }

        public String toString() {
            return "AsyncTimeout.source(" + this.e + ")";
        }
    }

    /* compiled from: AsyncTimeout */
    private static final class c extends Thread {
        c() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r1.i();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r3 = this;
            L_0x0000:
                java.lang.Class<okio.a> r0 = okio.a.class
                monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0000 }
                okio.a r1 = okio.a.j()     // Catch:{ all -> 0x0019 }
                if (r1 != 0) goto L_0x000b
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                goto L_0x0000
            L_0x000b:
                okio.a r2 = okio.a.f2088j     // Catch:{ all -> 0x0019 }
                if (r1 != r2) goto L_0x0014
                r1 = 0
                okio.a.f2088j = r1     // Catch:{ all -> 0x0019 }
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                return
            L_0x0014:
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                r1.i()     // Catch:{ InterruptedException -> 0x0000 }
                goto L_0x0000
            L_0x0019:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0019 }
                goto L_0x001d
            L_0x001c:
                throw r1
            L_0x001d:
                goto L_0x001c
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.a.c.run():void");
        }
    }

    private static synchronized void a(a aVar, long j2, boolean z) {
        Class<a> cls = a.class;
        synchronized (cls) {
            if (f2088j == null) {
                f2088j = new a();
                new c().start();
            }
            long nanoTime = System.nanoTime();
            if (j2 != 0 && z) {
                aVar.f2090g = Math.min(j2, aVar.c() - nanoTime) + nanoTime;
            } else if (j2 != 0) {
                aVar.f2090g = j2 + nanoTime;
            } else if (z) {
                aVar.f2090g = aVar.c();
            } else {
                throw new AssertionError();
            }
            long b2 = aVar.b(nanoTime);
            a aVar2 = f2088j;
            while (true) {
                if (aVar2.f2089f == null) {
                    break;
                } else if (b2 < aVar2.f2089f.b(nanoTime)) {
                    break;
                } else {
                    aVar2 = aVar2.f2089f;
                }
            }
            aVar.f2089f = aVar2.f2089f;
            aVar2.f2089f = aVar;
            if (aVar2 == f2088j) {
                cls.notify();
            }
        }
    }

    private long b(long j2) {
        return this.f2090g - j2;
    }

    static a j() {
        Class<a> cls = a.class;
        a aVar = f2088j.f2089f;
        if (aVar == null) {
            long nanoTime = System.nanoTime();
            cls.wait(f2086h);
            if (f2088j.f2089f != null || System.nanoTime() - nanoTime < f2087i) {
                return null;
            }
            return f2088j;
        }
        long b2 = aVar.b(System.nanoTime());
        if (b2 > 0) {
            long j2 = b2 / 1000000;
            cls.wait(j2, (int) (b2 - (1000000 * j2)));
            return null;
        }
        f2088j.f2089f = aVar.f2089f;
        aVar.f2089f = null;
        return aVar;
    }

    public final void g() {
        if (!this.e) {
            long f2 = f();
            boolean d = d();
            if (f2 != 0 || d) {
                this.e = true;
                a(this, f2, d);
                return;
            }
            return;
        }
        throw new IllegalStateException("Unbalanced enter/exit");
    }

    public final boolean h() {
        if (!this.e) {
            return false;
        }
        this.e = false;
        return a(this);
    }

    /* access modifiers changed from: protected */
    public void i() {
    }

    /* access modifiers changed from: protected */
    public IOException b(IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    private static synchronized boolean a(a aVar) {
        synchronized (a.class) {
            for (a aVar2 = f2088j; aVar2 != null; aVar2 = aVar2.f2089f) {
                if (aVar2.f2089f == aVar) {
                    aVar2.f2089f = aVar.f2089f;
                    aVar.f2089f = null;
                    return false;
                }
            }
            return true;
        }
    }

    public final q a(q qVar) {
        return new C0097a(qVar);
    }

    public final r a(r rVar) {
        return new b(rVar);
    }

    /* access modifiers changed from: package-private */
    public final void a(boolean z) {
        if (h() && z) {
            throw b((IOException) null);
        }
    }

    /* access modifiers changed from: package-private */
    public final IOException a(IOException iOException) {
        if (!h()) {
            return iOException;
        }
        return b(iOException);
    }
}
