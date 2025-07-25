package androidx.appcompat.d;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.h.a0;
import androidx.core.h.b0;
import androidx.core.h.z;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ViewPropertyAnimatorCompatSet */
public class h {
    final ArrayList<z> a = new ArrayList<>();
    private long b = -1;
    private Interpolator c;
    a0 d;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private final b0 f111f = new a();

    public h a(z zVar) {
        if (!this.e) {
            this.a.add(zVar);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.e = false;
    }

    public void c() {
        if (!this.e) {
            Iterator<z> it = this.a.iterator();
            while (it.hasNext()) {
                z next = it.next();
                long j2 = this.b;
                if (j2 >= 0) {
                    next.a(j2);
                }
                Interpolator interpolator = this.c;
                if (interpolator != null) {
                    next.a(interpolator);
                }
                if (this.d != null) {
                    next.a((a0) this.f111f);
                }
                next.c();
            }
            this.e = true;
        }
    }

    /* compiled from: ViewPropertyAnimatorCompatSet */
    class a extends b0 {
        private boolean a = false;
        private int b = 0;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.b = 0;
            this.a = false;
            h.this.b();
        }

        public void b(View view) {
            if (!this.a) {
                this.a = true;
                a0 a0Var = h.this.d;
                if (a0Var != null) {
                    a0Var.b((View) null);
                }
            }
        }

        public void a(View view) {
            int i2 = this.b + 1;
            this.b = i2;
            if (i2 == h.this.a.size()) {
                a0 a0Var = h.this.d;
                if (a0Var != null) {
                    a0Var.a((View) null);
                }
                a();
            }
        }
    }

    public h a(z zVar, z zVar2) {
        this.a.add(zVar);
        zVar2.b(zVar.b());
        this.a.add(zVar2);
        return this;
    }

    public void a() {
        if (this.e) {
            Iterator<z> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
            this.e = false;
        }
    }

    public h a(long j2) {
        if (!this.e) {
            this.b = j2;
        }
        return this;
    }

    public h a(Interpolator interpolator) {
        if (!this.e) {
            this.c = interpolator;
        }
        return this;
    }

    public h a(a0 a0Var) {
        if (!this.e) {
            this.d = a0Var;
        }
        return this;
    }
}
