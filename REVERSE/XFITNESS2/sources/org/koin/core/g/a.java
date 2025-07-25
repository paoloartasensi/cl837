package org.koin.core.g;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.i;
import kotlin.reflect.c;
import org.koin.core.KoinApplication;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.error.DefinitionOverrideException;
import org.koin.core.error.NoBeanDefFoundException;
import org.koin.core.logger.Level;
import org.koin.core.logger.b;

/* compiled from: BeanRegistry.kt */
public final class a {
    private final HashSet<BeanDefinition<?>> a = new HashSet<>();
    private final Map<String, BeanDefinition<?>> b = new ConcurrentHashMap();
    private final Map<c<?>, BeanDefinition<?>> c = new ConcurrentHashMap();
    private final Map<c<?>, ArrayList<BeanDefinition<?>>> d = new ConcurrentHashMap();
    private final HashSet<BeanDefinition<?>> e = new HashSet<>();

    private final void a(org.koin.core.d.a aVar) {
        for (BeanDefinition a2 : aVar.a()) {
            a((BeanDefinition<?>) a2);
        }
    }

    private final void c(BeanDefinition<?> beanDefinition) {
        for (c a2 : beanDefinition.g()) {
            a(beanDefinition, (c<?>) a2);
        }
    }

    private final void d(BeanDefinition<?> beanDefinition) {
        this.e.add(beanDefinition);
    }

    private final void e(BeanDefinition<?> beanDefinition) {
        a(beanDefinition.d(), beanDefinition);
    }

    public final Set<BeanDefinition<?>> b() {
        return this.a;
    }

    private final void b(BeanDefinition<?> beanDefinition) {
        org.koin.core.f.a e2 = beanDefinition.e();
        if (e2 == null) {
            return;
        }
        if (this.b.get(e2.toString()) == null || beanDefinition.c().a()) {
            this.b.put(e2.toString(), beanDefinition);
            if (KoinApplication.c.b().a(Level.INFO)) {
                b b2 = KoinApplication.c.b();
                b2.c("bind qualifier:'" + beanDefinition.e() + "' ~ " + beanDefinition);
                return;
            }
            return;
        }
        throw new DefinitionOverrideException("Already existing definition or try to override an existing one with qualifier '" + e2 + "' with " + beanDefinition + " but has already registered " + this.b.get(e2.toString()));
    }

    private final BeanDefinition<?> c(c<?> cVar) {
        return this.c.get(cVar);
    }

    public final void a(BeanDefinition<?> beanDefinition) {
        i.b(beanDefinition, "definition");
        a(this.a, beanDefinition);
        beanDefinition.a();
        if (beanDefinition.e() != null) {
            b(beanDefinition);
        } else {
            e(beanDefinition);
        }
        if (!beanDefinition.g().isEmpty()) {
            c(beanDefinition);
        }
        if (beanDefinition.c().b()) {
            d(beanDefinition);
        }
    }

    private final BeanDefinition<?> b(c<?> cVar) {
        ArrayList arrayList = this.d.get(cVar);
        if (arrayList != null && arrayList.size() == 1) {
            return (BeanDefinition) arrayList.get(0);
        }
        if (arrayList == null || arrayList.size() <= 1) {
            return null;
        }
        throw new NoBeanDefFoundException("Found multiple definitions for type '" + i.a.c.a.a(cVar) + "': " + arrayList + ". Please use the 'bind<P,S>()' function to bind your instance from primary and secondary types.");
    }

    private final void a(BeanDefinition<?> beanDefinition, c<?> cVar) {
        ArrayList<BeanDefinition<?>> arrayList = this.d.get(cVar);
        if (arrayList == null) {
            arrayList = a(cVar);
        }
        arrayList.add(beanDefinition);
        if (KoinApplication.c.b().a(Level.INFO)) {
            b b2 = KoinApplication.c.b();
            b2.c("bind secondary type:'" + i.a.c.a.a(cVar) + "' ~ " + beanDefinition);
        }
    }

    private final ArrayList<BeanDefinition<?>> a(c<?> cVar) {
        this.d.put(cVar, new ArrayList());
        ArrayList<BeanDefinition<?>> arrayList = this.d.get(cVar);
        if (arrayList != null) {
            return arrayList;
        }
        i.a();
        throw null;
    }

    private final void a(HashSet<BeanDefinition<?>> hashSet, BeanDefinition<?> beanDefinition) {
        if (!hashSet.add(beanDefinition) && !beanDefinition.c().a()) {
            throw new DefinitionOverrideException("Already existing definition or try to override an existing one: " + beanDefinition);
        }
    }

    private final void a(c<?> cVar, BeanDefinition<?> beanDefinition) {
        if (this.c.get(cVar) == null || beanDefinition.c().a()) {
            this.c.put(cVar, beanDefinition);
            if (KoinApplication.c.b().a(Level.INFO)) {
                b b2 = KoinApplication.c.b();
                b2.c("bind type:'" + i.a.c.a.a(cVar) + "' ~ " + beanDefinition);
                return;
            }
            return;
        }
        throw new DefinitionOverrideException("Already existing definition or try to override an existing one with type '" + cVar + "' and " + beanDefinition + " but has already registered " + this.c.get(cVar));
    }

    public final BeanDefinition<?> a(org.koin.core.f.a aVar, c<?> cVar) {
        i.b(cVar, "clazz");
        if (aVar != null) {
            return a(aVar.toString());
        }
        BeanDefinition<?> c2 = c(cVar);
        return c2 != null ? c2 : b(cVar);
    }

    private final BeanDefinition<?> a(String str) {
        return this.b.get(str);
    }

    public final Set<BeanDefinition<?>> a() {
        return this.e;
    }

    public final void a(Iterable<org.koin.core.d.a> iterable) {
        i.b(iterable, "modules");
        for (org.koin.core.d.a a2 : iterable) {
            a(a2);
        }
    }
}
