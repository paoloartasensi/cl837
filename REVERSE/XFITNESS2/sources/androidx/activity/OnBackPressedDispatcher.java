package androidx.activity;

import android.annotation.SuppressLint;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

public final class OnBackPressedDispatcher {
    private final Runnable a;
    final ArrayDeque<b> b = new ArrayDeque<>();

    private class LifecycleOnBackPressedCancellable implements LifecycleEventObserver, a {
        private final Lifecycle e;

        /* renamed from: f  reason: collision with root package name */
        private final b f20f;

        /* renamed from: g  reason: collision with root package name */
        private a f21g;

        LifecycleOnBackPressedCancellable(Lifecycle lifecycle, b bVar) {
            this.e = lifecycle;
            this.f20f = bVar;
            lifecycle.addObserver(this);
        }

        public void cancel() {
            this.e.removeObserver(this);
            this.f20f.b(this);
            a aVar = this.f21g;
            if (aVar != null) {
                aVar.cancel();
                this.f21g = null;
            }
        }

        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.f21g = OnBackPressedDispatcher.this.a(this.f20f);
            } else if (event == Lifecycle.Event.ON_STOP) {
                a aVar = this.f21g;
                if (aVar != null) {
                    aVar.cancel();
                }
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                cancel();
            }
        }
    }

    private class a implements a {
        private final b e;

        a(b bVar) {
            this.e = bVar;
        }

        public void cancel() {
            OnBackPressedDispatcher.this.b.remove(this.e);
            this.e.b(this);
        }
    }

    public OnBackPressedDispatcher(Runnable runnable) {
        this.a = runnable;
    }

    /* access modifiers changed from: package-private */
    public a a(b bVar) {
        this.b.add(bVar);
        a aVar = new a(bVar);
        bVar.a((a) aVar);
        return aVar;
    }

    @SuppressLint({"LambdaLast"})
    public void a(LifecycleOwner lifecycleOwner, b bVar) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() != Lifecycle.State.DESTROYED) {
            bVar.a((a) new LifecycleOnBackPressedCancellable(lifecycle, bVar));
        }
    }

    public void a() {
        Iterator<b> descendingIterator = this.b.descendingIterator();
        while (descendingIterator.hasNext()) {
            b next = descendingIterator.next();
            if (next.b()) {
                next.a();
                return;
            }
        }
        Runnable runnable = this.a;
        if (runnable != null) {
            runnable.run();
        }
    }
}
