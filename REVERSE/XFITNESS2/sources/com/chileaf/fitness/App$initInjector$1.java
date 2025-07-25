package com.chileaf.fitness;

import android.content.Context;
import com.chileaf.fitness.config.AppModuleKt;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import org.koin.android.ext.koin.KoinExtKt;
import org.koin.core.KoinApplication;
import org.koin.core.logger.Level;

/* compiled from: App.kt */
final class App$initInjector$1 extends Lambda implements l<KoinApplication, kotlin.l> {
    final /* synthetic */ App this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    App$initInjector$1(App app) {
        super(1);
        this.this$0 = app;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((KoinApplication) obj);
        return kotlin.l.a;
    }

    public final void invoke(KoinApplication koinApplication) {
        i.b(koinApplication, "$receiver");
        KoinExtKt.a(koinApplication, (Level) null, 1, (Object) null);
        KoinExtKt.a(koinApplication, (Context) this.this$0);
        koinApplication.a(AppModuleKt.a());
    }
}
