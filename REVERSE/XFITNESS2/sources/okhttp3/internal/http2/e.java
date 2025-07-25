package okhttp3.internal.http2;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.internal.connection.f;
import okhttp3.k0.h.c;
import okhttp3.k0.h.i;
import okhttp3.k0.h.k;
import okhttp3.y;
import okio.q;
import okio.r;

/* compiled from: Http2ExchangeCodec */
public final class e implements c {

    /* renamed from: g  reason: collision with root package name */
    private static final List<String> f1989g = okhttp3.k0.e.a((T[]) new String[]{"connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade", ":method", ":path", ":scheme", ":authority"});

    /* renamed from: h  reason: collision with root package name */
    private static final List<String> f1990h = okhttp3.k0.e.a((T[]) new String[]{"connection", "host", "keep-alive", "proxy-connection", "te", "transfer-encoding", "encoding", "upgrade"});
    private final a0.a a;
    private final f b;
    private final d c;
    private volatile g d;
    private final Protocol e;

    /* renamed from: f  reason: collision with root package name */
    private volatile boolean f1991f;

    public e(d0 d0Var, f fVar, a0.a aVar, d dVar) {
        Protocol protocol;
        this.b = fVar;
        this.a = aVar;
        this.c = dVar;
        if (d0Var.t().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            protocol = Protocol.H2_PRIOR_KNOWLEDGE;
        } else {
            protocol = Protocol.HTTP_2;
        }
        this.e = protocol;
    }

    public q a(f0 f0Var, long j2) {
        return this.d.d();
    }

    public void b() {
        this.c.flush();
    }

    public f c() {
        return this.b;
    }

    public void cancel() {
        this.f1991f = true;
        if (this.d != null) {
            this.d.a(ErrorCode.CANCEL);
        }
    }

    public static List<a> b(f0 f0Var) {
        y c2 = f0Var.c();
        ArrayList arrayList = new ArrayList(c2.b() + 4);
        arrayList.add(new a(a.f1935f, f0Var.e()));
        arrayList.add(new a(a.f1936g, i.a(f0Var.g())));
        String a2 = f0Var.a("Host");
        if (a2 != null) {
            arrayList.add(new a(a.f1938i, a2));
        }
        arrayList.add(new a(a.f1937h, f0Var.g().n()));
        int b2 = c2.b();
        for (int i2 = 0; i2 < b2; i2++) {
            String lowerCase = c2.a(i2).toLowerCase(Locale.US);
            if (!f1989g.contains(lowerCase) || (lowerCase.equals("te") && c2.b(i2).equals("trailers"))) {
                arrayList.add(new a(lowerCase, c2.b(i2)));
            }
        }
        return arrayList;
    }

    public void a(f0 f0Var) {
        if (this.d == null) {
            this.d = this.c.a(b(f0Var), f0Var.a() != null);
            if (!this.f1991f) {
                this.d.h().a((long) this.a.d(), TimeUnit.MILLISECONDS);
                this.d.k().a((long) this.a.b(), TimeUnit.MILLISECONDS);
                return;
            }
            this.d.a(ErrorCode.CANCEL);
            throw new IOException("Canceled");
        }
    }

    public void a() {
        this.d.d().close();
    }

    public h0.a a(boolean z) {
        h0.a a2 = a(this.d.i(), this.e);
        if (!z || okhttp3.k0.c.a.a(a2) != 100) {
            return a2;
        }
        return null;
    }

    public r b(h0 h0Var) {
        return this.d.e();
    }

    public static h0.a a(y yVar, Protocol protocol) {
        y.a aVar = new y.a();
        int b2 = yVar.b();
        k kVar = null;
        for (int i2 = 0; i2 < b2; i2++) {
            String a2 = yVar.a(i2);
            String b3 = yVar.b(i2);
            if (a2.equals(":status")) {
                kVar = k.a("HTTP/1.1 " + b3);
            } else if (!f1990h.contains(a2)) {
                okhttp3.k0.c.a.a(aVar, a2, b3);
            }
        }
        if (kVar != null) {
            h0.a aVar2 = new h0.a();
            aVar2.a(protocol);
            aVar2.a(kVar.b);
            aVar2.a(kVar.c);
            aVar2.a(aVar.a());
            return aVar2;
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public long a(h0 h0Var) {
        return okhttp3.k0.h.e.a(h0Var);
    }
}
