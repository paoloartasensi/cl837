package okhttp3.k0.h;

import java.util.List;
import okhttp3.a0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.internal.connection.d;
import okhttp3.internal.connection.j;

/* compiled from: RealInterceptorChain */
public final class g implements a0.a {
    private final List<a0> a;
    private final j b;
    private final d c;
    private final int d;
    private final f0 e;

    /* renamed from: f  reason: collision with root package name */
    private final okhttp3.j f2036f;

    /* renamed from: g  reason: collision with root package name */
    private final int f2037g;

    /* renamed from: h  reason: collision with root package name */
    private final int f2038h;

    /* renamed from: i  reason: collision with root package name */
    private final int f2039i;

    /* renamed from: j  reason: collision with root package name */
    private int f2040j;

    public g(List<a0> list, j jVar, d dVar, int i2, f0 f0Var, okhttp3.j jVar2, int i3, int i4, int i5) {
        this.a = list;
        this.b = jVar;
        this.c = dVar;
        this.d = i2;
        this.e = f0Var;
        this.f2036f = jVar2;
        this.f2037g = i3;
        this.f2038h = i4;
        this.f2039i = i5;
    }

    public f0 a() {
        return this.e;
    }

    public int b() {
        return this.f2039i;
    }

    public int c() {
        return this.f2037g;
    }

    public int d() {
        return this.f2038h;
    }

    public d e() {
        d dVar = this.c;
        if (dVar != null) {
            return dVar;
        }
        throw new IllegalStateException();
    }

    public j f() {
        return this.b;
    }

    public h0 a(f0 f0Var) {
        return a(f0Var, this.b, this.c);
    }

    public h0 a(f0 f0Var, j jVar, d dVar) {
        if (this.d < this.a.size()) {
            this.f2040j++;
            d dVar2 = this.c;
            if (dVar2 != null && !dVar2.b().a(f0Var.g())) {
                throw new IllegalStateException("network interceptor " + this.a.get(this.d - 1) + " must retain the same host and port");
            } else if (this.c == null || this.f2040j <= 1) {
                g gVar = new g(this.a, jVar, dVar, this.d + 1, f0Var, this.f2036f, this.f2037g, this.f2038h, this.f2039i);
                a0 a0Var = this.a.get(this.d);
                h0 a2 = a0Var.a(gVar);
                if (dVar != null && this.d + 1 < this.a.size() && gVar.f2040j != 1) {
                    throw new IllegalStateException("network interceptor " + a0Var + " must call proceed() exactly once");
                } else if (a2 == null) {
                    throw new NullPointerException("interceptor " + a0Var + " returned null");
                } else if (a2.a() != null) {
                    return a2;
                } else {
                    throw new IllegalStateException("interceptor " + a0Var + " returned a response with no body");
                }
            } else {
                throw new IllegalStateException("network interceptor " + this.a.get(this.d - 1) + " must call proceed() exactly once");
            }
        } else {
            throw new AssertionError();
        }
    }
}
