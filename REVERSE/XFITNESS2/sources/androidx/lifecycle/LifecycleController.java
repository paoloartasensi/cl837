package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.k1;

/* compiled from: LifecycleController.kt */
public final class LifecycleController {
    /* access modifiers changed from: private */
    public final DispatchQueue dispatchQueue;
    private final Lifecycle lifecycle;
    /* access modifiers changed from: private */
    public final Lifecycle.State minState;
    private final LifecycleEventObserver observer;

    public LifecycleController(Lifecycle lifecycle2, Lifecycle.State state, DispatchQueue dispatchQueue2, k1 k1Var) {
        i.b(lifecycle2, "lifecycle");
        i.b(state, "minState");
        i.b(dispatchQueue2, "dispatchQueue");
        i.b(k1Var, "parentJob");
        this.lifecycle = lifecycle2;
        this.minState = state;
        this.dispatchQueue = dispatchQueue2;
        this.observer = new LifecycleController$observer$1(this, k1Var);
        if (this.lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            k1.a.a(k1Var, (CancellationException) null, 1, (Object) null);
            finish();
            return;
        }
        this.lifecycle.addObserver(this.observer);
    }

    /* access modifiers changed from: private */
    public final void handleDestroy(k1 k1Var) {
        k1.a.a(k1Var, (CancellationException) null, 1, (Object) null);
        finish();
    }

    public final void finish() {
        this.lifecycle.removeObserver(this.observer);
        this.dispatchQueue.finish();
    }
}
