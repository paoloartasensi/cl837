package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import okio.c;
import okio.e;

/* compiled from: ResponseBody */
public abstract class i0 implements Closeable {
    private Reader e;

    /* compiled from: ResponseBody */
    class a extends i0 {

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ b0 f1898f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ long f1899g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ e f1900h;

        a(b0 b0Var, long j2, e eVar) {
            this.f1898f = b0Var;
            this.f1899g = j2;
            this.f1900h = eVar;
        }

        public long c() {
            return this.f1899g;
        }

        public b0 j() {
            return this.f1898f;
        }

        public e m() {
            return this.f1900h;
        }
    }

    /* compiled from: ResponseBody */
    static final class b extends Reader {
        private final e e;

        /* renamed from: f  reason: collision with root package name */
        private final Charset f1901f;

        /* renamed from: g  reason: collision with root package name */
        private boolean f1902g;

        /* renamed from: h  reason: collision with root package name */
        private Reader f1903h;

        b(e eVar, Charset charset) {
            this.e = eVar;
            this.f1901f = charset;
        }

        public void close() {
            this.f1902g = true;
            Reader reader = this.f1903h;
            if (reader != null) {
                reader.close();
            } else {
                this.e.close();
            }
        }

        public int read(char[] cArr, int i2, int i3) {
            if (!this.f1902g) {
                Reader reader = this.f1903h;
                if (reader == null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(this.e.l(), okhttp3.k0.e.a(this.e, this.f1901f));
                    this.f1903h = inputStreamReader;
                    reader = inputStreamReader;
                }
                return reader.read(cArr, i2, i3);
            }
            throw new IOException("Stream closed");
        }
    }

    private Charset n() {
        b0 j2 = j();
        return j2 != null ? j2.a(StandardCharsets.UTF_8) : StandardCharsets.UTF_8;
    }

    public final Reader a() {
        Reader reader = this.e;
        if (reader != null) {
            return reader;
        }
        b bVar = new b(m(), n());
        this.e = bVar;
        return bVar;
    }

    public abstract long c();

    public void close() {
        okhttp3.k0.e.a((Closeable) m());
    }

    public abstract b0 j();

    public abstract e m();

    public static i0 a(b0 b0Var, byte[] bArr) {
        c cVar = new c();
        cVar.write(bArr);
        return a(b0Var, (long) bArr.length, cVar);
    }

    public static i0 a(b0 b0Var, long j2, e eVar) {
        if (eVar != null) {
            return new a(b0Var, j2, eVar);
        }
        throw new NullPointerException("source == null");
    }
}
