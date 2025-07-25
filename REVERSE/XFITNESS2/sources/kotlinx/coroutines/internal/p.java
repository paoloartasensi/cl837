package kotlinx.coroutines.internal;

import kotlin.jvm.internal.i;

/* compiled from: LockFreeLinkedList.kt */
final class p {
    public final i a;

    public p(i iVar) {
        i.b(iVar, "ref");
        this.a = iVar;
    }

    public String toString() {
        return "Removed[" + this.a + ']';
    }
}
