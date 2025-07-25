package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.d;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.k1;

@c(c = "androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2", f = "PausingDispatcher.kt", l = {163}, m = "invokeSuspend")
/* compiled from: PausingDispatcher.kt */
final class PausingDispatcherKt$whenStateAtLeast$2 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super T>, Object> {
    final /* synthetic */ p $block;
    final /* synthetic */ Lifecycle.State $minState;
    final /* synthetic */ Lifecycle $this_whenStateAtLeast;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    private g0 p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PausingDispatcherKt$whenStateAtLeast$2(Lifecycle lifecycle, Lifecycle.State state, p pVar, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.$this_whenStateAtLeast = lifecycle;
        this.$minState = state;
        this.$block = pVar;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        PausingDispatcherKt$whenStateAtLeast$2 pausingDispatcherKt$whenStateAtLeast$2 = new PausingDispatcherKt$whenStateAtLeast$2(this.$this_whenStateAtLeast, this.$minState, this.$block, cVar);
        pausingDispatcherKt$whenStateAtLeast$2.p$ = (g0) obj;
        return pausingDispatcherKt$whenStateAtLeast$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((PausingDispatcherKt$whenStateAtLeast$2) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        LifecycleController lifecycleController;
        Object a = b.a();
        int i2 = this.label;
        if (i2 == 0) {
            kotlin.i.a(obj);
            g0 g0Var = this.p$;
            k1 k1Var = (k1) g0Var.getCoroutineContext().get(k1.d);
            if (k1Var != null) {
                PausingDispatcher pausingDispatcher = new PausingDispatcher();
                LifecycleController lifecycleController2 = new LifecycleController(this.$this_whenStateAtLeast, this.$minState, pausingDispatcher.dispatchQueue, k1Var);
                try {
                    p pVar = this.$block;
                    this.L$0 = g0Var;
                    this.L$1 = k1Var;
                    this.L$2 = pausingDispatcher;
                    this.L$3 = lifecycleController2;
                    this.label = 1;
                    obj = d.a(pausingDispatcher, pVar, this);
                    if (obj == a) {
                        return a;
                    }
                    lifecycleController = lifecycleController2;
                } catch (Throwable th) {
                    th = th;
                    lifecycleController = lifecycleController2;
                    lifecycleController.finish();
                    throw th;
                }
            } else {
                throw new IllegalStateException("when[State] methods should have a parent job".toString());
            }
        } else if (i2 == 1) {
            lifecycleController = (LifecycleController) this.L$3;
            PausingDispatcher pausingDispatcher2 = (PausingDispatcher) this.L$2;
            k1 k1Var2 = (k1) this.L$1;
            g0 g0Var2 = (g0) this.L$0;
            try {
                kotlin.i.a(obj);
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        lifecycleController.finish();
        return obj;
    }
}
