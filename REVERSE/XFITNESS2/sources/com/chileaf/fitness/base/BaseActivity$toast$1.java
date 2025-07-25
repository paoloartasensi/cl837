package com.chileaf.fitness.base;

import android.view.View;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.g0;

@c(c = "com.chileaf.fitness.base.BaseActivity$toast$1", f = "BaseActivity.kt", l = {}, m = "invokeSuspend")
/* compiled from: BaseActivity.kt */
final class BaseActivity$toast$1 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
    final /* synthetic */ int $duration;
    final /* synthetic */ CharSequence $message;
    int label;
    private g0 p$;
    final /* synthetic */ BaseActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BaseActivity$toast$1(BaseActivity baseActivity, CharSequence charSequence, int i2, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.this$0 = baseActivity;
        this.$message = charSequence;
        this.$duration = i2;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        BaseActivity$toast$1 baseActivity$toast$1 = new BaseActivity$toast$1(this.this$0, this.$message, this.$duration, cVar);
        baseActivity$toast$1.p$ = (g0) obj;
        return baseActivity$toast$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((BaseActivity$toast$1) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        Object unused = b.a();
        if (this.label == 0) {
            kotlin.i.a(obj);
            View view = this.this$0.r().getView();
            if (view != null && view.isShown()) {
                this.this$0.r().cancel();
            }
            CharSequence charSequence = this.$message;
            if (charSequence != null) {
                this.this$0.b(charSequence, this.$duration);
            }
            return l.a;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
