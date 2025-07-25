package okhttp3;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.internal.connection.j;
import okhttp3.k0.d;

/* compiled from: RealCall */
final class e0 implements j {
    final d0 e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public j f1872f;

    /* renamed from: g  reason: collision with root package name */
    final f0 f1873g;

    /* renamed from: h  reason: collision with root package name */
    final boolean f1874h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1875i;

    /* compiled from: RealCall */
    final class a extends d {

        /* renamed from: f  reason: collision with root package name */
        private final k f1876f;

        /* renamed from: g  reason: collision with root package name */
        private volatile AtomicInteger f1877g = new AtomicInteger(0);

        static {
            Class<e0> cls = e0.class;
        }

        a(k kVar) {
            super("OkHttp %s", e0.this.d());
            this.f1876f = kVar;
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            this.f1877g = aVar.f1877g;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0032 A[Catch:{ IOException -> 0x0052, all -> 0x0028, all -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0057 A[Catch:{ IOException -> 0x0052, all -> 0x0028, all -> 0x0050 }] */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0077 A[Catch:{ IOException -> 0x0052, all -> 0x0028, all -> 0x0050 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r5 = this;
                okhttp3.e0 r0 = okhttp3.e0.this
                okhttp3.internal.connection.j r0 = r0.f1872f
                r0.i()
                r0 = 0
                okhttp3.e0 r1 = okhttp3.e0.this     // Catch:{ IOException -> 0x0052, all -> 0x0028 }
                okhttp3.h0 r0 = r1.b()     // Catch:{ IOException -> 0x0052, all -> 0x0028 }
                r1 = 1
                okhttp3.k r2 = r5.f1876f     // Catch:{ IOException -> 0x0026, all -> 0x0024 }
                okhttp3.e0 r3 = okhttp3.e0.this     // Catch:{ IOException -> 0x0026, all -> 0x0024 }
                r2.a((okhttp3.j) r3, (okhttp3.h0) r0)     // Catch:{ IOException -> 0x0026, all -> 0x0024 }
            L_0x0018:
                okhttp3.e0 r0 = okhttp3.e0.this
                okhttp3.d0 r0 = r0.e
                okhttp3.s r0 = r0.j()
                r0.b(r5)
                goto L_0x007f
            L_0x0024:
                r0 = move-exception
                goto L_0x002b
            L_0x0026:
                r0 = move-exception
                goto L_0x0055
            L_0x0028:
                r1 = move-exception
                r0 = r1
                r1 = 0
            L_0x002b:
                okhttp3.e0 r2 = okhttp3.e0.this     // Catch:{ all -> 0x0050 }
                r2.cancel()     // Catch:{ all -> 0x0050 }
                if (r1 != 0) goto L_0x004f
                java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0050 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
                r2.<init>()     // Catch:{ all -> 0x0050 }
                java.lang.String r3 = "canceled due to "
                r2.append(r3)     // Catch:{ all -> 0x0050 }
                r2.append(r0)     // Catch:{ all -> 0x0050 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0050 }
                r1.<init>(r2)     // Catch:{ all -> 0x0050 }
                okhttp3.k r2 = r5.f1876f     // Catch:{ all -> 0x0050 }
                okhttp3.e0 r3 = okhttp3.e0.this     // Catch:{ all -> 0x0050 }
                r2.a((okhttp3.j) r3, (java.io.IOException) r1)     // Catch:{ all -> 0x0050 }
            L_0x004f:
                throw r0     // Catch:{ all -> 0x0050 }
            L_0x0050:
                r0 = move-exception
                goto L_0x0080
            L_0x0052:
                r1 = move-exception
                r0 = r1
                r1 = 0
            L_0x0055:
                if (r1 == 0) goto L_0x0077
                okhttp3.k0.j.f r1 = okhttp3.k0.j.f.c()     // Catch:{ all -> 0x0050 }
                r2 = 4
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
                r3.<init>()     // Catch:{ all -> 0x0050 }
                java.lang.String r4 = "Callback failure for "
                r3.append(r4)     // Catch:{ all -> 0x0050 }
                okhttp3.e0 r4 = okhttp3.e0.this     // Catch:{ all -> 0x0050 }
                java.lang.String r4 = r4.e()     // Catch:{ all -> 0x0050 }
                r3.append(r4)     // Catch:{ all -> 0x0050 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0050 }
                r1.a((int) r2, (java.lang.String) r3, (java.lang.Throwable) r0)     // Catch:{ all -> 0x0050 }
                goto L_0x0018
            L_0x0077:
                okhttp3.k r1 = r5.f1876f     // Catch:{ all -> 0x0050 }
                okhttp3.e0 r2 = okhttp3.e0.this     // Catch:{ all -> 0x0050 }
                r1.a((okhttp3.j) r2, (java.io.IOException) r0)     // Catch:{ all -> 0x0050 }
                goto L_0x0018
            L_0x007f:
                return
            L_0x0080:
                okhttp3.e0 r1 = okhttp3.e0.this
                okhttp3.d0 r1 = r1.e
                okhttp3.s r1 = r1.j()
                r1.b(r5)
                goto L_0x008d
            L_0x008c:
                throw r0
            L_0x008d:
                goto L_0x008c
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.a.b():void");
        }

        /* access modifiers changed from: package-private */
        public AtomicInteger c() {
            return this.f1877g;
        }

        /* access modifiers changed from: package-private */
        public e0 d() {
            return e0.this;
        }

        /* access modifiers changed from: package-private */
        public String e() {
            return e0.this.f1873g.g().g();
        }

        /* access modifiers changed from: package-private */
        public void a(ExecutorService executorService) {
            try {
                executorService.execute(this);
            } catch (RejectedExecutionException e) {
                InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                interruptedIOException.initCause(e);
                e0.this.f1872f.a((IOException) interruptedIOException);
                this.f1876f.a((j) e0.this, (IOException) interruptedIOException);
                e0.this.e.j().b(this);
            } catch (Throwable th) {
                e0.this.e.j().b(this);
                throw th;
            }
        }
    }

    private e0(d0 d0Var, f0 f0Var, boolean z) {
        this.e = d0Var;
        this.f1873g = f0Var;
        this.f1874h = z;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public okhttp3.h0 b() {
        /*
            r11 = this;
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            okhttp3.d0 r0 = r11.e
            java.util.List r0 = r0.p()
            r1.addAll(r0)
            okhttp3.k0.h.j r0 = new okhttp3.k0.h.j
            okhttp3.d0 r2 = r11.e
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.k0.h.a r0 = new okhttp3.k0.h.a
            okhttp3.d0 r2 = r11.e
            okhttp3.r r2 = r2.i()
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.k0.g.a r0 = new okhttp3.k0.g.a
            okhttp3.d0 r2 = r11.e
            okhttp3.k0.g.d r2 = r2.q()
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.internal.connection.b r0 = new okhttp3.internal.connection.b
            okhttp3.d0 r2 = r11.e
            r0.<init>(r2)
            r1.add(r0)
            boolean r0 = r11.f1874h
            if (r0 != 0) goto L_0x004b
            okhttp3.d0 r0 = r11.e
            java.util.List r0 = r0.r()
            r1.addAll(r0)
        L_0x004b:
            okhttp3.k0.h.b r0 = new okhttp3.k0.h.b
            boolean r2 = r11.f1874h
            r0.<init>(r2)
            r1.add(r0)
            okhttp3.k0.h.g r10 = new okhttp3.k0.h.g
            okhttp3.internal.connection.j r2 = r11.f1872f
            r3 = 0
            r4 = 0
            okhttp3.f0 r5 = r11.f1873g
            okhttp3.d0 r0 = r11.e
            int r7 = r0.f()
            okhttp3.d0 r0 = r11.e
            int r8 = r0.x()
            okhttp3.d0 r0 = r11.e
            int r9 = r0.B()
            r0 = r10
            r6 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r0 = 0
            r1 = 0
            okhttp3.f0 r2 = r11.f1873g     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            okhttp3.h0 r2 = r10.a(r2)     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            okhttp3.internal.connection.j r3 = r11.f1872f     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            boolean r3 = r3.f()     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            if (r3 != 0) goto L_0x008a
            okhttp3.internal.connection.j r0 = r11.f1872f
            r0.a((java.io.IOException) r1)
            return r2
        L_0x008a:
            okhttp3.k0.e.a((java.io.Closeable) r2)     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            java.lang.String r3 = "Canceled"
            r2.<init>(r3)     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
            throw r2     // Catch:{ IOException -> 0x0097, all -> 0x0095 }
        L_0x0095:
            r2 = move-exception
            goto L_0x00a3
        L_0x0097:
            r0 = move-exception
            r2 = 1
            okhttp3.internal.connection.j r3 = r11.f1872f     // Catch:{ all -> 0x00a0 }
            java.io.IOException r0 = r3.a((java.io.IOException) r0)     // Catch:{ all -> 0x00a0 }
            throw r0     // Catch:{ all -> 0x00a0 }
        L_0x00a0:
            r0 = move-exception
            r2 = r0
            r0 = 1
        L_0x00a3:
            if (r0 != 0) goto L_0x00aa
            okhttp3.internal.connection.j r0 = r11.f1872f
            r0.a((java.io.IOException) r1)
        L_0x00aa:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.e0.b():okhttp3.h0");
    }

    public boolean c() {
        return this.f1872f.f();
    }

    public void cancel() {
        this.f1872f.c();
    }

    /* access modifiers changed from: package-private */
    public String d() {
        return this.f1873g.g().m();
    }

    /* access modifiers changed from: package-private */
    public String e() {
        StringBuilder sb = new StringBuilder();
        sb.append(c() ? "canceled " : BuildConfig.FLAVOR);
        sb.append(this.f1874h ? "web socket" : "call");
        sb.append(" to ");
        sb.append(d());
        return sb.toString();
    }

    static e0 a(d0 d0Var, f0 f0Var, boolean z) {
        e0 e0Var = new e0(d0Var, f0Var, z);
        e0Var.f1872f = new j(d0Var, e0Var);
        return e0Var;
    }

    public e0 clone() {
        return a(this.e, this.f1873g, this.f1874h);
    }

    public f0 a() {
        return this.f1873g;
    }

    public void a(k kVar) {
        synchronized (this) {
            if (!this.f1875i) {
                this.f1875i = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        this.f1872f.a();
        this.e.j().a(new a(kVar));
    }
}
