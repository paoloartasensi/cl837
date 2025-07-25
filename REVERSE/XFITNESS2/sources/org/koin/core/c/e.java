package org.koin.core.c;

import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;

/* compiled from: SingleDefinitionInstance.kt */
public final class e<T> extends a<T> {
    private T b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(BeanDefinition<T> beanDefinition) {
        super(beanDefinition);
        i.b(beanDefinition, "beanDefinition");
    }

    public <T> T b(c cVar) {
        i.b(cVar, "context");
        if (this.b == null) {
            this.b = a(cVar);
        }
        T t = this.b;
        if (!(t instanceof Object)) {
            t = null;
        }
        if (t != null) {
            return t;
        }
        throw new IllegalStateException("Single instance created couldn't return value".toString());
    }
}
