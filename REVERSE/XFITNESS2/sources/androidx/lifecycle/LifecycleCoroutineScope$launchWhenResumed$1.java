package androidx.lifecycle;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.g0;

@c(c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenResumed$1", f = "Lifecycle.kt", l = {99}, m = "invokeSuspend")
/* compiled from: Lifecycle.kt */
final class LifecycleCoroutineScope$launchWhenResumed$1 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
    final /* synthetic */ p $block;
    Object L$0;
    int label;
    private g0 p$;
    final /* synthetic */ LifecycleCoroutineScope this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    LifecycleCoroutineScope$launchWhenResumed$1(LifecycleCoroutineScope lifecycleCoroutineScope, p pVar, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.this$0 = lifecycleCoroutineScope;
        this.$block = pVar;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        LifecycleCoroutineScope$launchWhenResumed$1 lifecycleCoroutineScope$launchWhenResumed$1 = new LifecycleCoroutineScope$launchWhenResumed$1(this.this$0, this.$block, cVar);
        lifecycleCoroutineScope$launchWhenResumed$1.p$ = (g0) obj;
        return lifecycleCoroutineScope$launchWhenResumed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((LifecycleCoroutineScope$launchWhenResumed$1) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        Object a = b.a();
        int i2 = this.label;
        if (i2 == 0) {
            kotlin.i.a(obj);
            g0 g0Var = this.p$;
            Lifecycle lifecycle$lifecycle_runtime_ktx_release = this.this$0.getLifecycle$lifecycle_runtime_ktx_release();
            p pVar = this.$block;
            this.L$0 = g0Var;
            this.label = 1;
            if (PausingDispatcherKt.whenResumed(lifecycle$lifecycle_runtime_ktx_release, pVar, this) == a) {
                return a;
            }
        } else if (i2 == 1) {
            g0 g0Var2 = (g0) this.L$0;
            kotlin.i.a(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return l.a;
    }
}
