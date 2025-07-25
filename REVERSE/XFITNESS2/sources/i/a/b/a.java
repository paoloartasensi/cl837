package i.a.b;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: Module.kt */
public final class a {
    public static /* synthetic */ org.koin.core.d.a a(boolean z, boolean z2, l lVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = false;
        }
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return a(z, z2, lVar);
    }

    public static final org.koin.core.d.a a(boolean z, boolean z2, l<? super org.koin.core.d.a, kotlin.l> lVar) {
        i.b(lVar, "moduleDeclaration");
        org.koin.core.d.a aVar = new org.koin.core.d.a(z, z2);
        lVar.invoke(aVar);
        return aVar;
    }
}
