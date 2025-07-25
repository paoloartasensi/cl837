package androidx.transition;

import android.os.Build;
import android.view.ViewGroup;

/* compiled from: ViewGroupUtils */
class w {
    static v a(ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new u(viewGroup);
        }
        return t.a(viewGroup);
    }

    static void a(ViewGroup viewGroup, boolean z) {
        if (Build.VERSION.SDK_INT >= 18) {
            y.a(viewGroup, z);
        } else {
            x.a(viewGroup, z);
        }
    }
}
