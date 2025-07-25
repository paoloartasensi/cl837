package com.chileaf.fitness.base;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.g0;

@c(c = "com.chileaf.fitness.base.BaseActivity$hideLoading$1", f = "BaseActivity.kt", l = {}, m = "invokeSuspend")
/* compiled from: BaseActivity.kt */
final class BaseActivity$hideLoading$1 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
    int label;
    private g0 p$;
    final /* synthetic */ BaseActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BaseActivity$hideLoading$1(BaseActivity baseActivity, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.this$0 = baseActivity;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        BaseActivity$hideLoading$1 baseActivity$hideLoading$1 = new BaseActivity$hideLoading$1(this.this$0, cVar);
        baseActivity$hideLoading$1.p$ = (g0) obj;
        return baseActivity$hideLoading$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((BaseActivity$hideLoading$1) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        Object unused = b.a();
        if (this.label == 0) {
            kotlin.i.a(obj);
            if (this.this$0.q().isShowing() && !this.this$0.isFinishing()) {
                this.this$0.q().dismiss();
            }
            return l.a;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
