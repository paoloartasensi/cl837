package okhttp3.k0.h;

import java.util.List;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.f0;
import okhttp3.g0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.k0.e;
import okhttp3.k0.f;
import okhttp3.q;
import okhttp3.r;
import okhttp3.y;
import okio.i;
import okio.k;

/* compiled from: BridgeInterceptor */
public final class a implements a0 {
    private final r a;

    public a(r rVar) {
        this.a = rVar;
    }

    public h0 a(a0.a aVar) {
        f0 a2 = aVar.a();
        f0.a f2 = a2.f();
        g0 a3 = a2.a();
        if (a3 != null) {
            b0 b = a3.b();
            if (b != null) {
                f2.a("Content-Type", b.toString());
            }
            long a4 = a3.a();
            if (a4 != -1) {
                f2.a("Content-Length", Long.toString(a4));
                f2.a("Transfer-Encoding");
            } else {
                f2.a("Transfer-Encoding", "chunked");
                f2.a("Content-Length");
            }
        }
        boolean z = false;
        if (a2.a("Host") == null) {
            f2.a("Host", e.a(a2.g(), false));
        }
        if (a2.a("Connection") == null) {
            f2.a("Connection", "Keep-Alive");
        }
        if (a2.a("Accept-Encoding") == null && a2.a("Range") == null) {
            z = true;
            f2.a("Accept-Encoding", "gzip");
        }
        List<q> a5 = this.a.a(a2.g());
        if (!a5.isEmpty()) {
            f2.a("Cookie", a(a5));
        }
        if (a2.a("User-Agent") == null) {
            f2.a("User-Agent", f.a());
        }
        h0 a6 = aVar.a(f2.a());
        e.a(this.a, a2.g(), a6.n());
        h0.a q = a6.q();
        q.a(a2);
        if (z && "gzip".equalsIgnoreCase(a6.b("Content-Encoding")) && e.b(a6)) {
            i iVar = new i(a6.a().m());
            y.a a7 = a6.n().a();
            a7.b("Content-Encoding");
            a7.b("Content-Length");
            q.a(a7.a());
            q.a((i0) new h(a6.b("Content-Type"), -1, k.a((okio.r) iVar)));
        }
        return q.a();
    }

    private String a(List<q> list) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                sb.append("; ");
            }
            q qVar = list.get(i2);
            sb.append(qVar.a());
            sb.append('=');
            sb.append(qVar.b());
        }
        return sb.toString();
    }
}
