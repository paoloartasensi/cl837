package okio;

import java.util.concurrent.TimeUnit;

/* compiled from: ForwardingTimeout */
public class h extends s {
    private s e;

    public h(s sVar) {
        if (sVar != null) {
            this.e = sVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public final h a(s sVar) {
        if (sVar != null) {
            this.e = sVar;
            return this;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public s b() {
        return this.e.b();
    }

    public long c() {
        return this.e.c();
    }

    public boolean d() {
        return this.e.d();
    }

    public void e() {
        this.e.e();
    }

    public long f() {
        return this.e.f();
    }

    public final s g() {
        return this.e;
    }

    public s a(long j2, TimeUnit timeUnit) {
        return this.e.a(j2, timeUnit);
    }

    public s a(long j2) {
        return this.e.a(j2);
    }

    public s a() {
        return this.e.a();
    }
}
