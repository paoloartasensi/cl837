package com.chileaf.fitness.ui.activity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.g0;
import kotlinx.coroutines.k1;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.u0;

/* compiled from: DevicesActivity.kt */
final class DevicesActivity$initData$$inlined$with$lambda$3 implements SwipeRefreshLayout.j {
    final /* synthetic */ DevicesActivity a;

    /* renamed from: com.chileaf.fitness.ui.activity.DevicesActivity$initData$$inlined$with$lambda$3$1  reason: invalid class name */
    /* compiled from: DevicesActivity.kt */
    static final class AnonymousClass1 extends SuspendLambda implements p<g0, c<? super l>, Object> {
        Object L$0;
        int label;
        private g0 p$;
        final /* synthetic */ DevicesActivity$initData$$inlined$with$lambda$3 this$0;

        {
            this.this$0 = r1;
        }

        public final c<l> create(Object obj, c<?> cVar) {
            i.b(cVar, "completion");
            AnonymousClass1 r0 = new AnonymousClass1(this.this$0, cVar);
            r0.p$ = (g0) obj;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(obj, (c) obj2)).invokeSuspend(l.a);
        }

        public final Object invokeSuspend(Object obj) {
            Object a = b.a();
            int i2 = this.label;
            if (i2 == 0) {
                kotlin.i.a(obj);
                this.L$0 = this.p$;
                this.label = 1;
                if (p0.a(200, this) == a) {
                    return a;
                }
            } else if (i2 == 1) {
                g0 g0Var = (g0) this.L$0;
                kotlin.i.a(obj);
            } else {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            SwipeRefreshLayout swipeRefreshLayout = DevicesActivity.a(this.this$0.a).F;
            i.a((Object) swipeRefreshLayout, "mBinding.swipeRefresh");
            swipeRefreshLayout.setRefreshing(false);
            return l.a;
        }
    }

    DevicesActivity$initData$$inlined$with$lambda$3(DevicesActivity devicesActivity) {
        this.a = devicesActivity;
    }

    public final void a() {
        SwipeRefreshLayout swipeRefreshLayout = DevicesActivity.a(this.a).F;
        i.a((Object) swipeRefreshLayout, "mBinding.swipeRefresh");
        swipeRefreshLayout.setRefreshing(true);
        this.a.r().clear();
        k1 unused = e.a(this.a, u0.b(), (CoroutineStart) null, new AnonymousClass1(this, (c) null), 2, (Object) null);
    }
}
