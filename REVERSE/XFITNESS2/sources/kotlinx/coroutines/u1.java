package kotlinx.coroutines;

import kotlin.TypeCastException;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.g;

/* compiled from: JobSupport.kt */
public final class u1 extends g implements f1 {
    public final String a(String str) {
        i.b(str, "state");
        StringBuilder sb = new StringBuilder();
        sb.append("List{");
        sb.append(str);
        sb.append("}[");
        Object c = c();
        if (c != null) {
            boolean z = true;
            for (kotlinx.coroutines.internal.i iVar = (kotlinx.coroutines.internal.i) c; !i.a((Object) iVar, (Object) this); iVar = iVar.d()) {
                if (iVar instanceof p1) {
                    p1 p1Var = (p1) iVar;
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(p1Var);
                }
            }
            sb.append("]");
            String sb2 = sb.toString();
            i.a((Object) sb2, "StringBuilder().apply(builderAction).toString()");
            return sb2;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    public u1 b() {
        return this;
    }

    public boolean isActive() {
        return true;
    }

    public String toString() {
        return j0.c() ? a("Active") : super.toString();
    }
}
