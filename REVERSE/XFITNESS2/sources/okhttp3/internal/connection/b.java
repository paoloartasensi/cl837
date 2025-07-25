package okhttp3.internal.connection;

import okhttp3.a0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.k0.h.g;

/* compiled from: ConnectInterceptor */
public final class b implements a0 {
    public b(d0 d0Var) {
    }

    public h0 a(a0.a aVar) {
        g gVar = (g) aVar;
        f0 a = gVar.a();
        j f2 = gVar.f();
        return gVar.a(a, f2, f2.a(aVar, !a.e().equals("GET")));
    }
}
