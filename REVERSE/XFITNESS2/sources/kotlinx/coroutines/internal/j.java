package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.k;

/* compiled from: LockFreeTaskQueue.kt */
public class j<E> {
    public static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(j.class, Object.class, "_cur$internal");
    public volatile /* synthetic */ Object _cur$internal;

    public j(boolean z) {
        this._cur$internal = new k(8, z);
    }

    public final void a() {
        while (true) {
            k kVar = (k) this._cur$internal;
            if (!kVar.a()) {
                a.compareAndSet(this, kVar, kVar.d());
            } else {
                return;
            }
        }
    }

    public final int b() {
        return ((k) this._cur$internal).b();
    }

    public final E c() {
        E e;
        E e2;
        while (true) {
            k kVar = (k) this._cur$internal;
            while (true) {
                long j2 = kVar._state$internal;
                e = null;
                if ((1152921504606846976L & j2) == 0) {
                    k.a aVar = k.f1802h;
                    int i2 = (int) ((1073741823 & j2) >> 0);
                    if ((kVar.a & ((int) ((1152921503533105152L & j2) >> 30))) == (kVar.a & i2)) {
                        break;
                    }
                    e2 = kVar.b.get(kVar.a & i2);
                    if (e2 != null) {
                        if (!(e2 instanceof k.b)) {
                            int i3 = (i2 + 1) & 1073741823;
                            if (!k.f1800f.compareAndSet(kVar, j2, k.f1802h.a(j2, i3))) {
                                if (kVar.d) {
                                    k kVar2 = kVar;
                                    do {
                                        kVar2 = kVar2.a(i2, i3);
                                    } while (kVar2 != null);
                                    break;
                                }
                            } else {
                                kVar.b.set(kVar.a & i2, (Object) null);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else if (kVar.d) {
                        break;
                    }
                } else {
                    e = k.f1801g;
                    break;
                }
            }
            e = e2;
            if (e != k.f1801g) {
                return e;
            }
            a.compareAndSet(this, kVar, kVar.d());
        }
    }

    public final boolean a(E e) {
        i.b(e, "element");
        while (true) {
            k kVar = (k) this._cur$internal;
            int a2 = kVar.a(e);
            if (a2 == 0) {
                return true;
            }
            if (a2 == 1) {
                a.compareAndSet(this, kVar, kVar.d());
            } else if (a2 == 2) {
                return false;
            }
        }
    }
}
