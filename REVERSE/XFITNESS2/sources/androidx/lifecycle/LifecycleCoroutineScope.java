package androidx.lifecycle;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.k1;

/* compiled from: Lifecycle.kt */
public abstract class LifecycleCoroutineScope implements g0 {
    public abstract Lifecycle getLifecycle$lifecycle_runtime_ktx_release();

    public final k1 launchWhenCreated(p<? super g0, ? super c<? super l>, ? extends Object> pVar) {
        i.b(pVar, "block");
        return e.a(this, (CoroutineContext) null, (CoroutineStart) null, new LifecycleCoroutineScope$launchWhenCreated$1(this, pVar, (c) null), 3, (Object) null);
    }

    public final k1 launchWhenResumed(p<? super g0, ? super c<? super l>, ? extends Object> pVar) {
        i.b(pVar, "block");
        return e.a(this, (CoroutineContext) null, (CoroutineStart) null, new LifecycleCoroutineScope$launchWhenResumed$1(this, pVar, (c) null), 3, (Object) null);
    }

    public final k1 launchWhenStarted(p<? super g0, ? super c<? super l>, ? extends Object> pVar) {
        i.b(pVar, "block");
        return e.a(this, (CoroutineContext) null, (CoroutineStart) null, new LifecycleCoroutineScope$launchWhenStarted$1(this, pVar, (c) null), 3, (Object) null);
    }
}
