package okhttp3;

import okhttp3.k0.e;
import okio.ByteString;
import okio.d;

/* compiled from: RequestBody */
public abstract class g0 {

    /* compiled from: RequestBody */
    class a extends g0 {
        final /* synthetic */ b0 a;
        final /* synthetic */ ByteString b;

        a(b0 b0Var, ByteString byteString) {
            this.a = b0Var;
            this.b = byteString;
        }

        public long a() {
            return (long) this.b.size();
        }

        public b0 b() {
            return this.a;
        }

        public void a(d dVar) {
            dVar.a(this.b);
        }
    }

    /* compiled from: RequestBody */
    class b extends g0 {
        final /* synthetic */ b0 a;
        final /* synthetic */ int b;
        final /* synthetic */ byte[] c;
        final /* synthetic */ int d;

        b(b0 b0Var, int i2, byte[] bArr, int i3) {
            this.a = b0Var;
            this.b = i2;
            this.c = bArr;
            this.d = i3;
        }

        public long a() {
            return (long) this.b;
        }

        public b0 b() {
            return this.a;
        }

        public void a(d dVar) {
            dVar.write(this.c, this.d, this.b);
        }
    }

    public static g0 a(b0 b0Var, ByteString byteString) {
        return new a(b0Var, byteString);
    }

    public long a() {
        return -1;
    }

    public abstract void a(d dVar);

    public abstract b0 b();

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public static g0 a(b0 b0Var, byte[] bArr) {
        return a(b0Var, bArr, 0, bArr.length);
    }

    public static g0 a(b0 b0Var, byte[] bArr, int i2, int i3) {
        if (bArr != null) {
            e.a((long) bArr.length, (long) i2, (long) i3);
            return new b(b0Var, i3, bArr, i2);
        }
        throw new NullPointerException("content == null");
    }
}
