package okio;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

final class SegmentedByteString extends ByteString {
    final transient int[] directory;
    final transient byte[][] segments;

    SegmentedByteString(c cVar, int i2) {
        super((byte[]) null);
        t.a(cVar.f2094f, 0, (long) i2);
        o oVar = cVar.e;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = oVar.c;
            int i7 = oVar.b;
            if (i6 != i7) {
                i4 += i6 - i7;
                i5++;
                oVar = oVar.f2109f;
            } else {
                throw new AssertionError("s.limit == s.pos");
            }
        }
        this.segments = new byte[i5][];
        this.directory = new int[(i5 * 2)];
        o oVar2 = cVar.e;
        int i8 = 0;
        while (i3 < i2) {
            this.segments[i8] = oVar2.a;
            i3 += oVar2.c - oVar2.b;
            if (i3 > i2) {
                i3 = i2;
            }
            int[] iArr = this.directory;
            iArr[i8] = i3;
            iArr[this.segments.length + i8] = oVar2.b;
            oVar2.d = true;
            i8++;
            oVar2 = oVar2.f2109f;
        }
    }

    private int a(int i2) {
        int binarySearch = Arrays.binarySearch(this.directory, 0, this.segments.length, i2 + 1);
        return binarySearch >= 0 ? binarySearch : binarySearch ^ -1;
    }

    private Object writeReplace() {
        return a();
    }

    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public String base64() {
        return a().base64();
    }

    public String base64Url() {
        return a().base64Url();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            if (byteString.size() != size() || !rangeEquals(0, byteString, 0, size())) {
                return false;
            }
            return true;
        }
        return false;
    }

    public byte getByte(int i2) {
        int i3;
        t.a((long) this.directory[this.segments.length - 1], (long) i2, 1);
        int a = a(i2);
        if (a == 0) {
            i3 = 0;
        } else {
            i3 = this.directory[a - 1];
        }
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        return bArr[a][(i2 - i3) + iArr[bArr.length + a]];
    }

    public int hashCode() {
        int i2 = this.hashCode;
        if (i2 != 0) {
            return i2;
        }
        int length = this.segments.length;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (i3 < length) {
            byte[] bArr = this.segments[i3];
            int[] iArr = this.directory;
            int i6 = iArr[length + i3];
            int i7 = iArr[i3];
            int i8 = (i7 - i4) + i6;
            while (i6 < i8) {
                i5 = (i5 * 31) + bArr[i6];
                i6++;
            }
            i3++;
            i4 = i7;
        }
        this.hashCode = i5;
        return i5;
    }

    public String hex() {
        return a().hex();
    }

    public ByteString hmacSha1(ByteString byteString) {
        return a().hmacSha1(byteString);
    }

    public ByteString hmacSha256(ByteString byteString) {
        return a().hmacSha256(byteString);
    }

    public int indexOf(byte[] bArr, int i2) {
        return a().indexOf(bArr, i2);
    }

    /* access modifiers changed from: package-private */
    public byte[] internalArray() {
        return toByteArray();
    }

    public int lastIndexOf(byte[] bArr, int i2) {
        return a().lastIndexOf(bArr, i2);
    }

    public ByteString md5() {
        return a().md5();
    }

    public boolean rangeEquals(int i2, ByteString byteString, int i3, int i4) {
        int i5;
        if (i2 < 0 || i2 > size() - i4) {
            return false;
        }
        int a = a(i2);
        while (i4 > 0) {
            if (a == 0) {
                i5 = 0;
            } else {
                i5 = this.directory[a - 1];
            }
            int min = Math.min(i4, ((this.directory[a] - i5) + i5) - i2);
            int[] iArr = this.directory;
            byte[][] bArr = this.segments;
            if (!byteString.rangeEquals(i3, bArr[a], (i2 - i5) + iArr[bArr.length + a], min)) {
                return false;
            }
            i2 += min;
            i3 += min;
            i4 -= min;
            a++;
        }
        return true;
    }

    public ByteString sha1() {
        return a().sha1();
    }

    public ByteString sha256() {
        return a().sha256();
    }

    public int size() {
        return this.directory[this.segments.length - 1];
    }

    public String string(Charset charset) {
        return a().string(charset);
    }

    public ByteString substring(int i2) {
        return a().substring(i2);
    }

    public ByteString toAsciiLowercase() {
        return a().toAsciiLowercase();
    }

    public ByteString toAsciiUppercase() {
        return a().toAsciiUppercase();
    }

    public byte[] toByteArray() {
        int[] iArr = this.directory;
        byte[][] bArr = this.segments;
        byte[] bArr2 = new byte[iArr[bArr.length - 1]];
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr2 = this.directory;
            int i4 = iArr2[length + i2];
            int i5 = iArr2[i2];
            System.arraycopy(this.segments[i2], i4, bArr2, i3, i5 - i3);
            i2++;
            i3 = i5;
        }
        return bArr2;
    }

    public String toString() {
        return a().toString();
    }

    public String utf8() {
        return a().utf8();
    }

    public void write(OutputStream outputStream) {
        if (outputStream != null) {
            int length = this.segments.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                int[] iArr = this.directory;
                int i4 = iArr[length + i2];
                int i5 = iArr[i2];
                outputStream.write(this.segments[i2], i4, i5 - i3);
                i2++;
                i3 = i5;
            }
            return;
        }
        throw new IllegalArgumentException("out == null");
    }

    private ByteString a() {
        return new ByteString(toByteArray());
    }

    public ByteString substring(int i2, int i3) {
        return a().substring(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public void write(c cVar) {
        int length = this.segments.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int[] iArr = this.directory;
            int i4 = iArr[length + i2];
            int i5 = iArr[i2];
            o oVar = new o(this.segments[i2], i4, (i4 + i5) - i3, true, false);
            o oVar2 = cVar.e;
            if (oVar2 == null) {
                oVar.f2110g = oVar;
                oVar.f2109f = oVar;
                cVar.e = oVar;
            } else {
                oVar2.f2110g.a(oVar);
            }
            i2++;
            i3 = i5;
        }
        cVar.f2094f += (long) i3;
    }

    public boolean rangeEquals(int i2, byte[] bArr, int i3, int i4) {
        int i5;
        if (i2 < 0 || i2 > size() - i4 || i3 < 0 || i3 > bArr.length - i4) {
            return false;
        }
        int a = a(i2);
        while (i4 > 0) {
            if (a == 0) {
                i5 = 0;
            } else {
                i5 = this.directory[a - 1];
            }
            int min = Math.min(i4, ((this.directory[a] - i5) + i5) - i2);
            int[] iArr = this.directory;
            byte[][] bArr2 = this.segments;
            if (!t.a(bArr2[a], (i2 - i5) + iArr[bArr2.length + a], bArr, i3, min)) {
                return false;
            }
            i2 += min;
            i3 += min;
            i4 -= min;
            a++;
        }
        return true;
    }
}
