package okhttp3.internal.http2;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Deque;
import okhttp3.y;
import okio.e;
import okio.q;
import okio.r;
import okio.s;

/* compiled from: Http2Stream */
public final class g {
    long a = 0;
    long b;
    final int c;
    final d d;
    private final Deque<y> e = new ArrayDeque();

    /* renamed from: f  reason: collision with root package name */
    private boolean f2001f;

    /* renamed from: g  reason: collision with root package name */
    private final b f2002g;

    /* renamed from: h  reason: collision with root package name */
    final a f2003h;

    /* renamed from: i  reason: collision with root package name */
    final c f2004i = new c();

    /* renamed from: j  reason: collision with root package name */
    final c f2005j = new c();
    ErrorCode k;
    IOException l;

    /* compiled from: Http2Stream */
    private final class b implements r {
        private final okio.c e = new okio.c();

        /* renamed from: f  reason: collision with root package name */
        private final okio.c f2010f = new okio.c();

        /* renamed from: g  reason: collision with root package name */
        private final long f2011g;
        /* access modifiers changed from: private */

        /* renamed from: h  reason: collision with root package name */
        public y f2012h;

        /* renamed from: i  reason: collision with root package name */
        boolean f2013i;

        /* renamed from: j  reason: collision with root package name */
        boolean f2014j;

        b(long j2) {
            this.f2011g = j2;
        }

        private void h(long j2) {
            g.this.d.i(j2);
        }

        public long b(okio.c cVar, long j2) {
            Throwable th;
            long b;
            if (j2 >= 0) {
                while (true) {
                    th = null;
                    synchronized (g.this) {
                        g.this.f2004i.g();
                        try {
                            if (g.this.k != null) {
                                if (g.this.l != null) {
                                    th = g.this.l;
                                } else {
                                    th = new StreamResetException(g.this.k);
                                }
                            }
                            if (this.f2013i) {
                                throw new IOException("stream closed");
                            } else if (this.f2010f.r() > 0) {
                                b = this.f2010f.b(cVar, Math.min(j2, this.f2010f.r()));
                                g.this.a += b;
                                if (th == null && g.this.a >= ((long) (g.this.d.w.c() / 2))) {
                                    g.this.d.a(g.this.c, g.this.a);
                                    g.this.a = 0;
                                }
                            } else if (this.f2014j || th != null) {
                                b = -1;
                            } else {
                                g.this.j();
                            }
                        } finally {
                            g.this.f2004i.k();
                        }
                    }
                }
                b = -1;
                g.this.f2004i.k();
                if (b != -1) {
                    h(b);
                    return b;
                } else if (th == null) {
                    return -1;
                } else {
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            }
        }

        public void close() {
            long r;
            synchronized (g.this) {
                this.f2013i = true;
                r = this.f2010f.r();
                this.f2010f.j();
                g.this.notifyAll();
            }
            if (r > 0) {
                h(r);
            }
            g.this.a();
        }

        public s d() {
            return g.this.f2004i;
        }

        /* access modifiers changed from: package-private */
        public void a(e eVar, long j2) {
            boolean z;
            boolean z2;
            boolean z3;
            long j3;
            while (j2 > 0) {
                synchronized (g.this) {
                    z = this.f2014j;
                    z2 = true;
                    z3 = this.f2010f.r() + j2 > this.f2011g;
                }
                if (z3) {
                    eVar.skip(j2);
                    g.this.a(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                } else if (z) {
                    eVar.skip(j2);
                    return;
                } else {
                    long b = eVar.b(this.e, j2);
                    if (b != -1) {
                        j2 -= b;
                        synchronized (g.this) {
                            if (this.f2013i) {
                                j3 = this.e.r();
                                this.e.j();
                            } else {
                                if (this.f2010f.r() != 0) {
                                    z2 = false;
                                }
                                this.f2010f.a((r) this.e);
                                if (z2) {
                                    g.this.notifyAll();
                                }
                                j3 = 0;
                            }
                        }
                        if (j3 > 0) {
                            h(j3);
                        }
                    } else {
                        throw new EOFException();
                    }
                }
            }
        }
    }

    /* compiled from: Http2Stream */
    class c extends okio.a {
        c() {
        }

        /* access modifiers changed from: protected */
        public IOException b(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        /* access modifiers changed from: protected */
        public void i() {
            g.this.a(ErrorCode.CANCEL);
            g.this.d.c();
        }

        public void k() {
            if (h()) {
                throw b((IOException) null);
            }
        }
    }

    g(int i2, d dVar, boolean z, boolean z2, y yVar) {
        if (dVar != null) {
            this.c = i2;
            this.d = dVar;
            this.b = (long) dVar.x.c();
            this.f2002g = new b((long) dVar.w.c());
            a aVar = new a();
            this.f2003h = aVar;
            this.f2002g.f2014j = z2;
            aVar.f2008h = z;
            if (yVar != null) {
                this.e.add(yVar);
            }
            if (f() && yVar != null) {
                throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
            } else if (!f() && yVar == null) {
                throw new IllegalStateException("remotely-initiated streams should have headers");
            }
        } else {
            throw new NullPointerException("connection == null");
        }
    }

    private boolean b(ErrorCode errorCode, IOException iOException) {
        synchronized (this) {
            if (this.k != null) {
                return false;
            }
            if (this.f2002g.f2014j && this.f2003h.f2008h) {
                return false;
            }
            this.k = errorCode;
            this.l = iOException;
            notifyAll();
            this.d.c(this.c);
            return true;
        }
    }

    public void a(ErrorCode errorCode, IOException iOException) {
        if (b(errorCode, iOException)) {
            this.d.b(this.c, errorCode);
        }
    }

    public int c() {
        return this.c;
    }

    public q d() {
        synchronized (this) {
            if (!this.f2001f) {
                if (!f()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.f2003h;
    }

    public r e() {
        return this.f2002g;
    }

    public boolean f() {
        if (this.d.e == ((this.c & 1) == 1)) {
            return true;
        }
        return false;
    }

    public synchronized boolean g() {
        if (this.k != null) {
            return false;
        }
        if ((this.f2002g.f2014j || this.f2002g.f2013i) && ((this.f2003h.f2008h || this.f2003h.f2007g) && this.f2001f)) {
            return false;
        }
        return true;
    }

    public s h() {
        return this.f2004i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003d, code lost:
        r2.f2004i.k();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
        throw r0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized okhttp3.y i() {
        /*
            r2 = this;
            monitor-enter(r2)
            okhttp3.internal.http2.g$c r0 = r2.f2004i     // Catch:{ all -> 0x0043 }
            r0.g()     // Catch:{ all -> 0x0043 }
        L_0x0006:
            java.util.Deque<okhttp3.y> r0 = r2.e     // Catch:{ all -> 0x003c }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x0016
            okhttp3.internal.http2.ErrorCode r0 = r2.k     // Catch:{ all -> 0x003c }
            if (r0 != 0) goto L_0x0016
            r2.j()     // Catch:{ all -> 0x003c }
            goto L_0x0006
        L_0x0016:
            okhttp3.internal.http2.g$c r0 = r2.f2004i     // Catch:{ all -> 0x0043 }
            r0.k()     // Catch:{ all -> 0x0043 }
            java.util.Deque<okhttp3.y> r0 = r2.e     // Catch:{ all -> 0x0043 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0043 }
            if (r0 != 0) goto L_0x002d
            java.util.Deque<okhttp3.y> r0 = r2.e     // Catch:{ all -> 0x0043 }
            java.lang.Object r0 = r0.removeFirst()     // Catch:{ all -> 0x0043 }
            okhttp3.y r0 = (okhttp3.y) r0     // Catch:{ all -> 0x0043 }
            monitor-exit(r2)
            return r0
        L_0x002d:
            java.io.IOException r0 = r2.l     // Catch:{ all -> 0x0043 }
            if (r0 == 0) goto L_0x0034
            java.io.IOException r0 = r2.l     // Catch:{ all -> 0x0043 }
            goto L_0x003b
        L_0x0034:
            okhttp3.internal.http2.StreamResetException r0 = new okhttp3.internal.http2.StreamResetException     // Catch:{ all -> 0x0043 }
            okhttp3.internal.http2.ErrorCode r1 = r2.k     // Catch:{ all -> 0x0043 }
            r0.<init>(r1)     // Catch:{ all -> 0x0043 }
        L_0x003b:
            throw r0     // Catch:{ all -> 0x0043 }
        L_0x003c:
            r0 = move-exception
            okhttp3.internal.http2.g$c r1 = r2.f2004i     // Catch:{ all -> 0x0043 }
            r1.k()     // Catch:{ all -> 0x0043 }
            throw r0     // Catch:{ all -> 0x0043 }
        L_0x0043:
            r0 = move-exception
            monitor-exit(r2)
            goto L_0x0047
        L_0x0046:
            throw r0
        L_0x0047:
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.g.i():okhttp3.y");
    }

    /* access modifiers changed from: package-private */
    public void j() {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    public s k() {
        return this.f2005j;
    }

    /* compiled from: Http2Stream */
    final class a implements q {
        private final okio.c e = new okio.c();

        /* renamed from: f  reason: collision with root package name */
        private y f2006f;

        /* renamed from: g  reason: collision with root package name */
        boolean f2007g;

        /* renamed from: h  reason: collision with root package name */
        boolean f2008h;

        a() {
        }

        public void a(okio.c cVar, long j2) {
            this.e.a(cVar, j2);
            while (this.e.r() >= PlaybackStateCompat.ACTION_PREPARE) {
                a(false);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
            if (r8.e.r() <= 0) goto L_0x0022;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
            if (r8.f2006f == null) goto L_0x0029;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
            r3 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
            r3 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
            if (r3 == false) goto L_0x004a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
            if (r8.e.r() <= 0) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
            a(false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
            r0 = r8.f2009i;
            r0.d.a(r0.c, true, okhttp3.k0.e.a(r8.f2006f));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            if (r2 == false) goto L_0x005a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0054, code lost:
            if (r8.e.r() <= 0) goto L_0x0067;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0056, code lost:
            a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005a, code lost:
            r0 = r8.f2009i;
            r0.d.a(r0.c, true, (okio.c) null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0067, code lost:
            r2 = r8.f2009i;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r8.f2007g = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x006c, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x006d, code lost:
            r8.f2009i.d.flush();
            r8.f2009i.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0079, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
            if (r8.f2009i.f2003h.f2008h != false) goto L_0x0067;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r8 = this;
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                monitor-enter(r0)
                boolean r1 = r8.f2007g     // Catch:{ all -> 0x007d }
                if (r1 == 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x007d }
                return
            L_0x0009:
                monitor-exit(r0)     // Catch:{ all -> 0x007d }
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                okhttp3.internal.http2.g$a r0 = r0.f2003h
                boolean r0 = r0.f2008h
                r1 = 1
                if (r0 != 0) goto L_0x0067
                okio.c r0 = r8.e
                long r2 = r0.r()
                r0 = 0
                r4 = 0
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 <= 0) goto L_0x0022
                r2 = 1
                goto L_0x0023
            L_0x0022:
                r2 = 0
            L_0x0023:
                okhttp3.y r3 = r8.f2006f
                if (r3 == 0) goto L_0x0029
                r3 = 1
                goto L_0x002a
            L_0x0029:
                r3 = 0
            L_0x002a:
                if (r3 == 0) goto L_0x004a
            L_0x002c:
                okio.c r2 = r8.e
                long r2 = r2.r()
                int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r6 <= 0) goto L_0x003a
                r8.a(r0)
                goto L_0x002c
            L_0x003a:
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                okhttp3.internal.http2.d r2 = r0.d
                int r0 = r0.c
                okhttp3.y r3 = r8.f2006f
                java.util.List r3 = okhttp3.k0.e.a((okhttp3.y) r3)
                r2.a((int) r0, (boolean) r1, (java.util.List<okhttp3.internal.http2.a>) r3)
                goto L_0x0067
            L_0x004a:
                if (r2 == 0) goto L_0x005a
            L_0x004c:
                okio.c r0 = r8.e
                long r2 = r0.r()
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0067
                r8.a(r1)
                goto L_0x004c
            L_0x005a:
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                okhttp3.internal.http2.d r2 = r0.d
                int r3 = r0.c
                r4 = 1
                r5 = 0
                r6 = 0
                r2.a((int) r3, (boolean) r4, (okio.c) r5, (long) r6)
            L_0x0067:
                okhttp3.internal.http2.g r2 = okhttp3.internal.http2.g.this
                monitor-enter(r2)
                r8.f2007g = r1     // Catch:{ all -> 0x007a }
                monitor-exit(r2)     // Catch:{ all -> 0x007a }
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                okhttp3.internal.http2.d r0 = r0.d
                r0.flush()
                okhttp3.internal.http2.g r0 = okhttp3.internal.http2.g.this
                r0.a()
                return
            L_0x007a:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x007a }
                throw r0
            L_0x007d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x007d }
                goto L_0x0081
            L_0x0080:
                throw r1
            L_0x0081:
                goto L_0x0080
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.g.a.close():void");
        }

        public s d() {
            return g.this.f2005j;
        }

        public void flush() {
            synchronized (g.this) {
                g.this.b();
            }
            while (this.e.r() > 0) {
                a(false);
                g.this.d.flush();
            }
        }

        /* JADX INFO: finally extract failed */
        private void a(boolean z) {
            long min;
            boolean z2;
            synchronized (g.this) {
                g.this.f2005j.g();
                while (g.this.b <= 0 && !this.f2008h && !this.f2007g && g.this.k == null) {
                    try {
                        g.this.j();
                    } catch (Throwable th) {
                        g.this.f2005j.k();
                        throw th;
                    }
                }
                g.this.f2005j.k();
                g.this.b();
                min = Math.min(g.this.b, this.e.r());
                g.this.b -= min;
            }
            g.this.f2005j.g();
            if (z) {
                try {
                    if (min == this.e.r()) {
                        z2 = true;
                        g.this.d.a(g.this.c, z2, this.e, min);
                        g.this.f2005j.k();
                    }
                } catch (Throwable th2) {
                    g.this.f2005j.k();
                    throw th2;
                }
            }
            z2 = false;
            g.this.d.a(g.this.c, z2, this.e, min);
            g.this.f2005j.k();
        }
    }

    public void a(ErrorCode errorCode) {
        if (b(errorCode, (IOException) null)) {
            this.d.c(this.c, errorCode);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(e eVar, int i2) {
        this.f2002g.a(eVar, (long) i2);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(okhttp3.y r3, boolean r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.f2001f     // Catch:{ all -> 0x002e }
            r1 = 1
            if (r0 == 0) goto L_0x000f
            if (r4 != 0) goto L_0x0009
            goto L_0x000f
        L_0x0009:
            okhttp3.internal.http2.g$b r0 = r2.f2002g     // Catch:{ all -> 0x002e }
            okhttp3.y unused = r0.f2012h = r3     // Catch:{ all -> 0x002e }
            goto L_0x0016
        L_0x000f:
            r2.f2001f = r1     // Catch:{ all -> 0x002e }
            java.util.Deque<okhttp3.y> r0 = r2.e     // Catch:{ all -> 0x002e }
            r0.add(r3)     // Catch:{ all -> 0x002e }
        L_0x0016:
            if (r4 == 0) goto L_0x001c
            okhttp3.internal.http2.g$b r3 = r2.f2002g     // Catch:{ all -> 0x002e }
            r3.f2014j = r1     // Catch:{ all -> 0x002e }
        L_0x001c:
            boolean r3 = r2.g()     // Catch:{ all -> 0x002e }
            r2.notifyAll()     // Catch:{ all -> 0x002e }
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            if (r3 != 0) goto L_0x002d
            okhttp3.internal.http2.d r3 = r2.d
            int r4 = r2.c
            r3.c((int) r4)
        L_0x002d:
            return
        L_0x002e:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.g.a(okhttp3.y, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void b(ErrorCode errorCode) {
        if (this.k == null) {
            this.k = errorCode;
            notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        a aVar = this.f2003h;
        if (aVar.f2007g) {
            throw new IOException("stream closed");
        } else if (aVar.f2008h) {
            throw new IOException("stream finished");
        } else if (this.k != null) {
            Throwable th = this.l;
            if (th == null) {
                th = new StreamResetException(this.k);
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        boolean z;
        boolean g2;
        synchronized (this) {
            z = !this.f2002g.f2014j && this.f2002g.f2013i && (this.f2003h.f2008h || this.f2003h.f2007g);
            g2 = g();
        }
        if (z) {
            a(ErrorCode.CANCEL, (IOException) null);
        } else if (!g2) {
            this.d.c(this.c);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(long j2) {
        this.b += j2;
        if (j2 > 0) {
            notifyAll();
        }
    }
}
