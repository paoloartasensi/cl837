package org.koin.core.c;

import java.util.ArrayList;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import org.koin.core.KoinApplication;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.error.InstanceCreationException;
import org.koin.core.logger.Level;
import org.koin.core.scope.Scope;

/* compiled from: DefinitionInstance.kt */
public abstract class a<T> {
    private final BeanDefinition<T> a;

    /* renamed from: org.koin.core.c.a$a  reason: collision with other inner class name */
    /* compiled from: DefinitionInstance.kt */
    public static final class C0098a {
        private C0098a() {
        }

        public /* synthetic */ C0098a(f fVar) {
            this();
        }
    }

    static {
        new C0098a((f) null);
    }

    public a(BeanDefinition<T> beanDefinition) {
        i.b(beanDefinition, "beanDefinition");
        this.a = beanDefinition;
    }

    public final BeanDefinition<T> a() {
        return this.a;
    }

    public abstract <T> T b(c cVar);

    public <T> T a(c cVar) {
        i.b(cVar, "context");
        if (KoinApplication.c.b().a(Level.DEBUG)) {
            KoinApplication.c.b().a("| create instance for " + this.a);
        }
        try {
            org.koin.core.e.a b = cVar.b();
            p<Scope, org.koin.core.e.a, T> b2 = this.a.b();
            Scope c = cVar.c();
            if (c != null) {
                return b2.invoke(c, b);
            }
            throw new IllegalStateException("Can't execute definition instance while this context is not registered against any Koin instance".toString());
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString());
            sb.append("\n\t");
            StackTraceElement[] stackTrace = e.getStackTrace();
            i.a((Object) stackTrace, "e.stackTrace");
            ArrayList arrayList = new ArrayList();
            for (StackTraceElement stackTraceElement : stackTrace) {
                i.a((Object) stackTraceElement, "it");
                String className = stackTraceElement.getClassName();
                i.a((Object) className, "it.className");
                if (!(!m.a((CharSequence) className, (CharSequence) "sun.reflect", false, 2, (Object) null))) {
                    break;
                }
                arrayList.add(stackTraceElement);
            }
            sb.append(r.a(arrayList, "\n\t", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 62, (Object) null));
            String sb2 = sb.toString();
            KoinApplication.c.b().b("Instance creation error : could not create instance for " + this.a + ": " + sb2);
            throw new InstanceCreationException("Could not create instance for " + this.a, e);
        }
    }
}
