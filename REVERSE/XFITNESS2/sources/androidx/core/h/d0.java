package androidx.core.h;

import android.os.Build;
import android.view.WindowInsets;

/* compiled from: WindowInsetsCompat */
public class d0 {
    private final Object a;

    private d0(Object obj) {
        this.a = obj;
    }

    public d0 a() {
        if (Build.VERSION.SDK_INT >= 20) {
            return new d0(((WindowInsets) this.a).consumeSystemWindowInsets());
        }
        return null;
    }

    public int b() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public int c() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int d() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int e() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetTop();
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || d0.class != obj.getClass()) {
            return false;
        }
        Object obj2 = this.a;
        Object obj3 = ((d0) obj).a;
        if (obj2 != null) {
            return obj2.equals(obj3);
        }
        if (obj3 == null) {
            return true;
        }
        return false;
    }

    public boolean f() {
        if (Build.VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).hasSystemWindowInsets();
        }
        return false;
    }

    public boolean g() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).isConsumed();
        }
        return false;
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public d0 a(int i2, int i3, int i4, int i5) {
        if (Build.VERSION.SDK_INT >= 20) {
            return new d0(((WindowInsets) this.a).replaceSystemWindowInsets(i2, i3, i4, i5));
        }
        return null;
    }

    static d0 a(Object obj) {
        if (obj == null) {
            return null;
        }
        return new d0(obj);
    }

    static Object a(d0 d0Var) {
        if (d0Var == null) {
            return null;
        }
        return d0Var.a;
    }
}
