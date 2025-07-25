package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;

/* compiled from: SavedStateRegistryController */
public final class a {
    private final b a;
    private final SavedStateRegistry b = new SavedStateRegistry();

    private a(b bVar) {
        this.a = bVar;
    }

    public SavedStateRegistry a() {
        return this.b;
    }

    public void b(Bundle bundle) {
        this.b.a(bundle);
    }

    public void a(Bundle bundle) {
        Lifecycle lifecycle = this.a.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.INITIALIZED) {
            lifecycle.addObserver(new Recreator(this.a));
            this.b.a(lifecycle, bundle);
            return;
        }
        throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
    }

    public static a a(b bVar) {
        return new a(bVar);
    }
}
