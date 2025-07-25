package kotlin.r;

import java.util.Iterator;
import kotlin.jvm.internal.i;

/* compiled from: Sequences.kt */
class f extends e {

    /* compiled from: Sequences.kt */
    public static final class a implements b<T> {
        final /* synthetic */ Iterator a;

        public a(Iterator it) {
            this.a = it;
        }

        public Iterator<T> iterator() {
            return this.a;
        }
    }

    public static <T> b<T> a(Iterator<? extends T> it) {
        i.b(it, "$this$asSequence");
        return a(new a(it));
    }

    public static final <T> b<T> a(b<? extends T> bVar) {
        i.b(bVar, "$this$constrainOnce");
        return bVar instanceof a ? bVar : new a(bVar);
    }
}
