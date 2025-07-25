package okhttp3.k0.h;

import java.net.ProtocolException;
import okhttp3.a0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.internal.connection.d;
import okhttp3.k0.e;
import okio.k;

/* compiled from: CallServerInterceptor */
public final class b implements a0 {
    private final boolean a;

    public b(boolean z) {
        this.a = z;
    }

    public h0 a(a0.a aVar) {
        boolean z;
        h0 h0Var;
        g gVar = (g) aVar;
        d e = gVar.e();
        f0 a2 = gVar.a();
        long currentTimeMillis = System.currentTimeMillis();
        e.a(a2);
        h0.a aVar2 = null;
        if (!f.b(a2.e()) || a2.a() == null) {
            e.h();
            z = false;
        } else {
            if ("100-continue".equalsIgnoreCase(a2.a("Expect"))) {
                e.e();
                e.i();
                aVar2 = e.a(true);
                z = true;
            } else {
                z = false;
            }
            if (aVar2 != null) {
                e.h();
                if (!e.b().c()) {
                    e.g();
                }
            } else if (a2.a().c()) {
                e.e();
                a2.a().a(k.a(e.a(a2, true)));
            } else {
                okio.d a3 = k.a(e.a(a2, false));
                a2.a().a(a3);
                a3.close();
            }
        }
        if (a2.a() == null || !a2.a().c()) {
            e.d();
        }
        if (!z) {
            e.i();
        }
        if (aVar2 == null) {
            aVar2 = e.a(false);
        }
        aVar2.a(a2);
        aVar2.a(e.b().b());
        aVar2.b(currentTimeMillis);
        aVar2.a(System.currentTimeMillis());
        h0 a4 = aVar2.a();
        int j2 = a4.j();
        if (j2 == 100) {
            h0.a a5 = e.a(false);
            a5.a(a2);
            a5.a(e.b().b());
            a5.b(currentTimeMillis);
            a5.a(System.currentTimeMillis());
            a4 = a5.a();
            j2 = a4.j();
        }
        e.b(a4);
        if (!this.a || j2 != 101) {
            h0.a q = a4.q();
            q.a(e.a(a4));
            h0Var = q.a();
        } else {
            h0.a q2 = a4.q();
            q2.a(e.d);
            h0Var = q2.a();
        }
        if ("close".equalsIgnoreCase(h0Var.t().a("Connection")) || "close".equalsIgnoreCase(h0Var.b("Connection"))) {
            e.g();
        }
        if ((j2 != 204 && j2 != 205) || h0Var.a().c() <= 0) {
            return h0Var;
        }
        throw new ProtocolException("HTTP " + j2 + " had non-zero Content-Length: " + h0Var.a().c());
    }
}
