package org.koin.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.i;
import org.koin.core.logger.Level;
import org.koin.core.logger.b;

/* compiled from: KoinApplication.kt */
public final class KoinApplication {
    /* access modifiers changed from: private */
    public static b b = new org.koin.core.logger.a();
    public static final a c = new a((f) null);
    private final a a;

    /* compiled from: KoinApplication.kt */
    public static final class a {
        private a() {
        }

        public final void a(b bVar) {
            i.b(bVar, "<set-?>");
            KoinApplication.b = bVar;
        }

        public final b b() {
            return KoinApplication.b;
        }

        public /* synthetic */ a(f fVar) {
            this();
        }

        public final KoinApplication a() {
            KoinApplication koinApplication = new KoinApplication((f) null);
            koinApplication.c();
            return koinApplication;
        }
    }

    private KoinApplication() {
        this.a = new a();
    }

    public final a b() {
        return this.a;
    }

    public final void c() {
        this.a.c().a(this.a);
    }

    public /* synthetic */ KoinApplication(f fVar) {
        this();
    }

    public final KoinApplication a(List<org.koin.core.d.a> list) {
        i.b(list, "modules");
        if (b.a(Level.INFO)) {
            double b2 = org.koin.core.h.a.b(new KoinApplication$modules$duration$1(this, list));
            int size = this.a.b().b().b().size();
            Collection<org.koin.core.scope.a> a2 = this.a.c().a();
            ArrayList arrayList = new ArrayList(k.a(a2, 10));
            for (org.koin.core.scope.a a3 : a2) {
                arrayList.add(Integer.valueOf(a3.a().size()));
            }
            int a4 = size + r.a((Iterable<Integer>) arrayList);
            b bVar = b;
            bVar.c("total " + a4 + " registered definitions");
            b bVar2 = b;
            bVar2.c("load modules in " + b2 + " ms");
        } else {
            a((Iterable<org.koin.core.d.a>) list);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public final void a(Iterable<org.koin.core.d.a> iterable) {
        this.a.b().b().a(iterable);
        this.a.c().a(iterable);
    }

    public final KoinApplication a() {
        if (b.a(Level.DEBUG)) {
            double b2 = org.koin.core.h.a.b(new KoinApplication$createEagerInstances$duration$1(this));
            b bVar = b;
            bVar.a("instances started in " + b2 + " ms");
        } else {
            this.a.a();
        }
        return this;
    }
}
