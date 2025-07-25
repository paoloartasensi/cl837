package com.chileaf.fitness.base;

import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.d;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.t1;
import kotlinx.coroutines.u0;

@c(c = "com.chileaf.fitness.base.BaseActivity$showLoading$2", f = "BaseActivity.kt", l = {132, 133}, m = "invokeSuspend")
/* compiled from: BaseActivity.kt */
final class BaseActivity$showLoading$2 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
    final /* synthetic */ long $delay;
    Object L$0;
    int label;
    private g0 p$;
    final /* synthetic */ BaseActivity this$0;

    @c(c = "com.chileaf.fitness.base.BaseActivity$showLoading$2$1", f = "BaseActivity.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.chileaf.fitness.base.BaseActivity$showLoading$2$1  reason: invalid class name */
    /* compiled from: BaseActivity.kt */
    static final class AnonymousClass1 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
        int label;
        private g0 p$;
        final /* synthetic */ BaseActivity$showLoading$2 this$0;

        {
            this.this$0 = r1;
        }

        public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
            i.b(cVar, "completion");
            AnonymousClass1 r0 = new AnonymousClass1(this.this$0, cVar);
            r0.p$ = (g0) obj;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
        }

        public final Object invokeSuspend(Object obj) {
            Object unused = b.a();
            if (this.label == 0) {
                kotlin.i.a(obj);
                this.this$0.this$0.q().dismiss();
                return l.a;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    BaseActivity$showLoading$2(BaseActivity baseActivity, long j2, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.this$0 = baseActivity;
        this.$delay = j2;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        BaseActivity$showLoading$2 baseActivity$showLoading$2 = new BaseActivity$showLoading$2(this.this$0, this.$delay, cVar);
        baseActivity$showLoading$2.p$ = (g0) obj;
        return baseActivity$showLoading$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((BaseActivity$showLoading$2) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        g0 g0Var;
        Object a = b.a();
        int i2 = this.label;
        if (i2 == 0) {
            kotlin.i.a(obj);
            g0Var = this.p$;
            this.this$0.q().show();
            long j2 = this.$delay;
            this.L$0 = g0Var;
            this.label = 1;
            if (p0.a(j2, this) == a) {
                return a;
            }
        } else if (i2 == 1) {
            g0Var = (g0) this.L$0;
            kotlin.i.a(obj);
        } else if (i2 == 2) {
            g0 g0Var2 = (g0) this.L$0;
            kotlin.i.a(obj);
            return l.a;
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        t1 b = u0.b();
        AnonymousClass1 r3 = new AnonymousClass1(this, (kotlin.coroutines.c) null);
        this.L$0 = g0Var;
        this.label = 2;
        if (d.a(b, r3, this) == a) {
            return a;
        }
        return l.a;
    }
}
