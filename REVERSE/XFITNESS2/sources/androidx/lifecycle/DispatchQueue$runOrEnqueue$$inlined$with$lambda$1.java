package androidx.lifecycle;

/* compiled from: DispatchQueue.kt */
final class DispatchQueue$runOrEnqueue$$inlined$with$lambda$1 implements Runnable {
    final /* synthetic */ Runnable $runnable$inlined;
    final /* synthetic */ DispatchQueue this$0;

    DispatchQueue$runOrEnqueue$$inlined$with$lambda$1(DispatchQueue dispatchQueue, Runnable runnable) {
        this.this$0 = dispatchQueue;
        this.$runnable$inlined = runnable;
    }

    public final void run() {
        this.this$0.enqueue(this.$runnable$inlined);
    }
}
