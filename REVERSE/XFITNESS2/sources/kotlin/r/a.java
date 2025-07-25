package kotlin.r;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.i;

/* compiled from: SequencesJVM.kt */
public final class a<T> implements b<T> {
    private final AtomicReference<b<T>> a;

    public a(b<? extends T> bVar) {
        i.b(bVar, "sequence");
        this.a = new AtomicReference<>(bVar);
    }

    public Iterator<T> iterator() {
        b andSet = this.a.getAndSet((Object) null);
        if (andSet != null) {
            return andSet.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}
