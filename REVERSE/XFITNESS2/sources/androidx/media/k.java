package androidx.media;

import android.text.TextUtils;
import androidx.core.g.c;

/* compiled from: MediaSessionManagerImplBase */
class k implements i {
    private String a;
    private int b;
    private int c;

    k(String str, int i2, int i3) {
        this.a = str;
        this.b = i2;
        this.c = i3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof k)) {
            return false;
        }
        k kVar = (k) obj;
        if (TextUtils.equals(this.a, kVar.a) && this.b == kVar.b && this.c == kVar.c) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return c.a(this.a, Integer.valueOf(this.b), Integer.valueOf(this.c));
    }
}
