package androidx.fragment.app;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

/* compiled from: FragmentViewLifecycleOwner */
class t implements LifecycleOwner {
    private LifecycleRegistry e = null;

    t() {
    }

    /* access modifiers changed from: package-private */
    public void a(Lifecycle.Event event) {
        this.e.handleLifecycleEvent(event);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (this.e == null) {
            this.e = new LifecycleRegistry(this);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return this.e != null;
    }

    public Lifecycle getLifecycle() {
        c();
        return this.e;
    }
}
