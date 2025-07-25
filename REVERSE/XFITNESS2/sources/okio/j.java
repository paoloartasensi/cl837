package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* compiled from: InflaterSource */
public final class j implements r {
    private final e e;

    /* renamed from: f  reason: collision with root package name */
    private final Inflater f2099f;

    /* renamed from: g  reason: collision with root package name */
    private int f2100g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f2101h;

    j(e eVar, Inflater inflater) {
        if (eVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater != null) {
            this.e = eVar;
            this.f2099f = inflater;
        } else {
            throw new IllegalArgumentException("inflater == null");
        }
    }

    private void c() {
        int i2 = this.f2100g;
        if (i2 != 0) {
            int remaining = i2 - this.f2099f.getRemaining();
            this.f2100g -= remaining;
            this.e.skip((long) remaining);
        }
    }

    public final boolean a() {
        if (!this.f2099f.needsInput()) {
            return false;
        }
        c();
        if (this.f2099f.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.e.i()) {
            return true;
        } else {
            o oVar = this.e.b().e;
            int i2 = oVar.c;
            int i3 = oVar.b;
            int i4 = i2 - i3;
            this.f2100g = i4;
            this.f2099f.setInput(oVar.a, i3, i4);
            return false;
        }
    }

    public long b(c cVar, long j2) {
        o b;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        } else if (this.f2101h) {
            throw new IllegalStateException("closed");
        } else if (j2 == 0) {
            return 0;
        } else {
            while (true) {
                boolean a = a();
                try {
                    b = cVar.b(1);
                    int inflate = this.f2099f.inflate(b.a, b.c, (int) Math.min(j2, (long) (8192 - b.c)));
                    if (inflate > 0) {
                        b.c += inflate;
                        long j3 = (long) inflate;
                        cVar.f2094f += j3;
                        return j3;
                    } else if (this.f2099f.finished()) {
                        break;
                    } else if (this.f2099f.needsDictionary()) {
                        break;
                    } else if (a) {
                        throw new EOFException("source exhausted prematurely");
                    }
                } catch (DataFormatException e2) {
                    throw new IOException(e2);
                }
            }
            c();
            if (b.b != b.c) {
                return -1;
            }
            cVar.e = b.b();
            p.a(b);
            return -1;
        }
    }

    public void close() {
        if (!this.f2101h) {
            this.f2099f.end();
            this.f2101h = true;
            this.e.close();
        }
    }

    public s d() {
        return this.e.d();
    }
}
