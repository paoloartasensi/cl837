package androidx.transition;

import android.os.IBinder;

/* compiled from: WindowIdApi14 */
class i0 implements k0 {
    private final IBinder a;

    i0(IBinder iBinder) {
        this.a = iBinder;
    }

    public boolean equals(Object obj) {
        return (obj instanceof i0) && ((i0) obj).a.equals(this.a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
