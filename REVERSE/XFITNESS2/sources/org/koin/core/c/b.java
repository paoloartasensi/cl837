package org.koin.core.c;

import kotlin.jvm.internal.i;
import org.koin.core.definition.BeanDefinition;

/* compiled from: FactoryDefinitionInstance.kt */
public final class b<T> extends a<T> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(BeanDefinition<T> beanDefinition) {
        super(beanDefinition);
        i.b(beanDefinition, "beanDefinition");
    }

    public <T> T b(c cVar) {
        i.b(cVar, "context");
        return a(cVar);
    }
}
