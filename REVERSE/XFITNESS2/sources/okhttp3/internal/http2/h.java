package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.http2.b;
import okhttp3.k0.e;
import okio.c;
import okio.d;

/* compiled from: Http2Writer */
final class h implements Closeable {
    private static final Logger k = Logger.getLogger(c.class.getName());
    private final d e;

    /* renamed from: f  reason: collision with root package name */
    private final boolean f2015f;

    /* renamed from: g  reason: collision with root package name */
    private final c f2016g;

    /* renamed from: h  reason: collision with root package name */
    private int f2017h = 16384;

    /* renamed from: i  reason: collision with root package name */
    private boolean f2018i;

    /* renamed from: j  reason: collision with root package name */
    final b.C0092b f2019j;

    h(d dVar, boolean z) {
        this.e = dVar;
        this.f2015f = z;
        c cVar = new c();
        this.f2016g = cVar;
        this.f2019j = new b.C0092b(cVar);
    }

    public synchronized void a() {
        if (this.f2018i) {
            throw new IOException("closed");
        } else if (this.f2015f) {
            if (k.isLoggable(Level.FINE)) {
                k.fine(e.a(">> CONNECTION %s", c.a.hex()));
            }
            this.e.write(c.a.toByteArray());
            this.e.flush();
        }
    }

    public synchronized void b(k kVar) {
        if (!this.f2018i) {
            int i2 = 0;
            a(0, kVar.d() * 6, (byte) 4, (byte) 0);
            while (i2 < 10) {
                if (kVar.d(i2)) {
                    this.e.writeShort(i2 == 4 ? 3 : i2 == 7 ? 4 : i2);
                    this.e.writeInt(kVar.a(i2));
                }
                i2++;
            }
            this.e.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public int c() {
        return this.f2017h;
    }

    public synchronized void close() {
        this.f2018i = true;
        this.e.close();
    }

    public synchronized void flush() {
        if (!this.f2018i) {
            this.e.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(k kVar) {
        if (!this.f2018i) {
            this.f2017h = kVar.c(this.f2017h);
            if (kVar.b() != -1) {
                this.f2019j.a(kVar.b());
            }
            a(0, 0, (byte) 4, (byte) 1);
            this.e.flush();
        } else {
            throw new IOException("closed");
        }
    }

    private void b(int i2, long j2) {
        while (j2 > 0) {
            int min = (int) Math.min((long) this.f2017h, j2);
            long j3 = (long) min;
            j2 -= j3;
            a(i2, min, (byte) 9, j2 == 0 ? (byte) 4 : 0);
            this.e.a(this.f2016g, j3);
        }
    }

    public synchronized void a(int i2, int i3, List<a> list) {
        if (!this.f2018i) {
            this.f2019j.a(list);
            long r = this.f2016g.r();
            int min = (int) Math.min((long) (this.f2017h - 4), r);
            long j2 = (long) min;
            a(i2, min + 4, (byte) 5, r == j2 ? (byte) 4 : 0);
            this.e.writeInt(i3 & Integer.MAX_VALUE);
            this.e.a(this.f2016g, j2);
            if (r > j2) {
                b(i2, r - j2);
            }
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(int i2, ErrorCode errorCode) {
        if (this.f2018i) {
            throw new IOException("closed");
        } else if (errorCode.httpCode != -1) {
            a(i2, 4, (byte) 3, (byte) 0);
            this.e.writeInt(errorCode.httpCode);
            this.e.flush();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public synchronized void a(boolean z, int i2, c cVar, int i3) {
        if (!this.f2018i) {
            byte b = 0;
            if (z) {
                b = (byte) 1;
            }
            a(i2, b, cVar, i3);
        } else {
            throw new IOException("closed");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, byte b, c cVar, int i3) {
        a(i2, i3, (byte) 0, b);
        if (i3 > 0) {
            this.e.a(cVar, (long) i3);
        }
    }

    public synchronized void a(boolean z, int i2, int i3) {
        if (!this.f2018i) {
            a(0, 8, (byte) 6, z ? (byte) 1 : 0);
            this.e.writeInt(i2);
            this.e.writeInt(i3);
            this.e.flush();
        } else {
            throw new IOException("closed");
        }
    }

    public synchronized void a(int i2, ErrorCode errorCode, byte[] bArr) {
        if (this.f2018i) {
            throw new IOException("closed");
        } else if (errorCode.httpCode != -1) {
            a(0, bArr.length + 8, (byte) 7, (byte) 0);
            this.e.writeInt(i2);
            this.e.writeInt(errorCode.httpCode);
            if (bArr.length > 0) {
                this.e.write(bArr);
            }
            this.e.flush();
        } else {
            c.a("errorCode.httpCode == -1", new Object[0]);
            throw null;
        }
    }

    public synchronized void a(int i2, long j2) {
        if (this.f2018i) {
            throw new IOException("closed");
        } else if (j2 == 0 || j2 > 2147483647L) {
            c.a("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", Long.valueOf(j2));
            throw null;
        } else {
            a(i2, 4, (byte) 8, (byte) 0);
            this.e.writeInt((int) j2);
            this.e.flush();
        }
    }

    public void a(int i2, int i3, byte b, byte b2) {
        if (k.isLoggable(Level.FINE)) {
            k.fine(c.a(false, i2, i3, b, b2));
        }
        int i4 = this.f2017h;
        if (i3 > i4) {
            c.a("FRAME_SIZE_ERROR length > %d: %d", Integer.valueOf(i4), Integer.valueOf(i3));
            throw null;
        } else if ((Integer.MIN_VALUE & i2) == 0) {
            a(this.e, i3);
            this.e.writeByte(b & 255);
            this.e.writeByte(b2 & 255);
            this.e.writeInt(i2 & Integer.MAX_VALUE);
        } else {
            c.a("reserved bit set: %s", Integer.valueOf(i2));
            throw null;
        }
    }

    private static void a(d dVar, int i2) {
        dVar.writeByte((i2 >>> 16) & 255);
        dVar.writeByte((i2 >>> 8) & 255);
        dVar.writeByte(i2 & 255);
    }

    public synchronized void a(boolean z, int i2, List<a> list) {
        if (!this.f2018i) {
            this.f2019j.a(list);
            long r = this.f2016g.r();
            int min = (int) Math.min((long) this.f2017h, r);
            long j2 = (long) min;
            byte b = r == j2 ? (byte) 4 : 0;
            if (z) {
                b = (byte) (b | 1);
            }
            a(i2, min, (byte) 1, b);
            this.e.a(this.f2016g, j2);
            if (r > j2) {
                b(i2, r - j2);
            }
        } else {
            throw new IOException("closed");
        }
    }
}
