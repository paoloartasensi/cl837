package com.chileaf.fitness.config;

import java.util.List;
import org.koin.core.d.a;

/* compiled from: AppModule.kt */
public final class AppModuleKt {
    private static final a a = i.a.b.a.a(false, false, AppModuleKt$httpModule$1.INSTANCE, 3, (Object) null);
    private static final a b = i.a.b.a.a(false, false, AppModuleKt$remoteModule$1.INSTANCE, 3, (Object) null);
    private static final a c;
    private static final List<a> d;

    static {
        a a2 = i.a.b.a.a(false, false, AppModuleKt$localModule$1.INSTANCE, 3, (Object) null);
        c = a2;
        d = j.b(a, b, a2);
    }

    public static final List<a> a() {
        return d;
    }
}
