package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.d;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.u0;

/* compiled from: PausingDispatcher.kt */
public final class PausingDispatcherKt {
    public static final <T> Object whenCreated(LifecycleOwner lifecycleOwner, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        i.a((Object) lifecycle, "lifecycle");
        return whenCreated(lifecycle, pVar, cVar);
    }

    public static final <T> Object whenResumed(LifecycleOwner lifecycleOwner, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        i.a((Object) lifecycle, "lifecycle");
        return whenResumed(lifecycle, pVar, cVar);
    }

    public static final <T> Object whenStarted(LifecycleOwner lifecycleOwner, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        i.a((Object) lifecycle, "lifecycle");
        return whenStarted(lifecycle, pVar, cVar);
    }

    public static final <T> Object whenStateAtLeast(Lifecycle lifecycle, Lifecycle.State state, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        return d.a(u0.b().n(), new PausingDispatcherKt$whenStateAtLeast$2(lifecycle, state, pVar, (c) null), cVar);
    }

    public static final <T> Object whenCreated(Lifecycle lifecycle, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.CREATED, pVar, cVar);
    }

    public static final <T> Object whenResumed(Lifecycle lifecycle, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.RESUMED, pVar, cVar);
    }

    public static final <T> Object whenStarted(Lifecycle lifecycle, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        return whenStateAtLeast(lifecycle, Lifecycle.State.STARTED, pVar, cVar);
    }
}
