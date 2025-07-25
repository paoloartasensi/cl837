package com.chileaf.fitness.ui.activity;

import com.chileaf.fitness.ui.MainActivity;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.coroutines.jvm.internal.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.p0;

@c(c = "com.chileaf.fitness.ui.activity.SplashActivity$initData$1", f = "SplashActivity.kt", l = {30}, m = "invokeSuspend")
/* compiled from: SplashActivity.kt */
final class SplashActivity$initData$1 extends SuspendLambda implements p<g0, kotlin.coroutines.c<? super l>, Object> {
    Object L$0;
    int label;
    private g0 p$;
    final /* synthetic */ SplashActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SplashActivity$initData$1(SplashActivity splashActivity, kotlin.coroutines.c cVar) {
        super(2, cVar);
        this.this$0 = splashActivity;
    }

    public final kotlin.coroutines.c<l> create(Object obj, kotlin.coroutines.c<?> cVar) {
        i.b(cVar, "completion");
        SplashActivity$initData$1 splashActivity$initData$1 = new SplashActivity$initData$1(this.this$0, cVar);
        splashActivity$initData$1.p$ = (g0) obj;
        return splashActivity$initData$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SplashActivity$initData$1) create(obj, (kotlin.coroutines.c) obj2)).invokeSuspend(l.a);
    }

    public final Object invokeSuspend(Object obj) {
        Object a = b.a();
        int i2 = this.label;
        if (i2 == 0) {
            kotlin.i.a(obj);
            this.L$0 = this.p$;
            this.label = 1;
            if (p0.a(1500, this) == a) {
                return a;
            }
        } else if (i2 == 1) {
            g0 g0Var = (g0) this.L$0;
            kotlin.i.a(obj);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        this.this$0.a((Class<?>) MainActivity.class, true);
        return l.a;
    }
}
