package org.koin.android.ext.koin;

import android.app.Application;
import android.content.Context;
import i.a.a.a.b;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import org.koin.core.KoinApplication;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Kind;
import org.koin.core.g.a;
import org.koin.core.logger.Level;

/* compiled from: KoinExt.kt */
public final class KoinExtKt {
    public static /* synthetic */ KoinApplication a(KoinApplication koinApplication, Level level, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            level = Level.INFO;
        }
        a(koinApplication, level);
        return koinApplication;
    }

    public static final KoinApplication a(KoinApplication koinApplication, Level level) {
        i.b(koinApplication, "$this$androidLogger");
        i.b(level, "level");
        KoinApplication.c.a(new b(level));
        return koinApplication;
    }

    public static final KoinApplication a(KoinApplication koinApplication, Context context) {
        i.b(koinApplication, "$this$androidContext");
        i.b(context, "androidContext");
        if (KoinApplication.c.b().a(Level.INFO)) {
            KoinApplication.c.b().c("[init] declare Android Context");
        }
        a b = koinApplication.b().b().b();
        org.koin.core.definition.b bVar = org.koin.core.definition.b.a;
        KoinExtKt$androidContext$1 koinExtKt$androidContext$1 = new KoinExtKt$androidContext$1(context);
        Kind kind = Kind.Single;
        BeanDefinition beanDefinition = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(Context.class));
        beanDefinition.a(koinExtKt$androidContext$1);
        beanDefinition.a(kind);
        b.a((BeanDefinition<?>) beanDefinition);
        if (context instanceof Application) {
            a b2 = koinApplication.b().b().b();
            org.koin.core.definition.b bVar2 = org.koin.core.definition.b.a;
            KoinExtKt$androidContext$2 koinExtKt$androidContext$2 = new KoinExtKt$androidContext$2(context);
            Kind kind2 = Kind.Single;
            BeanDefinition beanDefinition2 = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(Application.class));
            beanDefinition2.a(koinExtKt$androidContext$2);
            beanDefinition2.a(kind2);
            b2.a((BeanDefinition<?>) beanDefinition2);
        }
        return koinApplication;
    }
}
