package okio;

import android.support.v4.media.session.PlaybackStateCompat;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;

/* compiled from: Buffer */
public final class c implements e, d, Cloneable, ByteChannel {

    /* renamed from: g  reason: collision with root package name */
    private static final byte[] f2093g = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    o e;

    /* renamed from: f  reason: collision with root package name */
    long f2094f;

    /* compiled from: Buffer */
    class a extends OutputStream {
        a() {
        }

        public void close() {
        }

        public void flush() {
        }

        public String toString() {
            return c.this + ".outputStream()";
        }

        public void write(int i2) {
            c.this.writeByte((int) (byte) i2);
        }

        public void write(byte[] bArr, int i2, int i3) {
            c.this.write(bArr, i2, i3);
        }
    }

    /* compiled from: Buffer */
    class b extends InputStream {
        b() {
        }

        public int available() {
            return (int) Math.min(c.this.f2094f, 2147483647L);
        }

        public void close() {
        }

        public int read() {
            c cVar = c.this;
            if (cVar.f2094f > 0) {
                return cVar.readByte() & 255;
            }
            return -1;
        }

        public String toString() {
            return c.this + ".inputStream()";
        }

        public int read(byte[] bArr, int i2, int i3) {
            return c.this.a(bArr, i2, i3);
        }
    }

    public ByteString b(long j2) {
        return new ByteString(g(j2));
    }

    public c b() {
        return this;
    }

    public String c(long j2) {
        if (j2 >= 0) {
            long j3 = Long.MAX_VALUE;
            if (j2 != Long.MAX_VALUE) {
                j3 = j2 + 1;
            }
            long a2 = a((byte) 10, 0, j3);
            if (a2 != -1) {
                return j(a2);
            }
            if (j3 < r() && h(j3 - 1) == 13 && h(j3) == 10) {
                return j(j3);
            }
            c cVar = new c();
            a(cVar, 0, Math.min(32, r()));
            throw new EOFException("\\n not found: limit=" + Math.min(r(), j2) + " content=" + cVar.p().hex() + 8230);
        }
        throw new IllegalArgumentException("limit < 0: " + j2);
    }

    public void close() {
    }

    public boolean d(long j2) {
        return this.f2094f >= j2;
    }

    public void e(long j2) {
        if (this.f2094f < j2) {
            throw new EOFException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        long j2 = this.f2094f;
        if (j2 != cVar.f2094f) {
            return false;
        }
        long j3 = 0;
        if (j2 == 0) {
            return true;
        }
        o oVar = this.e;
        o oVar2 = cVar.e;
        int i2 = oVar.b;
        int i3 = oVar2.b;
        while (j3 < this.f2094f) {
            long min = (long) Math.min(oVar.c - i2, oVar2.c - i3);
            int i4 = 0;
            while (((long) i4) < min) {
                int i5 = i2 + 1;
                int i6 = i3 + 1;
                if (oVar.a[i2] != oVar2.a[i3]) {
                    return false;
                }
                i4++;
                i2 = i5;
                i3 = i6;
            }
            if (i2 == oVar.c) {
                oVar = oVar.f2109f;
                i2 = oVar.b;
            }
            if (i3 == oVar2.c) {
                oVar2 = oVar2.f2109f;
                i3 = oVar2.b;
            }
            j3 += min;
        }
        return true;
    }

    public c f() {
        return this;
    }

    public void flush() {
    }

    public String g() {
        return c(Long.MAX_VALUE);
    }

    public c getBuffer() {
        return this;
    }

    public final byte h(long j2) {
        int i2;
        t.a(this.f2094f, j2, 1);
        long j3 = this.f2094f;
        if (j3 - j2 > j2) {
            o oVar = this.e;
            while (true) {
                int i3 = oVar.c;
                int i4 = oVar.b;
                long j4 = (long) (i3 - i4);
                if (j2 < j4) {
                    return oVar.a[i4 + ((int) j2)];
                }
                j2 -= j4;
                oVar = oVar.f2109f;
            }
        } else {
            long j5 = j2 - j3;
            o oVar2 = this.e;
            do {
                oVar2 = oVar2.f2110g;
                int i5 = oVar2.c;
                i2 = oVar2.b;
                j5 += (long) (i5 - i2);
            } while (j5 < 0);
            return oVar2.a[i2 + ((int) j5)];
        }
    }

    public int hashCode() {
        o oVar = this.e;
        if (oVar == null) {
            return 0;
        }
        int i2 = 1;
        do {
            int i3 = oVar.c;
            for (int i4 = oVar.b; i4 < i3; i4++) {
                i2 = (i2 * 31) + oVar.a[i4];
            }
            oVar = oVar.f2109f;
        } while (oVar != this.e);
        return i2;
    }

    public boolean i() {
        return this.f2094f == 0;
    }

    public boolean isOpen() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public String j(long j2) {
        if (j2 > 0) {
            long j3 = j2 - 1;
            if (h(j3) == 13) {
                String i2 = i(j3);
                skip(2);
                return i2;
            }
        }
        String i3 = i(j2);
        skip(1);
        return i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008f, code lost:
        if (r8 != r9) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0091, code lost:
        r15.e = r6.b();
        okio.p.a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009b, code lost:
        r6.b = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009d, code lost:
        if (r1 != false) goto L_0x00a3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0074 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long k() {
        /*
            r15 = this;
            long r0 = r15.f2094f
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x00aa
            r0 = 0
            r4 = r2
            r1 = 0
        L_0x000b:
            okio.o r6 = r15.e
            byte[] r7 = r6.a
            int r8 = r6.b
            int r9 = r6.c
        L_0x0013:
            if (r8 >= r9) goto L_0x008f
            byte r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L_0x0022
            r11 = 57
            if (r10 > r11) goto L_0x0022
            int r11 = r10 + -48
            goto L_0x003a
        L_0x0022:
            r11 = 97
            if (r10 < r11) goto L_0x002f
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L_0x002f
            int r11 = r10 + -97
        L_0x002c:
            int r11 = r11 + 10
            goto L_0x003a
        L_0x002f:
            r11 = 65
            if (r10 < r11) goto L_0x0070
            r11 = 70
            if (r10 > r11) goto L_0x0070
            int r11 = r10 + -65
            goto L_0x002c
        L_0x003a:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r14 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r14 != 0) goto L_0x004a
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L_0x0013
        L_0x004a:
            okio.c r0 = new okio.c
            r0.<init>()
            r0.a((long) r4)
            r0.writeByte((int) r10)
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Number too large: "
            r2.append(r3)
            java.lang.String r0 = r0.q()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x0070:
            if (r0 == 0) goto L_0x0074
            r1 = 1
            goto L_0x008f
        L_0x0074:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Expected leading [0-9a-fA-F] character but was 0x"
            r1.append(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r10)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x008f:
            if (r8 != r9) goto L_0x009b
            okio.o r7 = r6.b()
            r15.e = r7
            okio.p.a(r6)
            goto L_0x009d
        L_0x009b:
            r6.b = r8
        L_0x009d:
            if (r1 != 0) goto L_0x00a3
            okio.o r6 = r15.e
            if (r6 != 0) goto L_0x000b
        L_0x00a3:
            long r1 = r15.f2094f
            long r6 = (long) r0
            long r1 = r1 - r6
            r15.f2094f = r1
            return r4
        L_0x00aa:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            goto L_0x00b3
        L_0x00b2:
            throw r0
        L_0x00b3:
            goto L_0x00b2
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.c.k():long");
    }

    public InputStream l() {
        return new b();
    }

    public final long m() {
        long j2 = this.f2094f;
        if (j2 == 0) {
            return 0;
        }
        o oVar = this.e.f2110g;
        int i2 = oVar.c;
        return (i2 >= 8192 || !oVar.e) ? j2 : j2 - ((long) (i2 - oVar.b));
    }

    public OutputStream n() {
        return new a();
    }

    public byte[] o() {
        try {
            return g(this.f2094f);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public ByteString p() {
        return new ByteString(o());
    }

    public String q() {
        try {
            return a(this.f2094f, t.a);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public final long r() {
        return this.f2094f;
    }

    public int read(ByteBuffer byteBuffer) {
        o oVar = this.e;
        if (oVar == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), oVar.c - oVar.b);
        byteBuffer.put(oVar.a, oVar.b, min);
        int i2 = oVar.b + min;
        oVar.b = i2;
        this.f2094f -= (long) min;
        if (i2 == oVar.c) {
            this.e = oVar.b();
            p.a(oVar);
        }
        return min;
    }

    public byte readByte() {
        long j2 = this.f2094f;
        if (j2 != 0) {
            o oVar = this.e;
            int i2 = oVar.b;
            int i3 = oVar.c;
            int i4 = i2 + 1;
            byte b2 = oVar.a[i2];
            this.f2094f = j2 - 1;
            if (i4 == i3) {
                this.e = oVar.b();
                p.a(oVar);
            } else {
                oVar.b = i4;
            }
            return b2;
        }
        throw new IllegalStateException("size == 0");
    }

    public void readFully(byte[] bArr) {
        int i2 = 0;
        while (i2 < bArr.length) {
            int a2 = a(bArr, i2, bArr.length - i2);
            if (a2 != -1) {
                i2 += a2;
            } else {
                throw new EOFException();
            }
        }
    }

    public int readInt() {
        long j2 = this.f2094f;
        if (j2 >= 4) {
            o oVar = this.e;
            int i2 = oVar.b;
            int i3 = oVar.c;
            if (i3 - i2 < 4) {
                return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
            }
            byte[] bArr = oVar.a;
            int i4 = i2 + 1;
            int i5 = i4 + 1;
            byte b2 = ((bArr[i2] & 255) << 24) | ((bArr[i4] & 255) << 16);
            int i6 = i5 + 1;
            byte b3 = b2 | ((bArr[i5] & 255) << 8);
            int i7 = i6 + 1;
            byte b4 = b3 | (bArr[i6] & 255);
            this.f2094f = j2 - 4;
            if (i7 == i3) {
                this.e = oVar.b();
                p.a(oVar);
            } else {
                oVar.b = i7;
            }
            return b4;
        }
        throw new IllegalStateException("size < 4: " + this.f2094f);
    }

    public short readShort() {
        long j2 = this.f2094f;
        if (j2 >= 2) {
            o oVar = this.e;
            int i2 = oVar.b;
            int i3 = oVar.c;
            if (i3 - i2 < 2) {
                return (short) (((readByte() & 255) << 8) | (readByte() & 255));
            }
            byte[] bArr = oVar.a;
            int i4 = i2 + 1;
            int i5 = i4 + 1;
            byte b2 = ((bArr[i2] & 255) << 8) | (bArr[i4] & 255);
            this.f2094f = j2 - 2;
            if (i5 == i3) {
                this.e = oVar.b();
                p.a(oVar);
            } else {
                oVar.b = i5;
            }
            return (short) b2;
        }
        throw new IllegalStateException("size < 2: " + this.f2094f);
    }

    public final ByteString s() {
        long j2 = this.f2094f;
        if (j2 <= 2147483647L) {
            return a((int) j2);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.f2094f);
    }

    public void skip(long j2) {
        while (j2 > 0) {
            o oVar = this.e;
            if (oVar != null) {
                int min = (int) Math.min(j2, (long) (oVar.c - oVar.b));
                long j3 = (long) min;
                this.f2094f -= j3;
                j2 -= j3;
                o oVar2 = this.e;
                int i2 = oVar2.b + min;
                oVar2.b = i2;
                if (i2 == oVar2.c) {
                    this.e = oVar2.b();
                    p.a(oVar2);
                }
            } else {
                throw new EOFException();
            }
        }
    }

    public String toString() {
        return s().toString();
    }

    /* access modifiers changed from: package-private */
    public o b(int i2) {
        if (i2 < 1 || i2 > 8192) {
            throw new IllegalArgumentException();
        }
        o oVar = this.e;
        if (oVar == null) {
            o a2 = p.a();
            this.e = a2;
            a2.f2110g = a2;
            a2.f2109f = a2;
            return a2;
        }
        o oVar2 = oVar.f2110g;
        if (oVar2.c + i2 <= 8192 && oVar2.e) {
            return oVar2;
        }
        o a3 = p.a();
        oVar2.a(a3);
        return a3;
    }

    public c clone() {
        c cVar = new c();
        if (this.f2094f == 0) {
            return cVar;
        }
        o c = this.e.c();
        cVar.e = c;
        c.f2110g = c;
        c.f2109f = c;
        o oVar = this.e;
        while (true) {
            oVar = oVar.f2109f;
            if (oVar != this.e) {
                cVar.e.f2110g.a(oVar.c());
            } else {
                cVar.f2094f = this.f2094f;
                return cVar;
            }
        }
    }

    public s d() {
        return s.d;
    }

    public short e() {
        return t.a(readShort());
    }

    public byte[] g(long j2) {
        t.a(this.f2094f, 0, j2);
        if (j2 <= 2147483647L) {
            byte[] bArr = new byte[((int) j2)];
            readFully(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
    }

    public String i(long j2) {
        return a(j2, t.a);
    }

    public c writeByte(int i2) {
        o b2 = b(1);
        byte[] bArr = b2.a;
        int i3 = b2.c;
        b2.c = i3 + 1;
        bArr[i3] = (byte) i2;
        this.f2094f++;
        return this;
    }

    public c writeInt(int i2) {
        o b2 = b(4);
        byte[] bArr = b2.a;
        int i3 = b2.c;
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 24) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((i2 >>> 8) & 255);
        bArr[i6] = (byte) (i2 & 255);
        b2.c = i6 + 1;
        this.f2094f += 4;
        return this;
    }

    public c writeShort(int i2) {
        o b2 = b(2);
        byte[] bArr = b2.a;
        int i3 = b2.c;
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        bArr[i4] = (byte) (i2 & 255);
        b2.c = i4 + 1;
        this.f2094f += 2;
        return this;
    }

    public c f(long j2) {
        if (j2 == 0) {
            writeByte(48);
            return this;
        }
        boolean z = false;
        int i2 = 1;
        if (j2 < 0) {
            j2 = -j2;
            if (j2 < 0) {
                a("-9223372036854775808");
                return this;
            }
            z = true;
        }
        if (j2 >= 100000000) {
            i2 = j2 < 1000000000000L ? j2 < 10000000000L ? j2 < 1000000000 ? 9 : 10 : j2 < 100000000000L ? 11 : 12 : j2 < 1000000000000000L ? j2 < 10000000000000L ? 13 : j2 < 100000000000000L ? 14 : 15 : j2 < 100000000000000000L ? j2 < 10000000000000000L ? 16 : 17 : j2 < 1000000000000000000L ? 18 : 19;
        } else if (j2 >= 10000) {
            i2 = j2 < 1000000 ? j2 < 100000 ? 5 : 6 : j2 < 10000000 ? 7 : 8;
        } else if (j2 >= 100) {
            i2 = j2 < 1000 ? 3 : 4;
        } else if (j2 >= 10) {
            i2 = 2;
        }
        if (z) {
            i2++;
        }
        o b2 = b(i2);
        byte[] bArr = b2.a;
        int i3 = b2.c + i2;
        while (j2 != 0) {
            i3--;
            bArr[i3] = f2093g[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z) {
            bArr[i3 - 1] = 45;
        }
        b2.c += i2;
        this.f2094f += (long) i2;
        return this;
    }

    public c write(byte[] bArr) {
        if (bArr != null) {
            write(bArr, 0, bArr.length);
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final c a(c cVar, long j2, long j3) {
        if (cVar != null) {
            t.a(this.f2094f, j2, j3);
            if (j3 == 0) {
                return this;
            }
            cVar.f2094f += j3;
            o oVar = this.e;
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
                o c = oVar.c();
                int i4 = (int) (((long) c.b) + j2);
                c.b = i4;
                c.c = Math.min(i4 + ((int) j3), c.c);
                o oVar2 = cVar.e;
                if (oVar2 == null) {
                    c.f2110g = c;
                    c.f2109f = c;
                    cVar.e = c;
                } else {
                    oVar2.f2110g.a(c);
                }
                j3 -= (long) (c.c - c.b);
                oVar = oVar.f2109f;
                j2 = 0;
            }
            return this;
        }
        throw new IllegalArgumentException("out == null");
    }

    public c write(byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            long j2 = (long) i3;
            t.a((long) bArr.length, (long) i2, j2);
            int i4 = i3 + i2;
            while (i2 < i4) {
                o b2 = b(1);
                int min = Math.min(i4 - i2, 8192 - b2.c);
                System.arraycopy(bArr, i2, b2.a, b2.c, min);
                i2 += min;
                b2.c += min;
            }
            this.f2094f += j2;
            return this;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final void j() {
        try {
            skip(this.f2094f);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public long b(c cVar, long j2) {
        if (cVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j2 >= 0) {
            long j3 = this.f2094f;
            if (j3 == 0) {
                return -1;
            }
            if (j2 > j3) {
                j2 = j3;
            }
            cVar.a(this, j2);
            return j2;
        } else {
            throw new IllegalArgumentException("byteCount < 0: " + j2);
        }
    }

    public int h() {
        return t.a(readInt());
    }

    public c c(int i2) {
        if (i2 < 128) {
            writeByte(i2);
        } else if (i2 < 2048) {
            writeByte((i2 >> 6) | 192);
            writeByte((i2 & 63) | 128);
        } else if (i2 < 65536) {
            if (i2 < 55296 || i2 > 57343) {
                writeByte((i2 >> 12) | 224);
                writeByte(((i2 >> 6) & 63) | 128);
                writeByte((i2 & 63) | 128);
            } else {
                writeByte(63);
            }
        } else if (i2 <= 1114111) {
            writeByte((i2 >> 18) | 240);
            writeByte(((i2 >> 12) & 63) | 128);
            writeByte(((i2 >> 6) & 63) | 128);
            writeByte((i2 & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i2));
        }
        return this;
    }

    public int write(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            int remaining = byteBuffer.remaining();
            int i2 = remaining;
            while (i2 > 0) {
                o b2 = b(1);
                int min = Math.min(i2, 8192 - b2.c);
                byteBuffer.get(b2.a, b2.c, min);
                i2 -= min;
                b2.c += min;
            }
            this.f2094f += (long) remaining;
            return remaining;
        }
        throw new IllegalArgumentException("source == null");
    }

    public int a(l lVar) {
        int a2 = a(lVar, false);
        if (a2 == -1) {
            return -1;
        }
        try {
            skip((long) lVar.e[a2].size());
            return a2;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: package-private */
    public int a(l lVar, boolean z) {
        int i2;
        int i3;
        int i4;
        o oVar;
        int i5;
        l lVar2 = lVar;
        o oVar2 = this.e;
        int i6 = -2;
        if (oVar2 != null) {
            byte[] bArr = oVar2.a;
            int i7 = oVar2.b;
            int i8 = oVar2.c;
            int[] iArr = lVar2.f2104f;
            o oVar3 = oVar2;
            int i9 = 0;
            int i10 = -1;
            loop0:
            while (true) {
                int i11 = i9 + 1;
                int i12 = iArr[i9];
                int i13 = i11 + 1;
                int i14 = iArr[i11];
                if (i14 != -1) {
                    i10 = i14;
                }
                if (oVar3 == null) {
                    break;
                }
                if (i12 < 0) {
                    int i15 = i13 + (i12 * -1);
                    while (true) {
                        int i16 = i7 + 1;
                        int i17 = i13 + 1;
                        if ((bArr[i7] & 255) != iArr[i13]) {
                            return i10;
                        }
                        boolean z2 = i17 == i15;
                        if (i16 == i8) {
                            o oVar4 = oVar3.f2109f;
                            i5 = oVar4.b;
                            byte[] bArr2 = oVar4.a;
                            i4 = oVar4.c;
                            if (oVar4 != oVar2) {
                                byte[] bArr3 = bArr2;
                                oVar = oVar4;
                                bArr = bArr3;
                            } else if (!z2) {
                                break loop0;
                            } else {
                                bArr = bArr2;
                                oVar = null;
                            }
                        } else {
                            o oVar5 = oVar3;
                            i4 = i8;
                            i5 = i16;
                            oVar = oVar5;
                        }
                        if (z2) {
                            i2 = iArr[i17];
                            i3 = i5;
                            i8 = i4;
                            oVar3 = oVar;
                            break;
                        }
                        i7 = i5;
                        i8 = i4;
                        i13 = i17;
                        oVar3 = oVar;
                    }
                } else {
                    int i18 = i7 + 1;
                    byte b2 = bArr[i7] & 255;
                    int i19 = i13 + i12;
                    while (i13 != i19) {
                        if (b2 == iArr[i13]) {
                            i2 = iArr[i13 + i12];
                            if (i18 == i8) {
                                oVar3 = oVar3.f2109f;
                                i3 = oVar3.b;
                                bArr = oVar3.a;
                                i8 = oVar3.c;
                                if (oVar3 == oVar2) {
                                    oVar3 = null;
                                }
                            } else {
                                i3 = i18;
                            }
                        } else {
                            i13++;
                        }
                    }
                    return i10;
                }
                if (i2 >= 0) {
                    return i2;
                }
                i9 = -i2;
                i7 = i3;
                i6 = -2;
            }
            return z ? i6 : i10;
        } else if (z) {
            return -2;
        } else {
            return lVar2.indexOf(ByteString.EMPTY);
        }
    }

    public long a(q qVar) {
        long j2 = this.f2094f;
        if (j2 > 0) {
            qVar.a(this, j2);
        }
        return j2;
    }

    public String a(Charset charset) {
        try {
            return a(this.f2094f, charset);
        } catch (EOFException e2) {
            throw new AssertionError(e2);
        }
    }

    public String a(long j2, Charset charset) {
        t.a(this.f2094f, 0, j2);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j2 > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j2);
        } else if (j2 == 0) {
            return BuildConfig.FLAVOR;
        } else {
            o oVar = this.e;
            if (((long) oVar.b) + j2 > ((long) oVar.c)) {
                return new String(g(j2), charset);
            }
            String str = new String(oVar.a, oVar.b, (int) j2, charset);
            int i2 = (int) (((long) oVar.b) + j2);
            oVar.b = i2;
            this.f2094f -= j2;
            if (i2 == oVar.c) {
                this.e = oVar.b();
                p.a(oVar);
            }
            return str;
        }
    }

    public int a(byte[] bArr, int i2, int i3) {
        t.a((long) bArr.length, (long) i2, (long) i3);
        o oVar = this.e;
        if (oVar == null) {
            return -1;
        }
        int min = Math.min(i3, oVar.c - oVar.b);
        System.arraycopy(oVar.a, oVar.b, bArr, i2, min);
        int i4 = oVar.b + min;
        oVar.b = i4;
        this.f2094f -= (long) min;
        if (i4 == oVar.c) {
            this.e = oVar.b();
            p.a(oVar);
        }
        return min;
    }

    public c a(ByteString byteString) {
        if (byteString != null) {
            byteString.write(this);
            return this;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    public c a(String str) {
        a(str, 0, str.length());
        return this;
    }

    public c a(String str, int i2, int i3) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("beginIndex < 0: " + i2);
        } else if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        } else if (i3 <= str.length()) {
            while (i2 < i3) {
                char charAt = str.charAt(i2);
                if (charAt < 128) {
                    o b2 = b(1);
                    byte[] bArr = b2.a;
                    int i4 = b2.c - i2;
                    int min = Math.min(i3, 8192 - i4);
                    int i5 = i2 + 1;
                    bArr[i2 + i4] = (byte) charAt;
                    while (i5 < min) {
                        char charAt2 = str.charAt(i5);
                        if (charAt2 >= 128) {
                            break;
                        }
                        bArr[i5 + i4] = (byte) charAt2;
                        i5++;
                    }
                    int i6 = b2.c;
                    int i7 = (i4 + i5) - i6;
                    b2.c = i6 + i7;
                    this.f2094f += (long) i7;
                    i2 = i5;
                } else {
                    if (charAt < 2048) {
                        writeByte((charAt >> 6) | 192);
                        writeByte((int) (charAt & '?') | 128);
                    } else if (charAt < 55296 || charAt > 57343) {
                        writeByte((charAt >> 12) | 224);
                        writeByte(((charAt >> 6) & 63) | 128);
                        writeByte((int) (charAt & '?') | 128);
                    } else {
                        int i8 = i2 + 1;
                        char charAt3 = i8 < i3 ? str.charAt(i8) : 0;
                        if (charAt > 56319 || charAt3 < 56320 || charAt3 > 57343) {
                            writeByte(63);
                            i2 = i8;
                        } else {
                            int i9 = (((charAt & 10239) << 10) | (9215 & charAt3)) + 0;
                            writeByte((i9 >> 18) | 240);
                            writeByte(((i9 >> 12) & 63) | 128);
                            writeByte(((i9 >> 6) & 63) | 128);
                            writeByte((i9 & 63) | 128);
                            i2 += 2;
                        }
                    }
                    i2++;
                }
            }
            return this;
        } else {
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        }
    }

    public c a(String str, int i2, int i3, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i2 < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i2);
        } else if (i3 < i2) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i3 + " < " + i2);
        } else if (i3 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i3 + " > " + str.length());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(t.a)) {
            a(str, i2, i3);
            return this;
        } else {
            byte[] bytes = str.substring(i2, i3).getBytes(charset);
            write(bytes, 0, bytes.length);
            return this;
        }
    }

    public long a(r rVar) {
        if (rVar != null) {
            long j2 = 0;
            while (true) {
                long b2 = rVar.b(this, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
                if (b2 == -1) {
                    return j2;
                }
                j2 += b2;
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public c a(long j2) {
        if (j2 == 0) {
            writeByte(48);
            return this;
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j2)) / 4) + 1;
        o b2 = b(numberOfTrailingZeros);
        byte[] bArr = b2.a;
        int i2 = b2.c;
        for (int i3 = (i2 + numberOfTrailingZeros) - 1; i3 >= i2; i3--) {
            bArr[i3] = f2093g[(int) (15 & j2)];
            j2 >>>= 4;
        }
        b2.c += numberOfTrailingZeros;
        this.f2094f += (long) numberOfTrailingZeros;
        return this;
    }

    public void a(c cVar, long j2) {
        int i2;
        if (cVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (cVar != this) {
            t.a(cVar.f2094f, 0, j2);
            while (j2 > 0) {
                o oVar = cVar.e;
                if (j2 < ((long) (oVar.c - oVar.b))) {
                    o oVar2 = this.e;
                    o oVar3 = oVar2 != null ? oVar2.f2110g : null;
                    if (oVar3 != null && oVar3.e) {
                        long j3 = ((long) oVar3.c) + j2;
                        if (oVar3.d) {
                            i2 = 0;
                        } else {
                            i2 = oVar3.b;
                        }
                        if (j3 - ((long) i2) <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                            cVar.e.a(oVar3, (int) j2);
                            cVar.f2094f -= j2;
                            this.f2094f += j2;
                            return;
                        }
                    }
                    cVar.e = cVar.e.a((int) j2);
                }
                o oVar4 = cVar.e;
                long j4 = (long) (oVar4.c - oVar4.b);
                cVar.e = oVar4.b();
                o oVar5 = this.e;
                if (oVar5 == null) {
                    this.e = oVar4;
                    oVar4.f2110g = oVar4;
                    oVar4.f2109f = oVar4;
                } else {
                    oVar5.f2110g.a(oVar4);
                    oVar4.a();
                }
                cVar.f2094f -= j4;
                this.f2094f += j4;
                j2 -= j4;
            }
        } else {
            throw new IllegalArgumentException("source == this");
        }
    }

    public long a(byte b2) {
        return a(b2, 0, Long.MAX_VALUE);
    }

    public long a(byte b2, long j2, long j3) {
        o oVar;
        long j4 = 0;
        if (j2 < 0 || j3 < j2) {
            throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", new Object[]{Long.valueOf(this.f2094f), Long.valueOf(j2), Long.valueOf(j3)}));
        }
        long j5 = this.f2094f;
        if (j3 <= j5) {
            j5 = j3;
        }
        if (j2 == j5 || (oVar = this.e) == null) {
            return -1;
        }
        long j6 = this.f2094f;
        if (j6 - j2 < j2) {
            while (j6 > j2) {
                oVar = oVar.f2110g;
                j6 -= (long) (oVar.c - oVar.b);
            }
        } else {
            while (true) {
                long j7 = ((long) (oVar.c - oVar.b)) + j4;
                if (j7 >= j2) {
                    break;
                }
                oVar = oVar.f2109f;
                j4 = j7;
            }
            j6 = j4;
        }
        long j8 = j2;
        while (j6 < j5) {
            byte[] bArr = oVar.a;
            int min = (int) Math.min((long) oVar.c, (((long) oVar.b) + j5) - j6);
            for (int i2 = (int) ((((long) oVar.b) + j8) - j6); i2 < min; i2++) {
                if (bArr[i2] == b2) {
                    return ((long) (i2 - oVar.b)) + j6;
                }
            }
            byte b3 = b2;
            j6 += (long) (oVar.c - oVar.b);
            oVar = oVar.f2109f;
            j8 = j6;
        }
        return -1;
    }

    public final ByteString a(int i2) {
        if (i2 == 0) {
            return ByteString.EMPTY;
        }
        return new SegmentedByteString(this, i2);
    }
}
