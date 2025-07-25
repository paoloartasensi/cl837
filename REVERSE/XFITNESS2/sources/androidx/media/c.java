package androidx.media;

import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.Arrays;

/* compiled from: AudioAttributesImplBase */
class c implements a {
    int a = 0;
    int b = 0;
    int c = 0;
    int d = -1;

    c() {
    }

    public int a() {
        return this.b;
    }

    public int b() {
        int i2 = this.c;
        int c2 = c();
        if (c2 == 6) {
            i2 |= 4;
        } else if (c2 == 7) {
            i2 |= 1;
        }
        return i2 & BaseQuickAdapter.HEADER_VIEW;
    }

    public int c() {
        int i2 = this.d;
        if (i2 != -1) {
            return i2;
        }
        return AudioAttributesCompat.a(false, this.c, this.a);
    }

    public int d() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        if (this.b == cVar.a() && this.c == cVar.b() && this.a == cVar.d() && this.d == cVar.d) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.a), Integer.valueOf(this.d)});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.d != -1) {
            sb.append(" stream=");
            sb.append(this.d);
            sb.append(" derived");
        }
        sb.append(" usage=");
        sb.append(AudioAttributesCompat.a(this.a));
        sb.append(" content=");
        sb.append(this.b);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.c).toUpperCase());
        return sb.toString();
    }
}
