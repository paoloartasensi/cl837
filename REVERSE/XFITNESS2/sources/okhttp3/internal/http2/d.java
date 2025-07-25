package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.http2.f;
import okio.ByteString;

/* compiled from: Http2Connection */
public final class d implements Closeable {
    /* access modifiers changed from: private */
    public static final ExecutorService C = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), okhttp3.k0.e.a("OkHttp Http2Connection", true));
    final l A;
    final Set<Integer> B = new LinkedHashSet();
    final boolean e;

    /* renamed from: f  reason: collision with root package name */
    final j f1946f;

    /* renamed from: g  reason: collision with root package name */
    final Map<Integer, g> f1947g = new LinkedHashMap();

    /* renamed from: h  reason: collision with root package name */
    final String f1948h;

    /* renamed from: i  reason: collision with root package name */
    int f1949i;

    /* renamed from: j  reason: collision with root package name */
    int f1950j;
    /* access modifiers changed from: private */
    public boolean k;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService l;
    private final ExecutorService m;
    final j n;
    /* access modifiers changed from: private */
    public long o = 0;
    /* access modifiers changed from: private */
    public long p = 0;
    private long q = 0;
    private long r = 0;
    private long s = 0;
    private long t = 0;
    long u = 0;
    long v;
    k w = new k();
    final k x = new k();
    final Socket y;
    final h z;

    /* compiled from: Http2Connection */
    class a extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1951f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ ErrorCode f1952g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(String str, Object[] objArr, int i2, ErrorCode errorCode) {
            super(str, objArr);
            this.f1951f = i2;
            this.f1952g = errorCode;
        }

        public void b() {
            try {
                d.this.b(this.f1951f, this.f1952g);
            } catch (IOException e) {
                d.this.a(e);
            }
        }
    }

    /* compiled from: Http2Connection */
    class b extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1954f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ long f1955g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str, Object[] objArr, int i2, long j2) {
            super(str, objArr);
            this.f1954f = i2;
            this.f1955g = j2;
        }

        public void b() {
            try {
                d.this.z.a(this.f1954f, this.f1955g);
            } catch (IOException e) {
                d.this.a(e);
            }
        }
    }

    /* compiled from: Http2Connection */
    class c extends okhttp3.k0.d {
        c(String str, Object... objArr) {
            super(str, objArr);
        }

        public void b() {
            d.this.a(false, 2, 0);
        }
    }

    /* renamed from: okhttp3.internal.http2.d$d  reason: collision with other inner class name */
    /* compiled from: Http2Connection */
    class C0093d extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1958f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ List f1959g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0093d(String str, Object[] objArr, int i2, List list) {
            super(str, objArr);
            this.f1958f = i2;
            this.f1959g = list;
        }

        public void b() {
            if (d.this.n.a(this.f1958f, (List<a>) this.f1959g)) {
                try {
                    d.this.z.a(this.f1958f, ErrorCode.CANCEL);
                    synchronized (d.this) {
                        d.this.B.remove(Integer.valueOf(this.f1958f));
                    }
                } catch (IOException unused) {
                }
            }
        }
    }

    /* compiled from: Http2Connection */
    class e extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1961f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ List f1962g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ boolean f1963h;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(String str, Object[] objArr, int i2, List list, boolean z) {
            super(str, objArr);
            this.f1961f = i2;
            this.f1962g = list;
            this.f1963h = z;
        }

        public void b() {
            boolean a = d.this.n.a(this.f1961f, this.f1962g, this.f1963h);
            if (a) {
                try {
                    d.this.z.a(this.f1961f, ErrorCode.CANCEL);
                } catch (IOException unused) {
                    return;
                }
            }
            if (a || this.f1963h) {
                synchronized (d.this) {
                    d.this.B.remove(Integer.valueOf(this.f1961f));
                }
            }
        }
    }

    /* compiled from: Http2Connection */
    class f extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1965f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ okio.c f1966g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ int f1967h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ boolean f1968i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(String str, Object[] objArr, int i2, okio.c cVar, int i3, boolean z) {
            super(str, objArr);
            this.f1965f = i2;
            this.f1966g = cVar;
            this.f1967h = i3;
            this.f1968i = z;
        }

        public void b() {
            try {
                boolean a = d.this.n.a(this.f1965f, this.f1966g, this.f1967h, this.f1968i);
                if (a) {
                    d.this.z.a(this.f1965f, ErrorCode.CANCEL);
                }
                if (a || this.f1968i) {
                    synchronized (d.this) {
                        d.this.B.remove(Integer.valueOf(this.f1965f));
                    }
                }
            } catch (IOException unused) {
            }
        }
    }

    /* compiled from: Http2Connection */
    class g extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1970f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ ErrorCode f1971g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(String str, Object[] objArr, int i2, ErrorCode errorCode) {
            super(str, objArr);
            this.f1970f = i2;
            this.f1971g = errorCode;
        }

        public void b() {
            d.this.n.a(this.f1970f, this.f1971g);
            synchronized (d.this) {
                d.this.B.remove(Integer.valueOf(this.f1970f));
            }
        }
    }

    /* compiled from: Http2Connection */
    final class i extends okhttp3.k0.d {
        i() {
            super("OkHttp %s ping", d.this.f1948h);
        }

        public void b() {
            boolean z;
            synchronized (d.this) {
                if (d.this.p < d.this.o) {
                    z = true;
                } else {
                    d.d(d.this);
                    z = false;
                }
            }
            if (z) {
                d.this.a((IOException) null);
            } else {
                d.this.a(false, 1, 0);
            }
        }
    }

    /* compiled from: Http2Connection */
    public static abstract class j {
        public static final j a = new a();

        /* compiled from: Http2Connection */
        class a extends j {
            a() {
            }

            public void a(g gVar) {
                gVar.a(ErrorCode.REFUSED_STREAM, (IOException) null);
            }
        }

        public void a(d dVar) {
        }

        public abstract void a(g gVar);
    }

    /* compiled from: Http2Connection */
    final class k extends okhttp3.k0.d {

        /* renamed from: f  reason: collision with root package name */
        final boolean f1977f;

        /* renamed from: g  reason: collision with root package name */
        final int f1978g;

        /* renamed from: h  reason: collision with root package name */
        final int f1979h;

        k(boolean z, int i2, int i3) {
            super("OkHttp %s ping %08x%08x", d.this.f1948h, Integer.valueOf(i2), Integer.valueOf(i3));
            this.f1977f = z;
            this.f1978g = i2;
            this.f1979h = i3;
        }

        public void b() {
            d.this.a(this.f1977f, this.f1978g, this.f1979h);
        }
    }

    static {
        Class<d> cls = d.class;
    }

    d(h hVar) {
        h hVar2 = hVar;
        this.n = hVar2.f1973f;
        boolean z2 = hVar2.f1974g;
        this.e = z2;
        this.f1946f = hVar2.e;
        int i2 = z2 ? 1 : 2;
        this.f1950j = i2;
        if (hVar2.f1974g) {
            this.f1950j = i2 + 2;
        }
        if (hVar2.f1974g) {
            this.w.a(7, 16777216);
        }
        this.f1948h = hVar2.b;
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, okhttp3.k0.e.a(okhttp3.k0.e.a("OkHttp %s Writer", this.f1948h), false));
        this.l = scheduledThreadPoolExecutor;
        if (hVar2.f1975h != 0) {
            i iVar = new i();
            int i3 = hVar2.f1975h;
            scheduledThreadPoolExecutor.scheduleAtFixedRate(iVar, (long) i3, (long) i3, TimeUnit.MILLISECONDS);
        }
        this.m = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), okhttp3.k0.e.a(okhttp3.k0.e.a("OkHttp %s Push Observer", this.f1948h), true));
        this.x.a(7, 65535);
        this.x.a(5, 16384);
        this.v = (long) this.x.c();
        this.y = hVar2.a;
        this.z = new h(hVar2.d, this.e);
        this.A = new l(new f(hVar2.c, this.e));
    }

    static /* synthetic */ long b(d dVar) {
        long j2 = dVar.p;
        dVar.p = 1 + j2;
        return j2;
    }

    static /* synthetic */ long d(d dVar) {
        long j2 = dVar.o;
        dVar.o = 1 + j2;
        return j2;
    }

    static /* synthetic */ long g(d dVar) {
        long j2 = dVar.r;
        dVar.r = 1 + j2;
        return j2;
    }

    static /* synthetic */ long h(d dVar) {
        long j2 = dVar.s;
        dVar.s = 1 + j2;
        return j2;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2) {
        return i2 != 0 && (i2 & 1) == 0;
    }

    public void close() {
        a(ErrorCode.NO_ERROR, ErrorCode.CANCEL, (IOException) null);
    }

    public void flush() {
        this.z.flush();
    }

    /* access modifiers changed from: package-private */
    public synchronized void i(long j2) {
        long j3 = this.u + j2;
        this.u = j3;
        if (j3 >= ((long) (this.w.c() / 2))) {
            a(0, this.u);
            this.u = 0;
        }
    }

    public void j() {
        a(true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.http2.g b(int r11, java.util.List<okhttp3.internal.http2.a> r12, boolean r13) {
        /*
            r10 = this;
            r6 = r13 ^ 1
            r4 = 0
            okhttp3.internal.http2.h r7 = r10.z
            monitor-enter(r7)
            monitor-enter(r10)     // Catch:{ all -> 0x0078 }
            int r0 = r10.f1950j     // Catch:{ all -> 0x0075 }
            r1 = 1073741823(0x3fffffff, float:1.9999999)
            if (r0 <= r1) goto L_0x0013
            okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.REFUSED_STREAM     // Catch:{ all -> 0x0075 }
            r10.a((okhttp3.internal.http2.ErrorCode) r0)     // Catch:{ all -> 0x0075 }
        L_0x0013:
            boolean r0 = r10.k     // Catch:{ all -> 0x0075 }
            if (r0 != 0) goto L_0x006f
            int r8 = r10.f1950j     // Catch:{ all -> 0x0075 }
            int r0 = r10.f1950j     // Catch:{ all -> 0x0075 }
            int r0 = r0 + 2
            r10.f1950j = r0     // Catch:{ all -> 0x0075 }
            okhttp3.internal.http2.g r9 = new okhttp3.internal.http2.g     // Catch:{ all -> 0x0075 }
            r5 = 0
            r0 = r9
            r1 = r8
            r2 = r10
            r3 = r6
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ all -> 0x0075 }
            if (r13 == 0) goto L_0x003c
            long r0 = r10.v     // Catch:{ all -> 0x0075 }
            r2 = 0
            int r13 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r13 == 0) goto L_0x003c
            long r0 = r9.b     // Catch:{ all -> 0x0075 }
            int r13 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r13 != 0) goto L_0x003a
            goto L_0x003c
        L_0x003a:
            r13 = 0
            goto L_0x003d
        L_0x003c:
            r13 = 1
        L_0x003d:
            boolean r0 = r9.g()     // Catch:{ all -> 0x0075 }
            if (r0 == 0) goto L_0x004c
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r0 = r10.f1947g     // Catch:{ all -> 0x0075 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x0075 }
            r0.put(r1, r9)     // Catch:{ all -> 0x0075 }
        L_0x004c:
            monitor-exit(r10)     // Catch:{ all -> 0x0075 }
            if (r11 != 0) goto L_0x0055
            okhttp3.internal.http2.h r11 = r10.z     // Catch:{ all -> 0x0078 }
            r11.a((boolean) r6, (int) r8, (java.util.List<okhttp3.internal.http2.a>) r12)     // Catch:{ all -> 0x0078 }
            goto L_0x005e
        L_0x0055:
            boolean r0 = r10.e     // Catch:{ all -> 0x0078 }
            if (r0 != 0) goto L_0x0067
            okhttp3.internal.http2.h r0 = r10.z     // Catch:{ all -> 0x0078 }
            r0.a((int) r11, (int) r8, (java.util.List<okhttp3.internal.http2.a>) r12)     // Catch:{ all -> 0x0078 }
        L_0x005e:
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            if (r13 == 0) goto L_0x0066
            okhttp3.internal.http2.h r11 = r10.z
            r11.flush()
        L_0x0066:
            return r9
        L_0x0067:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0078 }
            java.lang.String r12 = "client streams shouldn't have associated stream IDs"
            r11.<init>(r12)     // Catch:{ all -> 0x0078 }
            throw r11     // Catch:{ all -> 0x0078 }
        L_0x006f:
            okhttp3.internal.http2.ConnectionShutdownException r11 = new okhttp3.internal.http2.ConnectionShutdownException     // Catch:{ all -> 0x0075 }
            r11.<init>()     // Catch:{ all -> 0x0075 }
            throw r11     // Catch:{ all -> 0x0075 }
        L_0x0075:
            r11 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0075 }
            throw r11     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r11 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0078 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.d.b(int, java.util.List, boolean):okhttp3.internal.http2.g");
    }

    /* access modifiers changed from: package-private */
    public synchronized g c(int i2) {
        g remove;
        remove = this.f1947g.remove(Integer.valueOf(i2));
        notifyAll();
        return remove;
    }

    public synchronized boolean h(long j2) {
        if (this.k) {
            return false;
        }
        if (this.r >= this.q || j2 < this.t) {
            return true;
        }
        return false;
    }

    /* compiled from: Http2Connection */
    public static class h {
        Socket a;
        String b;
        okio.e c;
        okio.d d;
        j e = j.a;

        /* renamed from: f  reason: collision with root package name */
        j f1973f = j.a;

        /* renamed from: g  reason: collision with root package name */
        boolean f1974g;

        /* renamed from: h  reason: collision with root package name */
        int f1975h;

        public h(boolean z) {
            this.f1974g = z;
        }

        public h a(Socket socket, String str, okio.e eVar, okio.d dVar) {
            this.a = socket;
            this.b = str;
            this.c = eVar;
            this.d = dVar;
            return this;
        }

        public h a(j jVar) {
            this.e = jVar;
            return this;
        }

        public h a(int i2) {
            this.f1975h = i2;
            return this;
        }

        public d a() {
            return new d(this);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized g a(int i2) {
        return this.f1947g.get(Integer.valueOf(i2));
    }

    public synchronized int a() {
        return this.x.b(Integer.MAX_VALUE);
    }

    /* access modifiers changed from: package-private */
    public void c(int i2, ErrorCode errorCode) {
        try {
            this.l.execute(new a("OkHttp %s stream %d", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, errorCode));
        } catch (RejectedExecutionException unused) {
        }
    }

    public g a(List<a> list, boolean z2) {
        return b(0, list, z2);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        synchronized (this) {
            if (this.r >= this.q) {
                this.q++;
                this.t = System.nanoTime() + 1000000000;
                try {
                    this.l.execute(new c("OkHttp %s ping", this.f1948h));
                } catch (RejectedExecutionException unused) {
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z2, List<a> list) {
        this.z.a(z2, i2, list);
    }

    /* compiled from: Http2Connection */
    class l extends okhttp3.k0.d implements f.b {

        /* renamed from: f  reason: collision with root package name */
        final f f1981f;

        /* compiled from: Http2Connection */
        class a extends okhttp3.k0.d {

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ g f1983f;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, Object[] objArr, g gVar) {
                super(str, objArr);
                this.f1983f = gVar;
            }

            public void b() {
                try {
                    d.this.f1946f.a(this.f1983f);
                } catch (IOException e) {
                    okhttp3.k0.j.f c = okhttp3.k0.j.f.c();
                    c.a(4, "Http2Connection.Listener failure for " + d.this.f1948h, (Throwable) e);
                    try {
                        this.f1983f.a(ErrorCode.PROTOCOL_ERROR, e);
                    } catch (IOException unused) {
                    }
                }
            }
        }

        /* compiled from: Http2Connection */
        class b extends okhttp3.k0.d {

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ boolean f1985f;

            /* renamed from: g  reason: collision with root package name */
            final /* synthetic */ k f1986g;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(String str, Object[] objArr, boolean z, k kVar) {
                super(str, objArr);
                this.f1985f = z;
                this.f1986g = kVar;
            }

            public void b() {
                l.this.b(this.f1985f, this.f1986g);
            }
        }

        /* compiled from: Http2Connection */
        class c extends okhttp3.k0.d {
            c(String str, Object... objArr) {
                super(str, objArr);
            }

            public void b() {
                d dVar = d.this;
                dVar.f1946f.a(dVar);
            }
        }

        l(f fVar) {
            super("OkHttp %s", d.this.f1948h);
            this.f1981f = fVar;
        }

        public void a() {
        }

        public void a(int i2, int i3, int i4, boolean z) {
        }

        public void a(boolean z, int i2, okio.e eVar, int i3) {
            if (d.this.b(i2)) {
                d.this.a(i2, eVar, i3, z);
                return;
            }
            g a2 = d.this.a(i2);
            if (a2 == null) {
                d.this.c(i2, ErrorCode.PROTOCOL_ERROR);
                long j2 = (long) i3;
                d.this.i(j2);
                eVar.skip(j2);
                return;
            }
            a2.a(eVar, i3);
            if (z) {
                a2.a(okhttp3.k0.e.c, true);
            }
        }

        /* access modifiers changed from: protected */
        public void b() {
            ErrorCode errorCode;
            ErrorCode errorCode2;
            ErrorCode errorCode3 = ErrorCode.INTERNAL_ERROR;
            e = null;
            try {
                this.f1981f.a((f.b) this);
                while (this.f1981f.a(false, (f.b) this)) {
                }
                errorCode = ErrorCode.NO_ERROR;
                try {
                    errorCode2 = ErrorCode.CANCEL;
                } catch (IOException e) {
                    e = e;
                }
            } catch (IOException e2) {
                e = e2;
                errorCode = errorCode3;
                try {
                    errorCode = ErrorCode.PROTOCOL_ERROR;
                    errorCode2 = ErrorCode.PROTOCOL_ERROR;
                    d.this.a(errorCode, errorCode2, e);
                    okhttp3.k0.e.a((Closeable) this.f1981f);
                } catch (Throwable th) {
                    th = th;
                    d.this.a(errorCode, errorCode3, e);
                    okhttp3.k0.e.a((Closeable) this.f1981f);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                errorCode = errorCode3;
                d.this.a(errorCode, errorCode3, e);
                okhttp3.k0.e.a((Closeable) this.f1981f);
                throw th;
            }
            d.this.a(errorCode, errorCode2, e);
            okhttp3.k0.e.a((Closeable) this.f1981f);
        }

        public void a(boolean z, int i2, int i3, List<a> list) {
            if (d.this.b(i2)) {
                d.this.a(i2, list, z);
                return;
            }
            synchronized (d.this) {
                g a2 = d.this.a(i2);
                if (a2 != null) {
                    a2.a(okhttp3.k0.e.b(list), z);
                } else if (!d.this.k) {
                    if (i2 > d.this.f1949i) {
                        if (i2 % 2 != d.this.f1950j % 2) {
                            int i4 = i2;
                            g gVar = new g(i4, d.this, false, z, okhttp3.k0.e.b(list));
                            d.this.f1949i = i2;
                            d.this.f1947g.put(Integer.valueOf(i2), gVar);
                            d.C.execute(new a("OkHttp %s stream %d", new Object[]{d.this.f1948h, Integer.valueOf(i2)}, gVar));
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(boolean z, k kVar) {
            g[] gVarArr;
            long j2;
            synchronized (d.this.z) {
                synchronized (d.this) {
                    int c2 = d.this.x.c();
                    if (z) {
                        d.this.x.a();
                    }
                    d.this.x.a(kVar);
                    int c3 = d.this.x.c();
                    gVarArr = null;
                    if (c3 == -1 || c3 == c2) {
                        j2 = 0;
                    } else {
                        j2 = (long) (c3 - c2);
                        if (!d.this.f1947g.isEmpty()) {
                            gVarArr = (g[]) d.this.f1947g.values().toArray(new g[d.this.f1947g.size()]);
                        }
                    }
                }
                try {
                    d.this.z.a(d.this.x);
                } catch (IOException e) {
                    d.this.a(e);
                }
            }
            if (gVarArr != null) {
                for (g gVar : gVarArr) {
                    synchronized (gVar) {
                        gVar.a(j2);
                    }
                }
            }
            d.C.execute(new c("OkHttp %s settings", d.this.f1948h));
        }

        public void a(int i2, ErrorCode errorCode) {
            if (d.this.b(i2)) {
                d.this.a(i2, errorCode);
                return;
            }
            g c2 = d.this.c(i2);
            if (c2 != null) {
                c2.b(errorCode);
            }
        }

        public void a(boolean z, k kVar) {
            try {
                d.this.l.execute(new b("OkHttp %s ACK Settings", new Object[]{d.this.f1948h}, z, kVar));
            } catch (RejectedExecutionException unused) {
            }
        }

        public void a(boolean z, int i2, int i3) {
            if (z) {
                synchronized (d.this) {
                    if (i2 == 1) {
                        try {
                            d.b(d.this);
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else if (i2 == 2) {
                        d.g(d.this);
                    } else if (i2 == 3) {
                        d.h(d.this);
                        d.this.notifyAll();
                    }
                }
                return;
            }
            try {
                d.this.l.execute(new k(true, i2, i3));
            } catch (RejectedExecutionException unused) {
            }
        }

        public void a(int i2, ErrorCode errorCode, ByteString byteString) {
            g[] gVarArr;
            byteString.size();
            synchronized (d.this) {
                gVarArr = (g[]) d.this.f1947g.values().toArray(new g[d.this.f1947g.size()]);
                boolean unused = d.this.k = true;
            }
            for (g gVar : gVarArr) {
                if (gVar.c() > i2 && gVar.f()) {
                    gVar.b(ErrorCode.REFUSED_STREAM);
                    d.this.c(gVar.c());
                }
            }
        }

        public void a(int i2, long j2) {
            if (i2 == 0) {
                synchronized (d.this) {
                    d.this.v += j2;
                    d.this.notifyAll();
                }
                return;
            }
            g a2 = d.this.a(i2);
            if (a2 != null) {
                synchronized (a2) {
                    a2.a(j2);
                }
            }
        }

        public void a(int i2, int i3, List<a> list) {
            d.this.a(i3, list);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:26|27|28) */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r3 = java.lang.Math.min((int) java.lang.Math.min(r12, r8.v), r8.z.c());
        r6 = (long) r3;
        r8.v -= r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x005a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r9, boolean r10, okio.c r11, long r12) {
        /*
            r8 = this;
            r0 = 0
            r1 = 0
            int r3 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r3 != 0) goto L_0x000d
            okhttp3.internal.http2.h r12 = r8.z
            r12.a((boolean) r10, (int) r9, (okio.c) r11, (int) r0)
            return
        L_0x000d:
            int r3 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x0069
            monitor-enter(r8)
        L_0x0012:
            long r3 = r8.v     // Catch:{ InterruptedException -> 0x005a }
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 > 0) goto L_0x0030
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r3 = r8.f1947g     // Catch:{ InterruptedException -> 0x005a }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch:{ InterruptedException -> 0x005a }
            boolean r3 = r3.containsKey(r4)     // Catch:{ InterruptedException -> 0x005a }
            if (r3 == 0) goto L_0x0028
            r8.wait()     // Catch:{ InterruptedException -> 0x005a }
            goto L_0x0012
        L_0x0028:
            java.io.IOException r9 = new java.io.IOException     // Catch:{ InterruptedException -> 0x005a }
            java.lang.String r10 = "stream closed"
            r9.<init>(r10)     // Catch:{ InterruptedException -> 0x005a }
            throw r9     // Catch:{ InterruptedException -> 0x005a }
        L_0x0030:
            long r3 = r8.v     // Catch:{ all -> 0x0058 }
            long r3 = java.lang.Math.min(r12, r3)     // Catch:{ all -> 0x0058 }
            int r4 = (int) r3     // Catch:{ all -> 0x0058 }
            okhttp3.internal.http2.h r3 = r8.z     // Catch:{ all -> 0x0058 }
            int r3 = r3.c()     // Catch:{ all -> 0x0058 }
            int r3 = java.lang.Math.min(r4, r3)     // Catch:{ all -> 0x0058 }
            long r4 = r8.v     // Catch:{ all -> 0x0058 }
            long r6 = (long) r3     // Catch:{ all -> 0x0058 }
            long r4 = r4 - r6
            r8.v = r4     // Catch:{ all -> 0x0058 }
            monitor-exit(r8)     // Catch:{ all -> 0x0058 }
            long r12 = r12 - r6
            okhttp3.internal.http2.h r4 = r8.z
            if (r10 == 0) goto L_0x0053
            int r5 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r5 != 0) goto L_0x0053
            r5 = 1
            goto L_0x0054
        L_0x0053:
            r5 = 0
        L_0x0054:
            r4.a((boolean) r5, (int) r9, (okio.c) r11, (int) r3)
            goto L_0x000d
        L_0x0058:
            r9 = move-exception
            goto L_0x0067
        L_0x005a:
            java.lang.Thread r9 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0058 }
            r9.interrupt()     // Catch:{ all -> 0x0058 }
            java.io.InterruptedIOException r9 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0058 }
            r9.<init>()     // Catch:{ all -> 0x0058 }
            throw r9     // Catch:{ all -> 0x0058 }
        L_0x0067:
            monitor-exit(r8)     // Catch:{ all -> 0x0058 }
            throw r9
        L_0x0069:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.d.a(int, boolean, okio.c, long):void");
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, long j2) {
        try {
            this.l.execute(new b("OkHttp Window Update %s stream %d", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, j2));
        } catch (RejectedExecutionException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2, ErrorCode errorCode) {
        this.z.a(i2, errorCode);
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2, int i2, int i3) {
        try {
            this.z.a(z2, i2, i3);
        } catch (IOException e2) {
            a(e2);
        }
    }

    public void a(ErrorCode errorCode) {
        synchronized (this.z) {
            synchronized (this) {
                if (!this.k) {
                    this.k = true;
                    int i2 = this.f1949i;
                    this.z.a(i2, errorCode, okhttp3.k0.e.a);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x003a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(okhttp3.internal.http2.ErrorCode r4, okhttp3.internal.http2.ErrorCode r5, java.io.IOException r6) {
        /*
            r3 = this;
            r3.a((okhttp3.internal.http2.ErrorCode) r4)     // Catch:{ IOException -> 0x0003 }
        L_0x0003:
            r4 = 0
            monitor-enter(r3)
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r0 = r3.f1947g     // Catch:{ all -> 0x004a }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x0026
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r4 = r3.f1947g     // Catch:{ all -> 0x004a }
            java.util.Collection r4 = r4.values()     // Catch:{ all -> 0x004a }
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r0 = r3.f1947g     // Catch:{ all -> 0x004a }
            int r0 = r0.size()     // Catch:{ all -> 0x004a }
            okhttp3.internal.http2.g[] r0 = new okhttp3.internal.http2.g[r0]     // Catch:{ all -> 0x004a }
            java.lang.Object[] r4 = r4.toArray(r0)     // Catch:{ all -> 0x004a }
            okhttp3.internal.http2.g[] r4 = (okhttp3.internal.http2.g[]) r4     // Catch:{ all -> 0x004a }
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.g> r0 = r3.f1947g     // Catch:{ all -> 0x004a }
            r0.clear()     // Catch:{ all -> 0x004a }
        L_0x0026:
            monitor-exit(r3)     // Catch:{ all -> 0x004a }
            if (r4 == 0) goto L_0x0035
            int r0 = r4.length
            r1 = 0
        L_0x002b:
            if (r1 >= r0) goto L_0x0035
            r2 = r4[r1]
            r2.a((okhttp3.internal.http2.ErrorCode) r5, (java.io.IOException) r6)     // Catch:{ IOException -> 0x0032 }
        L_0x0032:
            int r1 = r1 + 1
            goto L_0x002b
        L_0x0035:
            okhttp3.internal.http2.h r4 = r3.z     // Catch:{ IOException -> 0x003a }
            r4.close()     // Catch:{ IOException -> 0x003a }
        L_0x003a:
            java.net.Socket r4 = r3.y     // Catch:{ IOException -> 0x003f }
            r4.close()     // Catch:{ IOException -> 0x003f }
        L_0x003f:
            java.util.concurrent.ScheduledExecutorService r4 = r3.l
            r4.shutdown()
            java.util.concurrent.ExecutorService r4 = r3.m
            r4.shutdown()
            return
        L_0x004a:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004a }
            goto L_0x004e
        L_0x004d:
            throw r4
        L_0x004e:
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.d.a(okhttp3.internal.http2.ErrorCode, okhttp3.internal.http2.ErrorCode, java.io.IOException):void");
    }

    /* access modifiers changed from: private */
    public void a(IOException iOException) {
        ErrorCode errorCode = ErrorCode.PROTOCOL_ERROR;
        a(errorCode, errorCode, iOException);
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        if (z2) {
            this.z.a();
            this.z.b(this.w);
            int c2 = this.w.c();
            if (c2 != 65535) {
                this.z.a(0, (long) (c2 - 65535));
            }
        }
        new Thread(this.A).start();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, List<a> list) {
        synchronized (this) {
            if (this.B.contains(Integer.valueOf(i2))) {
                c(i2, ErrorCode.PROTOCOL_ERROR);
                return;
            }
            this.B.add(Integer.valueOf(i2));
            try {
                a((okhttp3.k0.d) new C0093d("OkHttp %s Push Request[%s]", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, list));
            } catch (RejectedExecutionException unused) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, List<a> list, boolean z2) {
        try {
            a((okhttp3.k0.d) new e("OkHttp %s Push Headers[%s]", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, list, z2));
        } catch (RejectedExecutionException unused) {
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, okio.e eVar, int i3, boolean z2) {
        okio.c cVar = new okio.c();
        long j2 = (long) i3;
        eVar.e(j2);
        eVar.b(cVar, j2);
        if (cVar.r() == j2) {
            a((okhttp3.k0.d) new f("OkHttp %s Push Data[%s]", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, cVar, i3, z2));
            return;
        }
        throw new IOException(cVar.r() + " != " + i3);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, ErrorCode errorCode) {
        a((okhttp3.k0.d) new g("OkHttp %s Push Reset[%s]", new Object[]{this.f1948h, Integer.valueOf(i2)}, i2, errorCode));
    }

    private synchronized void a(okhttp3.k0.d dVar) {
        if (!this.k) {
            this.m.execute(dVar);
        }
    }
}
