package org.koin.core.definition;

import java.util.ArrayList;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.reflect.c;
import org.koin.core.c.a;
import org.koin.core.c.b;
import org.koin.core.c.d;
import org.koin.core.c.e;
import org.koin.core.scope.Scope;

/* compiled from: BeanDefinition.kt */
public final class BeanDefinition<T> {
    private ArrayList<c<?>> a = new ArrayList<>();
    private a<T> b;
    public p<? super Scope, ? super org.koin.core.e.a, ? extends T> c;
    private c d = new c(false, false, 3, (f) null);
    public Kind e;

    /* renamed from: f  reason: collision with root package name */
    private final org.koin.core.f.a f2111f;

    /* renamed from: g  reason: collision with root package name */
    private final org.koin.core.f.a f2112g;

    /* renamed from: h  reason: collision with root package name */
    private final c<?> f2113h;

    public BeanDefinition(org.koin.core.f.a aVar, org.koin.core.f.a aVar2, c<?> cVar) {
        i.b(cVar, "primaryType");
        this.f2111f = aVar;
        this.f2112g = aVar2;
        this.f2113h = cVar;
        new d((Map) null, 1, (f) null);
    }

    public final void a(p<? super Scope, ? super org.koin.core.e.a, ? extends T> pVar) {
        i.b(pVar, "<set-?>");
        this.c = pVar;
    }

    public final p<Scope, org.koin.core.e.a, T> b() {
        p<? super Scope, ? super org.koin.core.e.a, ? extends T> pVar = this.c;
        if (pVar != null) {
            return pVar;
        }
        i.d("definition");
        throw null;
    }

    public final c c() {
        return this.d;
    }

    public final c<?> d() {
        return this.f2113h;
    }

    public final org.koin.core.f.a e() {
        return this.f2111f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!i.a((Object) BeanDefinition.class, (Object) obj != null ? obj.getClass() : null)) {
            return false;
        }
        if (obj != null) {
            BeanDefinition beanDefinition = (BeanDefinition) obj;
            return !(i.a((Object) this.f2111f, (Object) beanDefinition.f2111f) ^ true) && !(i.a((Object) this.f2113h, (Object) beanDefinition.f2113h) ^ true);
        }
        throw new TypeCastException("null cannot be cast to non-null type org.koin.core.definition.BeanDefinition<*>");
    }

    public final org.koin.core.f.a f() {
        return this.f2112g;
    }

    public final ArrayList<c<?>> g() {
        return this.a;
    }

    public int hashCode() {
        org.koin.core.f.a aVar = this.f2111f;
        return ((aVar != null ? aVar.hashCode() : 0) * 31) + this.f2113h.hashCode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0044, code lost:
        if (r2 != null) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0026, code lost:
        if (r1 != null) goto L_0x002a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r15 = this;
            org.koin.core.definition.Kind r0 = r15.e
            if (r0 == 0) goto L_0x00b8
            java.lang.String r0 = r0.toString()
            org.koin.core.f.a r1 = r15.f2111f
            java.lang.String r2 = "', "
            java.lang.String r3 = ""
            if (r1 == 0) goto L_0x0029
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "name:'"
            r1.append(r4)
            org.koin.core.f.a r4 = r15.f2111f
            r1.append(r4)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            if (r1 == 0) goto L_0x0029
            goto L_0x002a
        L_0x0029:
            r1 = r3
        L_0x002a:
            org.koin.core.f.a r4 = r15.f2112g
            if (r4 == 0) goto L_0x0047
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "scope:'"
            r4.append(r5)
            org.koin.core.f.a r5 = r15.f2112g
            r4.append(r5)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            if (r2 == 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r2 = r3
        L_0x0048:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "primary_type:'"
            r4.append(r5)
            kotlin.reflect.c<?> r5 = r15.f2113h
            java.lang.String r5 = i.a.c.a.a(r5)
            r4.append(r5)
            r5 = 39
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.util.ArrayList<kotlin.reflect.c<?>> r5 = r15.a
            boolean r5 = r5.isEmpty()
            r5 = r5 ^ 1
            if (r5 == 0) goto L_0x0090
            java.util.ArrayList<kotlin.reflect.c<?>> r6 = r15.a
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            org.koin.core.definition.BeanDefinition$toString$defOtherTypes$typesAsString$1 r12 = org.koin.core.definition.BeanDefinition$toString$defOtherTypes$typesAsString$1.INSTANCE
            r13 = 30
            r14 = 0
            java.lang.String r7 = ","
            java.lang.String r3 = kotlin.collections.r.a(r6, r7, r8, r9, r10, r11, r12, r13, r14)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = ", secondary_type:"
            r5.append(r6)
            r5.append(r3)
            java.lang.String r3 = r5.toString()
        L_0x0090:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "[type:"
            r5.append(r6)
            r5.append(r0)
            r0 = 44
            r5.append(r0)
            r5.append(r2)
            r5.append(r1)
            r5.append(r4)
            r5.append(r3)
            r0 = 93
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            return r0
        L_0x00b8:
            java.lang.String r0 = "kind"
            kotlin.jvm.internal.i.d(r0)
            r0 = 0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.koin.core.definition.BeanDefinition.toString():java.lang.String");
    }

    public final void a(Kind kind) {
        i.b(kind, "<set-?>");
        this.e = kind;
    }

    public final void a() {
        a<T> aVar;
        Kind kind = this.e;
        if (kind != null) {
            int i2 = a.a[kind.ordinal()];
            if (i2 == 1) {
                aVar = new e<>(this);
            } else if (i2 == 2) {
                aVar = new b<>(this);
            } else if (i2 == 3) {
                aVar = new d<>(this);
            } else {
                throw new NoWhenBranchMatchedException();
            }
            this.b = aVar;
            return;
        }
        i.d("kind");
        throw null;
    }

    public final <T> T a(org.koin.core.c.c cVar) {
        T b2;
        i.b(cVar, "context");
        a<T> aVar = this.b;
        if (aVar != null && (b2 = aVar.b(cVar)) != null) {
            return b2;
        }
        throw new IllegalStateException(("Definition without any InstanceContext - " + this).toString());
    }
}
