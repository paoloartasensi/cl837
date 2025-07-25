package androidx.lifecycle;

import android.annotation.SuppressLint;
import java.util.ArrayDeque;
import java.util.Queue;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.t1;
import kotlinx.coroutines.u0;

/* compiled from: DispatchQueue.kt */
public final class DispatchQueue {
    private boolean finished;
    private boolean isDraining;
    private boolean paused = true;
    private final Queue<Runnable> queue = new ArrayDeque();

    private final boolean canRun() {
        return this.finished || !this.paused;
    }

    /* access modifiers changed from: private */
    public final void enqueue(Runnable runnable) {
        if (this.queue.offer(runnable)) {
            drainQueue();
            return;
        }
        throw new IllegalStateException("cannot enqueue any more runnables".toString());
    }

    public final void drainQueue() {
        if (!this.isDraining) {
            boolean z = false;
            z = true;
            try {
                while (true) {
                    if (!(this.queue.isEmpty() ^ z)) {
                        break;
                    } else if (!canRun()) {
                        break;
                    } else {
                        Runnable poll = this.queue.poll();
                        if (poll != null) {
                            poll.run();
                        }
                    }
                }
                this.isDraining = z;
            } finally {
                this.isDraining = z;
            }
        }
    }

    public final void finish() {
        this.finished = true;
        drainQueue();
    }

    public final void pause() {
        this.paused = true;
    }

    public final void resume() {
        if (this.paused) {
            if (!this.finished) {
                this.paused = false;
                drainQueue();
                return;
            }
            throw new IllegalStateException("Cannot resume a finished dispatcher".toString());
        }
    }

    @SuppressLint({"WrongThread"})
    public final void runOrEnqueue(Runnable runnable) {
        i.b(runnable, "runnable");
        t1 n = u0.b().n();
        if (n.isDispatchNeeded(EmptyCoroutineContext.INSTANCE)) {
            n.dispatch(EmptyCoroutineContext.INSTANCE, new DispatchQueue$runOrEnqueue$$inlined$with$lambda$1(this, runnable));
        } else {
            enqueue(runnable);
        }
    }
}
