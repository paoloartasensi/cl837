package org.koin.core.definition;

/* compiled from: Options.kt */
public final class c {
    private boolean a;
    private boolean b;

    public c() {
        this(false, false, 3, (f) null);
    }

    public c(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
    }

    public final boolean a() {
        return this.b;
    }

    public final boolean b() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof c) {
                c cVar = (c) obj;
                if (this.a == cVar.a) {
                    if (this.b == cVar.b) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        boolean z = this.a;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i2 = (z ? 1 : 0) * true;
        boolean z3 = this.b;
        if (!z3) {
            z2 = z3;
        }
        return i2 + (z2 ? 1 : 0);
    }

    public String toString() {
        return "Options(isCreatedAtStart=" + this.a + ", override=" + this.b + ")";
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(boolean z, boolean z2, int i2, f fVar) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? false : z2);
    }

    public final void a(boolean z) {
        this.a = z;
    }

    public final void b(boolean z) {
        this.b = z;
    }
}
