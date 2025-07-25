package androidx.navigation;

import android.os.Bundle;

/* compiled from: NavAction */
public final class d {
    private final int a;
    private o b;
    private Bundle c;

    public d(int i2) {
        this(i2, (o) null);
    }

    public void a(o oVar) {
        this.b = oVar;
    }

    public int b() {
        return this.a;
    }

    public o c() {
        return this.b;
    }

    public d(int i2, o oVar) {
        this(i2, oVar, (Bundle) null);
    }

    public Bundle a() {
        return this.c;
    }

    public d(int i2, o oVar, Bundle bundle) {
        this.a = i2;
        this.b = oVar;
        this.c = bundle;
    }

    public void a(Bundle bundle) {
        this.c = bundle;
    }
}
