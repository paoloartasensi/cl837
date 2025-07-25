package org.koin.core.c;

import org.koin.core.e.a;
import org.koin.core.scope.Scope;

/* compiled from: DefinitionInstance.kt */
public final class c {
    private final a a;
    private final org.koin.core.a b;
    private final Scope c;
    private final kotlin.jvm.b.a<a> d;

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
        r1 = r3.invoke();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public c(org.koin.core.a r1, org.koin.core.scope.Scope r2, kotlin.jvm.b.a<org.koin.core.e.a> r3) {
        /*
            r0 = this;
            r0.<init>()
            r0.b = r1
            r0.c = r2
            r0.d = r3
            if (r3 == 0) goto L_0x0014
            java.lang.Object r1 = r3.invoke()
            org.koin.core.e.a r1 = (org.koin.core.e.a) r1
            if (r1 == 0) goto L_0x0014
            goto L_0x0018
        L_0x0014:
            org.koin.core.e.a r1 = org.koin.core.e.b.a()
        L_0x0018:
            r0.a = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.koin.core.c.c.<init>(org.koin.core.a, org.koin.core.scope.Scope, kotlin.jvm.b.a):void");
    }

    public final org.koin.core.a a() {
        return this.b;
    }

    public final a b() {
        return this.a;
    }

    public final Scope c() {
        return this.c;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ c(org.koin.core.a r2, org.koin.core.scope.Scope r3, kotlin.jvm.b.a r4, int r5, kotlin.jvm.internal.f r6) {
        /*
            r1 = this;
            r6 = r5 & 1
            r0 = 0
            if (r6 == 0) goto L_0x0006
            r2 = r0
        L_0x0006:
            r6 = r5 & 2
            if (r6 == 0) goto L_0x0012
            if (r2 == 0) goto L_0x0011
            org.koin.core.scope.Scope r3 = r2.b()
            goto L_0x0012
        L_0x0011:
            r3 = r0
        L_0x0012:
            r5 = r5 & 4
            if (r5 == 0) goto L_0x0017
            r4 = r0
        L_0x0017:
            r1.<init>(r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.koin.core.c.c.<init>(org.koin.core.a, org.koin.core.scope.Scope, kotlin.jvm.b.a, int, kotlin.jvm.internal.f):void");
    }
}
