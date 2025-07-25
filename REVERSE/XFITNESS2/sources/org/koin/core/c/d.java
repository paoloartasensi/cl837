package org.koin.core.c;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.error.BadScopeInstanceException;
import org.koin.core.error.ScopeNotCreatedException;
import org.koin.core.scope.Scope;
import org.koin.core.scope.a;

/* compiled from: ScopeDefinitionInstance.kt */
public final class d<T> extends a<T> {
    private final Map<String, T> b = new ConcurrentHashMap();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d(BeanDefinition<T> beanDefinition) {
        super(beanDefinition);
        i.b(beanDefinition, "beanDefinition");
    }

    private final void a(BeanDefinition<?> beanDefinition, Scope scope) {
        a d = scope.d();
        org.koin.core.f.a b2 = d != null ? d.b() : null;
        org.koin.core.f.a f2 = beanDefinition.f();
        if (!(!i.a((Object) f2, (Object) b2))) {
            return;
        }
        if (b2 == null) {
            throw new BadScopeInstanceException("Can't use definition " + beanDefinition + " defined for scope '" + f2 + "', with an open scope instance " + scope + ". Use a scope instance with scope '" + f2 + '\'');
        } else if (f2 != null) {
            throw new BadScopeInstanceException("Can't use definition " + beanDefinition + " defined for scope '" + f2 + "' with scope instance " + scope + ". Use a scope instance with scope '" + f2 + "'.");
        }
    }

    public <T> T b(c cVar) {
        i.b(cVar, "context");
        if (cVar.a() == null) {
            throw new IllegalStateException("ScopeDefinitionInstance has no registered Koin instance".toString());
        } else if (!i.a((Object) cVar.c(), (Object) cVar.a().b())) {
            Scope c = cVar.c();
            if (c != null) {
                a(a(), c);
                String c2 = c.c();
                T t = this.b.get(c2);
                if (t == null) {
                    t = a(cVar);
                    Map<String, T> map = this.b;
                    if (t != null) {
                        map.put(c2, t);
                    } else {
                        throw new IllegalStateException(("Instance creation from " + a() + " should not be null").toString());
                    }
                }
                return t;
            }
            throw new IllegalStateException("ScopeDefinitionInstance has no scope in context".toString());
        } else {
            throw new ScopeNotCreatedException("No scope instance created to resolve " + a());
        }
    }
}
