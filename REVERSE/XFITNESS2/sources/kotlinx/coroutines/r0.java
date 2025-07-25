package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.q;

/* compiled from: Builders.common.kt */
final class r0<T> extends q<T> {

    /* renamed from: i  reason: collision with root package name */
    private static final AtomicIntegerFieldUpdater f1818i = AtomicIntegerFieldUpdater.newUpdater(r0.class, "_decision");
    private volatile int _decision = 0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r0(CoroutineContext coroutineContext, c<? super T> cVar) {
        super(coroutineContext, cVar);
        i.b(coroutineContext, "context");
        i.b(cVar, "uCont");
    }

    private final boolean o() {
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!f1818i.compareAndSet(this, 0, 2));
        return true;
    }

    private final boolean p() {
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!f1818i.compareAndSet(this, 0, 1));
        return true;
    }

    /* access modifiers changed from: protected */
    public void a(Object obj, int i2) {
        if (!o()) {
            super.a(obj, i2);
        }
    }

    public int k() {
        return 1;
    }

    public final Object n() {
        if (p()) {
            return b.a();
        }
        Object b = r1.b(e());
        if (!(b instanceof u)) {
            return b;
        }
        throw ((u) b).a;
    }
}
