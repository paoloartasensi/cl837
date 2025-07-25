package org.koin.core.d;

import i.a.b.b;
import java.util.ArrayList;
import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.definition.c;

/* compiled from: Module.kt */
public final class a {
    private final ArrayList<BeanDefinition<?>> a = new ArrayList<>();
    private final ArrayList<b> b = new ArrayList<>();
    private final boolean c;
    private final boolean d;

    public a(boolean z, boolean z2) {
        this.c = z;
        this.d = z2;
    }

    public final ArrayList<BeanDefinition<?>> a() {
        return this.a;
    }

    public final ArrayList<b> b() {
        return this.b;
    }

    private final void b(BeanDefinition<?> beanDefinition, c cVar) {
        boolean z = false;
        beanDefinition.c().a(cVar.b() || this.c);
        c c2 = beanDefinition.c();
        if (cVar.a() || this.d) {
            z = true;
        }
        c2.b(z);
    }

    public final <T> void a(BeanDefinition<T> beanDefinition, c cVar) {
        i.b(beanDefinition, "definition");
        i.b(cVar, "options");
        b(beanDefinition, cVar);
        this.a.add(beanDefinition);
    }
}
