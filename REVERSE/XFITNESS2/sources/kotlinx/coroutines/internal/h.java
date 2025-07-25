package kotlinx.coroutines.internal;

import kotlin.jvm.internal.i;

/* compiled from: LockFreeLinkedList.kt */
public final class h {
    private static final Object a = new t("CONDITION_FALSE");

    static {
        new t("ALREADY_REMOVED");
        new t("LIST_EMPTY");
        new t("REMOVE_PREPARED");
    }

    public static final Object a() {
        return a;
    }

    public static final i a(Object obj) {
        i iVar;
        i.b(obj, "$this$unwrap");
        p pVar = (p) (!(obj instanceof p) ? null : obj);
        return (pVar == null || (iVar = pVar.a) == null) ? (i) obj : iVar;
    }
}
