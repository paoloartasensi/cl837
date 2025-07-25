package org.koin.core.scope;

import java.util.ArrayList;
import java.util.Set;
import kotlin.Pair;
import kotlin.jvm.internal.i;
import kotlin.reflect.c;
import org.koin.core.KoinApplication;
import org.koin.core.definition.BeanDefinition;
import org.koin.core.error.NoBeanDefFoundException;
import org.koin.core.g.a;
import org.koin.core.logger.Level;
import org.koin.core.logger.b;

/* compiled from: Scope.kt */
public final class Scope {
    private final a a = new a();
    private a b;
    private final String c;
    private final boolean d;
    private final org.koin.core.a e;

    public Scope(String str, boolean z, org.koin.core.a aVar) {
        i.b(str, "id");
        i.b(aVar, "_koin");
        this.c = str;
        this.d = z;
        this.e = aVar;
        new ArrayList();
    }

    public final a b() {
        return this.a;
    }

    public final String c() {
        return this.c;
    }

    public final a d() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof Scope) {
                Scope scope = (Scope) obj;
                if (i.a((Object) this.c, (Object) scope.c)) {
                    if (!(this.d == scope.d) || !i.a((Object) this.e, (Object) scope.e)) {
                        return false;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.c;
        int i2 = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        boolean z = this.d;
        if (z) {
            z = true;
        }
        int i3 = (hashCode + (z ? 1 : 0)) * 31;
        org.koin.core.a aVar = this.e;
        if (aVar != null) {
            i2 = aVar.hashCode();
        }
        return i3 + i2;
    }

    public String toString() {
        a aVar = this.b;
        StringBuilder sb = new StringBuilder();
        sb.append(",set:'");
        sb.append(aVar != null ? aVar.b() : null);
        sb.append('\'');
        String sb2 = sb.toString();
        return "Scope[id:'" + this.c + '\'' + sb2 + ']';
    }

    public final <T> T a(c<?> cVar, org.koin.core.f.a aVar, kotlin.jvm.b.a<org.koin.core.e.a> aVar2) {
        i.b(cVar, "clazz");
        synchronized (this) {
            if (KoinApplication.c.b().a(Level.DEBUG)) {
                b b2 = KoinApplication.c.b();
                b2.a("+- get '" + i.a.c.a.a(cVar) + '\'');
                Pair a2 = org.koin.core.h.a.a(new Scope$get$$inlined$synchronized$lambda$1(this, cVar, aVar, aVar2));
                T component1 = a2.component1();
                double doubleValue = ((Number) a2.component2()).doubleValue();
                b b3 = KoinApplication.c.b();
                b3.a("+- got '" + i.a.c.a.a(cVar) + "' in " + doubleValue + " ms");
                return component1;
            }
            T a3 = a(aVar, cVar, aVar2);
            return a3;
        }
    }

    /* access modifiers changed from: private */
    public final <T> T a(org.koin.core.f.a aVar, c<?> cVar, kotlin.jvm.b.a<org.koin.core.e.a> aVar2) {
        return a(aVar, cVar).a(new org.koin.core.c.c(this.e, this, aVar2));
    }

    private final BeanDefinition<?> a(org.koin.core.f.a aVar, c<?> cVar) {
        BeanDefinition<?> a2 = this.a.a(aVar, cVar);
        if (a2 != null) {
            return a2;
        }
        if (!this.d) {
            return this.e.b().a(aVar, cVar);
        }
        throw new NoBeanDefFoundException("No definition found for '" + i.a.c.a.a(cVar) + "' has been found. Check your module definitions.");
    }

    public final void a() {
        if (this.d) {
            Set<BeanDefinition<?>> a2 = this.a.a();
            if (!a2.isEmpty()) {
                for (BeanDefinition a3 : a2) {
                    a3.a(new org.koin.core.c.c(this.e, this, (kotlin.jvm.b.a) null, 4, (f) null));
                }
            }
        }
    }
}
