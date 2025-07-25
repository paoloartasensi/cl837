package org.koin.core.g;

import i.a.b.b;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.i;
import org.koin.core.scope.Scope;
import org.koin.core.scope.a;

/* compiled from: ScopeRegistry.kt */
public final class c {
    private final ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Scope> b = new ConcurrentHashMap<>();

    public final Collection<a> a() {
        Collection<a> values = this.a.values();
        i.a((Object) values, "definitions.values");
        return values;
    }

    public final void a(org.koin.core.a aVar) {
        i.b(aVar, "koin");
        a(aVar.b());
    }

    private final void a(org.koin.core.d.a aVar) {
        for (b a2 : aVar.b()) {
            a(a2);
        }
    }

    private final void a(b bVar) {
        a aVar = this.a.get(bVar.c().toString());
        if (aVar == null) {
            this.a.put(bVar.c().toString(), bVar.a());
        } else {
            aVar.a().addAll(bVar.b());
        }
    }

    private final void a(Scope scope) {
        this.b.put(scope.c(), scope);
    }

    public final void a(Iterable<org.koin.core.d.a> iterable) {
        i.b(iterable, "modules");
        for (org.koin.core.d.a a2 : iterable) {
            a(a2);
        }
    }
}
