package androidx.navigation;

import android.os.Bundle;

/* compiled from: NavArgument */
public final class e {
    private final p a;
    private final boolean b;
    private final boolean c;
    private final Object d;

    /* compiled from: NavArgument */
    public static final class a {
        private p<?> a;
        private boolean b = false;
        private Object c;
        private boolean d = false;

        public a a(p<?> pVar) {
            this.a = pVar;
            return this;
        }

        public a a(boolean z) {
            this.b = z;
            return this;
        }

        public a a(Object obj) {
            this.c = obj;
            this.d = true;
            return this;
        }

        public e a() {
            if (this.a == null) {
                this.a = p.a(this.c);
            }
            return new e(this.a, this.b, this.c, this.d);
        }
    }

    e(p<?> pVar, boolean z, Object obj, boolean z2) {
        if (!pVar.b() && z) {
            throw new IllegalArgumentException(pVar.a() + " does not allow nullable values");
        } else if (z || !z2 || obj != null) {
            this.a = pVar;
            this.b = z;
            this.d = obj;
            this.c = z2;
        } else {
            throw new IllegalArgumentException("Argument with type " + pVar.a() + " has null value but is not nullable.");
        }
    }

    public Object a() {
        return this.d;
    }

    public p<?> b() {
        return this.a;
    }

    public boolean c() {
        return this.c;
    }

    public boolean d() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || e.class != obj.getClass()) {
            return false;
        }
        e eVar = (e) obj;
        if (this.b != eVar.b || this.c != eVar.c || !this.a.equals(eVar.a)) {
            return false;
        }
        Object obj2 = this.d;
        Object obj3 = eVar.d;
        if (obj2 != null) {
            return obj2.equals(obj3);
        }
        if (obj3 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + (this.b ? 1 : 0)) * 31) + (this.c ? 1 : 0)) * 31;
        Object obj = this.d;
        return hashCode + (obj != null ? obj.hashCode() : 0);
    }

    /* access modifiers changed from: package-private */
    public void a(String str, Bundle bundle) {
        if (this.c) {
            this.a.a(bundle, str, this.d);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(String str, Bundle bundle) {
        if (!this.b && bundle.containsKey(str) && bundle.get(str) == null) {
            return false;
        }
        try {
            this.a.a(bundle, str);
            return true;
        } catch (ClassCastException unused) {
            return false;
        }
    }
}
