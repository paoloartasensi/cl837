package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* compiled from: GzipSource */
public final class i implements r {
    private int e = 0;

    /* renamed from: f  reason: collision with root package name */
    private final e f2095f;

    /* renamed from: g  reason: collision with root package name */
    private final Inflater f2096g;

    /* renamed from: h  reason: collision with root package name */
    private final j f2097h;

    /* renamed from: i  reason: collision with root package name */
    private final CRC32 f2098i = new CRC32();

    public i(r rVar) {
        if (rVar != null) {
            this.f2096g = new Inflater(true);
            e a = k.a(rVar);
            this.f2095f = a;
            this.f2097h = new j(a, this.f2096g);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    private void a() {
        this.f2095f.e(10);
        byte h2 = this.f2095f.b().h(3);
        boolean z = ((h2 >> 1) & 1) == 1;
        if (z) {
            a(this.f2095f.b(), 0, 10);
        }
        a("ID1ID2", 8075, (int) this.f2095f.readShort());
        this.f2095f.skip(8);
        if (((h2 >> 2) & 1) == 1) {
            this.f2095f.e(2);
            if (z) {
                a(this.f2095f.b(), 0, 2);
            }
            long e2 = (long) this.f2095f.b().e();
            this.f2095f.e(e2);
            if (z) {
                a(this.f2095f.b(), 0, e2);
            }
            this.f2095f.skip(e2);
        }
        if (((h2 >> 3) & 1) == 1) {
            long a = this.f2095f.a((byte) 0);
            if (a != -1) {
                if (z) {
                    a(this.f2095f.b(), 0, a + 1);
                }
                this.f2095f.skip(a + 1);
            } else {
                throw new EOFException();
            }
        }
        if (((h2 >> 4) & 1) == 1) {
            long a2 = this.f2095f.a((byte) 0);
            if (a2 != -1) {
                if (z) {
                    a(this.f2095f.b(), 0, a2 + 1);
                }
                this.f2095f.skip(a2 + 1);
            } else {
                throw new EOFException();
            }
        }
        if (z) {
            a("FHCRC", (int) this.f2095f.e(), (int) (short) ((int) this.f2098i.getValue()));
            this.f2098i.reset();
        }
    }

    private void c() {
        a("CRC", this.f2095f.h(), (int) this.f2098i.getValue());
        a("ISIZE", this.f2095f.h(), (int) this.f2096g.getBytesWritten());
    }

    public long b(c cVar, long j2) {
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        } else if (j2 == 0) {
            return 0;
        } else {
            if (this.e == 0) {
                a();
                this.e = 1;
            }
            if (this.e == 1) {
                long j3 = cVar.f2094f;
                long b = this.f2097h.b(cVar, j2);
                if (b != -1) {
                    a(cVar, j3, b);
                    return b;
                }
                this.e = 2;
            }
            if (this.e == 2) {
                c();
                this.e = 3;
                if (!this.f2095f.i()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1;
        }
    }

    public void close() {
        this.f2097h.close();
    }

    public s d() {
        return this.f2095f.d();
    }

    private void a(c cVar, long j2, long j3) {
        o oVar = cVar.e;
        while (true) {
            int i2 = oVar.c;
            int i3 = oVar.b;
            if (j2 < ((long) (i2 - i3))) {
                break;
            }
            j2 -= (long) (i2 - i3);
            oVar = oVar.f2109f;
        }
        while (j3 > 0) {
            int i4 = (int) (((long) oVar.b) + j2);
            int min = (int) Math.min((long) (oVar.c - i4), j3);
            this.f2098i.update(oVar.a, i4, min);
            j3 -= (long) min;
            oVar = oVar.f2109f;
            j2 = 0;
        }
    }

    private void a(String str, int i2, int i3) {
        if (i3 != i2) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", new Object[]{str, Integer.valueOf(i3), Integer.valueOf(i2)}));
        }
    }
}
