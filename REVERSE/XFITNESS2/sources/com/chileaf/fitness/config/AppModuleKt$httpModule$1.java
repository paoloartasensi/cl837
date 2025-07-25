package com.chileaf.fitness.config;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;
import okhttp3.a0;
import okhttp3.d0;
import org.koin.core.d.a;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.Kind;
import org.koin.core.definition.b;
import org.koin.core.definition.c;
import retrofit2.s;

/* compiled from: AppModule.kt */
final class AppModuleKt$httpModule$1 extends Lambda implements l<a, kotlin.l> {
    public static final AppModuleKt$httpModule$1 INSTANCE = new AppModuleKt$httpModule$1();

    AppModuleKt$httpModule$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((a) obj);
        return kotlin.l.a;
    }

    public final void invoke(a aVar) {
        i.b(aVar, "$receiver");
        AnonymousClass1 r0 = AnonymousClass1.INSTANCE;
        b bVar = b.a;
        Kind kind = Kind.Factory;
        BeanDefinition beanDefinition = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(a0.class));
        beanDefinition.a(r0);
        beanDefinition.a(kind);
        aVar.a(beanDefinition, new c(false, false, 1, (f) null));
        AnonymousClass2 r02 = AnonymousClass2.INSTANCE;
        b bVar2 = b.a;
        Kind kind2 = Kind.Factory;
        BeanDefinition beanDefinition2 = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(d0.class));
        beanDefinition2.a(r02);
        beanDefinition2.a(kind2);
        aVar.a(beanDefinition2, new c(false, false, 1, (f) null));
        AnonymousClass3 r03 = AnonymousClass3.INSTANCE;
        b bVar3 = b.a;
        Kind kind3 = Kind.Single;
        BeanDefinition beanDefinition3 = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(s.class));
        beanDefinition3.a(r03);
        beanDefinition3.a(kind3);
        aVar.a(beanDefinition3, new c(false, false));
        AnonymousClass4 r04 = AnonymousClass4.INSTANCE;
        b bVar4 = b.a;
        Kind kind4 = Kind.Factory;
        BeanDefinition beanDefinition4 = new BeanDefinition((org.koin.core.f.a) null, (org.koin.core.f.a) null, k.a(com.chileaf.fitness.config.http.a.class));
        beanDefinition4.a(r04);
        beanDefinition4.a(kind4);
        aVar.a(beanDefinition4, new c(false, false, 1, (f) null));
    }
}
