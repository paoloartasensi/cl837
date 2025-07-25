package i.a.b;

import java.util.HashSet;
import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.f.a;

/* compiled from: ScopeSet.kt */
public final class b {
    private final HashSet<BeanDefinition<?>> a;
    private final a b;

    public final org.koin.core.scope.a a() {
        org.koin.core.scope.a aVar = new org.koin.core.scope.a(this.b);
        aVar.a().addAll(this.a);
        return aVar;
    }

    public final HashSet<BeanDefinition<?>> b() {
        return this.a;
    }

    public final a c() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof b) && i.a((Object) this.b, (Object) ((b) obj).b);
        }
        return true;
    }

    public int hashCode() {
        a aVar = this.b;
        if (aVar != null) {
            return aVar.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "Scope['" + this.b + "']";
    }
}
