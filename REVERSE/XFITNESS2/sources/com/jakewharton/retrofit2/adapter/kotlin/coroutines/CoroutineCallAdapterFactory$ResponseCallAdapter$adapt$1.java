package com.jakewharton.retrofit2.adapter.kotlin.coroutines;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.q;
import retrofit2.d;

/* compiled from: CoroutineCallAdapterFactory.kt */
final class CoroutineCallAdapterFactory$ResponseCallAdapter$adapt$1 extends Lambda implements l<Throwable, kotlin.l> {
    final /* synthetic */ d $call;
    final /* synthetic */ q $deferred;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CoroutineCallAdapterFactory$ResponseCallAdapter$adapt$1(q qVar, d dVar) {
        super(1);
        this.$deferred = qVar;
        this.$call = dVar;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return kotlin.l.a;
    }

    public final void invoke(Throwable th) {
        if (this.$deferred.isCancelled()) {
            this.$call.cancel();
        }
    }
}
