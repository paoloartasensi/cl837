package androidx.transition;

import android.view.View;
import android.view.WindowId;

/* compiled from: WindowIdApi18 */
class j0 implements k0 {
    private final WindowId a;

    j0(View view) {
        this.a = view.getWindowId();
    }

    public boolean equals(Object obj) {
        return (obj instanceof j0) && ((j0) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
