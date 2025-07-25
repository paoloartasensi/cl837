package okhttp3;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import okhttp3.k0.e;
import okio.c;
import okio.d;

/* compiled from: FormBody */
public final class w extends g0 {
    private static final b0 c = b0.a("application/x-www-form-urlencoded");
    private final List<String> a;
    private final List<String> b;

    /* compiled from: FormBody */
    public static final class a {
        private final List<String> a;
        private final List<String> b;
        private final Charset c;

        public a() {
            this((Charset) null);
        }

        public a a(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            } else if (str2 != null) {
                this.a.add(z.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.c));
                this.b.add(z.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true, this.c));
                return this;
            } else {
                throw new NullPointerException("value == null");
            }
        }

        public a b(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            } else if (str2 != null) {
                this.a.add(z.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.c));
                this.b.add(z.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true, this.c));
                return this;
            } else {
                throw new NullPointerException("value == null");
            }
        }

        public a(Charset charset) {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = charset;
        }

        public w a() {
            return new w(this.a, this.b);
        }
    }

    w(List<String> list, List<String> list2) {
        this.a = e.a(list);
        this.b = e.a(list2);
    }

    public long a() {
        return a((d) null, true);
    }

    public b0 b() {
        return c;
    }

    public void a(d dVar) {
        a(dVar, false);
    }

    private long a(d dVar, boolean z) {
        c cVar;
        if (z) {
            cVar = new c();
        } else {
            cVar = dVar.b();
        }
        int size = this.a.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                cVar.writeByte(38);
            }
            cVar.a(this.a.get(i2));
            cVar.writeByte(61);
            cVar.a(this.b.get(i2));
        }
        if (!z) {
            return 0;
        }
        long r = cVar.r();
        cVar.j();
        return r;
    }
}
