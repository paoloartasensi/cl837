package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.k1;

/* compiled from: LifecycleController.kt */
final class LifecycleController$observer$1 implements LifecycleEventObserver {
    final /* synthetic */ k1 $parentJob;
    final /* synthetic */ LifecycleController this$0;

    LifecycleController$observer$1(LifecycleController lifecycleController, k1 k1Var) {
        this.this$0 = lifecycleController;
        this.$parentJob = k1Var;
    }

    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        i.b(lifecycleOwner, "source");
        i.b(event, "<anonymous parameter 1>");
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        i.a((Object) lifecycle, "source.lifecycle");
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            LifecycleController lifecycleController = this.this$0;
            k1.a.a(this.$parentJob, (CancellationException) null, 1, (Object) null);
            lifecycleController.finish();
            return;
        }
        Lifecycle lifecycle2 = lifecycleOwner.getLifecycle();
        i.a((Object) lifecycle2, "source.lifecycle");
        if (lifecycle2.getCurrentState().compareTo(this.this$0.minState) < 0) {
            this.this$0.dispatchQueue.pause();
        } else {
            this.this$0.dispatchQueue.resume();
        }
    }
}
