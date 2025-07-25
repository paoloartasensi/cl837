package kotlinx.coroutines;

import kotlinx.coroutines.internal.t;

/* compiled from: JobSupport.kt */
public final class r1 {
    /* access modifiers changed from: private */
    public static final t a = new t("SEALED");
    /* access modifiers changed from: private */
    public static final x0 b = new x0(false);
    /* access modifiers changed from: private */
    public static final x0 c = new x0(true);

    public static final Object a(Object obj) {
        return obj instanceof f1 ? new g1((f1) obj) : obj;
    }

    public static final Object b(Object obj) {
        f1 f1Var;
        g1 g1Var = (g1) (!(obj instanceof g1) ? null : obj);
        return (g1Var == null || (f1Var = g1Var.a) == null) ? obj : f1Var;
    }
}
