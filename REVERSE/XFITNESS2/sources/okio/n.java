package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* compiled from: RealBufferedSource */
final class n implements e {
    public final c e = new c();

    /* renamed from: f  reason: collision with root package name */
    public final r f2107f;

    /* renamed from: g  reason: collision with root package name */
    boolean f2108g;

    n(r rVar) {
        if (rVar != null) {
            this.f2107f = rVar;
            return;
        }
        throw new NullPointerException("source == null");
    }

    public int a(l lVar) {
        if (!this.f2108g) {
            do {
                int a2 = this.e.a(lVar, true);
                if (a2 == -1) {
                    return -1;
                }
                if (a2 != -2) {
                    this.e.skip((long) lVar.e[a2].size());
                    return a2;
                }
            } while (this.f2107f.b(this.e, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
            return -1;
        }
        throw new IllegalStateException("closed");
    }

    public c b() {
        return this.e;
    }

    public String c(long j2) {
        if (j2 >= 0) {
            long j3 = j2 == Long.MAX_VALUE ? Long.MAX_VALUE : j2 + 1;
            long a2 = a((byte) 10, 0, j3);
            if (a2 != -1) {
                return this.e.j(a2);
            }
            if (j3 < Long.MAX_VALUE && d(j3) && this.e.h(j3 - 1) == 13 && d(1 + j3) && this.e.h(j3) == 10) {
                return this.e.j(j3);
            }
            c cVar = new c();
            c cVar2 = this.e;
            cVar2.a(cVar, 0, Math.min(32, cVar2.r()));
            throw new EOFException("\\n not found: limit=" + Math.min(this.e.r(), j2) + " content=" + cVar.p().hex() + 8230);
        }
        throw new IllegalArgumentException("limit < 0: " + j2);
    }

    public void close() {
        if (!this.f2108g) {
            this.f2108g = true;
            this.f2107f.close();
            this.e.j();
        }
    }

    public boolean d(long j2) {
        c cVar;
        if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        } else if (!this.f2108g) {
            do {
                cVar = this.e;
                if (cVar.f2094f >= j2) {
                    return true;
                }
            } while (this.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1);
            return false;
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public void e(long j2) {
        if (!d(j2)) {
            throw new EOFException();
        }
    }

    public byte[] g(long j2) {
        e(j2);
        return this.e.g(j2);
    }

    public c getBuffer() {
        return this.e;
    }

    public int h() {
        e(4);
        return this.e.h();
    }

    public boolean i() {
        if (!this.f2108g) {
            return this.e.i() && this.f2107f.b(this.e, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1;
        }
        throw new IllegalStateException("closed");
    }

    public boolean isOpen() {
        return !this.f2108g;
    }

    public long k() {
        e(1);
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (!d((long) i3)) {
                break;
            }
            byte h2 = this.e.h((long) i2);
            if ((h2 >= 48 && h2 <= 57) || ((h2 >= 97 && h2 <= 102) || (h2 >= 65 && h2 <= 70))) {
                i2 = i3;
            } else if (i2 == 0) {
                throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[]{Byte.valueOf(h2)}));
            }
        }
        return this.e.k();
    }

    public InputStream l() {
        return new a();
    }

    public int read(ByteBuffer byteBuffer) {
        c cVar = this.e;
        if (cVar.f2094f == 0 && this.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.e.read(byteBuffer);
    }

    public byte readByte() {
        e(1);
        return this.e.readByte();
    }

    public void readFully(byte[] bArr) {
        try {
            e((long) bArr.length);
            this.e.readFully(bArr);
        } catch (EOFException e2) {
            int i2 = 0;
            while (true) {
                c cVar = this.e;
                long j2 = cVar.f2094f;
                if (j2 > 0) {
                    int a2 = cVar.a(bArr, i2, (int) j2);
                    if (a2 != -1) {
                        i2 += a2;
                    } else {
                        throw new AssertionError();
                    }
                } else {
                    throw e2;
                }
            }
        }
    }

    public int readInt() {
        e(4);
        return this.e.readInt();
    }

    public short readShort() {
        e(2);
        return this.e.readShort();
    }

    public void skip(long j2) {
        if (!this.f2108g) {
            while (j2 > 0) {
                c cVar = this.e;
                if (cVar.f2094f == 0 && this.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    throw new EOFException();
                }
                long min = Math.min(j2, this.e.r());
                this.e.skip(min);
                j2 -= min;
            }
            return;
        }
        throw new IllegalStateException("closed");
    }

    public String toString() {
        return "buffer(" + this.f2107f + ")";
    }

    public long b(c cVar, long j2) {
        if (cVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        } else if (!this.f2108g) {
            c cVar2 = this.e;
            if (cVar2.f2094f == 0 && this.f2107f.b(cVar2, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1;
            }
            return this.e.b(cVar, Math.min(j2, this.e.f2094f));
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public short e() {
        e(2);
        return this.e.e();
    }

    public String g() {
        return c(Long.MAX_VALUE);
    }

    /* compiled from: RealBufferedSource */
    class a extends InputStream {
        a() {
        }

        public int available() {
            n nVar = n.this;
            if (!nVar.f2108g) {
                return (int) Math.min(nVar.e.f2094f, 2147483647L);
            }
            throw new IOException("closed");
        }

        public void close() {
            n.this.close();
        }

        public int read() {
            n nVar = n.this;
            if (!nVar.f2108g) {
                c cVar = nVar.e;
                if (cVar.f2094f == 0 && nVar.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return n.this.e.readByte() & 255;
            }
            throw new IOException("closed");
        }

        public String toString() {
            return n.this + ".inputStream()";
        }

        public int read(byte[] bArr, int i2, int i3) {
            if (!n.this.f2108g) {
                t.a((long) bArr.length, (long) i2, (long) i3);
                n nVar = n.this;
                c cVar = nVar.e;
                if (cVar.f2094f == 0 && nVar.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return n.this.e.a(bArr, i2, i3);
            }
            throw new IOException("closed");
        }
    }

    public s d() {
        return this.f2107f.d();
    }

    public long a(q qVar) {
        if (qVar != null) {
            long j2 = 0;
            while (this.f2107f.b(this.e, PlaybackStateCompat.ACTION_PLAY_FROM_URI) != -1) {
                long m = this.e.m();
                if (m > 0) {
                    j2 += m;
                    qVar.a(this.e, m);
                }
            }
            if (this.e.r() <= 0) {
                return j2;
            }
            long r = j2 + this.e.r();
            c cVar = this.e;
            qVar.a(cVar, cVar.r());
            return r;
        }
        throw new IllegalArgumentException("sink == null");
    }

    public ByteString b(long j2) {
        e(j2);
        return this.e.b(j2);
    }

    public long a(byte b) {
        return a(b, 0, Long.MAX_VALUE);
    }

    public long a(byte b, long j2, long j3) {
        if (this.f2108g) {
            throw new IllegalStateException("closed");
        } else if (j2 < 0 || j3 < j2) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", new Object[]{Long.valueOf(j2), Long.valueOf(j3)}));
        } else {
            while (j2 < j3) {
                long a2 = this.e.a(b, j2, j3);
                if (a2 == -1) {
                    c cVar = this.e;
                    long j4 = cVar.f2094f;
                    if (j4 >= j3 || this.f2107f.b(cVar, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                        break;
                    }
                    j2 = Math.max(j2, j4);
                } else {
                    return a2;
                }
            }
            return -1;
        }
    }
}
