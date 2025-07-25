package org.koin.core.scope;

import java.util.HashSet;
import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;

/* compiled from: ScopeDefinition.kt */
public final class a {
    private final HashSet<BeanDefinition<?>> a = new HashSet<>();
    private final org.koin.core.f.a b;

    public a(org.koin.core.f.a aVar) {
        i.b(aVar, "qualifier");
        this.b = aVar;
    }

    public final HashSet<BeanDefinition<?>> a() {
        return this.a;
    }

    public final org.koin.core.f.a b() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof a) && i.a((Object) this.b, (Object) ((a) obj).b);
        }
        return true;
    }

    public int hashCode() {
        org.koin.core.f.a aVar = this.b;
        if (aVar != null) {
            return aVar.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "ScopeDefinition(qualifier=" + this.b + ")";
    }
}
