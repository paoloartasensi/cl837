package kotlinx.coroutines;

import kotlin.NoWhenBranchMatchedException;
import kotlin.coroutines.c;
import kotlin.coroutines.e;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.j2.a;
import kotlinx.coroutines.j2.b;

/* compiled from: CoroutineStart.kt */
public enum CoroutineStart {
    DEFAULT,
    LAZY,
    ATOMIC,
    UNDISPATCHED;

    public final <T> void invoke(l<? super c<? super T>, ? extends Object> lVar, c<? super T> cVar) {
        i.b(lVar, "block");
        i.b(cVar, "completion");
        int i2 = i0.a[ordinal()];
        if (i2 == 1) {
            a.a(lVar, cVar);
        } else if (i2 == 2) {
            e.a(lVar, cVar);
        } else if (i2 == 3) {
            b.a(lVar, cVar);
        } else if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
    }

    public final boolean isLazy() {
        return this == LAZY;
    }

    public final <R, T> void invoke(p<? super R, ? super c<? super T>, ? extends Object> pVar, R r, c<? super T> cVar) {
        i.b(pVar, "block");
        i.b(cVar, "completion");
        int i2 = i0.b[ordinal()];
        if (i2 == 1) {
            a.a(pVar, r, cVar);
        } else if (i2 == 2) {
            e.a(pVar, r, cVar);
        } else if (i2 == 3) {
            b.a(pVar, r, cVar);
        } else if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
    }
}
