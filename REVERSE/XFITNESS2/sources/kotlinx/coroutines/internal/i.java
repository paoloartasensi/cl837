package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlinx.coroutines.j0;

/* compiled from: LockFreeLinkedList.kt */
public class i {
    static final AtomicReferenceFieldUpdater e;

    /* renamed from: f  reason: collision with root package name */
    static final AtomicReferenceFieldUpdater f1798f;

    /* renamed from: g  reason: collision with root package name */
    private static final AtomicReferenceFieldUpdater f1799g;
    volatile Object _next = this;
    volatile Object _prev = this;
    private volatile Object _removedRef = null;

    /* compiled from: LockFreeLinkedList.kt */
    public static abstract class a extends c<i> {
        public i b;
        public final i c;

        public a(i iVar) {
            kotlin.jvm.internal.i.b(iVar, "newNode");
            this.c = iVar;
        }

        public void a(i iVar, Object obj) {
            kotlin.jvm.internal.i.b(iVar, "affected");
            boolean z = obj == null;
            i iVar2 = z ? this.c : this.b;
            if (iVar2 != null && i.e.compareAndSet(iVar, this, iVar2) && z) {
                i iVar3 = this.c;
                i iVar4 = this.b;
                if (iVar4 != null) {
                    iVar3.b(iVar4);
                } else {
                    kotlin.jvm.internal.i.a();
                    throw null;
                }
            }
        }
    }

    static {
        Class<Object> cls = Object.class;
        Class<i> cls2 = i.class;
        e = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_next");
        f1798f = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_prev");
        f1799g = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_removedRef");
    }

    /* access modifiers changed from: private */
    public final void b(i iVar) {
        Object obj;
        do {
            obj = iVar._prev;
            if ((obj instanceof p) || c() != iVar) {
                return;
            }
        } while (!f1798f.compareAndSet(iVar, obj, this));
        if (!(c() instanceof p)) {
            return;
        }
        if (obj != null) {
            iVar.a((i) obj, (o) null);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    private final void c(i iVar) {
        g();
        iVar.a(h.a(this._prev), (o) null);
    }

    private final i j() {
        i iVar = this;
        while (!(iVar instanceof g)) {
            iVar = iVar.d();
            if (j0.a()) {
                if (!(iVar != this)) {
                    throw new AssertionError();
                }
            }
        }
        return iVar;
    }

    private final i k() {
        Object obj;
        i iVar;
        do {
            obj = this._prev;
            if (obj instanceof p) {
                return ((p) obj).a;
            }
            if (obj == this) {
                iVar = j();
            } else if (obj != null) {
                iVar = (i) obj;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        } while (!f1798f.compareAndSet(this, obj, iVar.l()));
        return (i) obj;
    }

    private final p l() {
        p pVar = (p) this._removedRef;
        if (pVar != null) {
            return pVar;
        }
        p pVar2 = new p(this);
        f1799g.lazySet(this, pVar2);
        return pVar2;
    }

    public final i d() {
        return h.a(c());
    }

    public final Object e() {
        while (true) {
            Object obj = this._prev;
            if (obj instanceof p) {
                return obj;
            }
            if (obj != null) {
                i iVar = (i) obj;
                if (iVar.c() == this) {
                    return obj;
                }
                a(iVar, (o) null);
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        }
    }

    public final i f() {
        return h.a(e());
    }

    public final void g() {
        Object c;
        i k = k();
        Object obj = this._next;
        if (obj != null) {
            i iVar = ((p) obj).a;
            while (true) {
                i iVar2 = null;
                while (true) {
                    Object c2 = iVar.c();
                    if (c2 instanceof p) {
                        iVar.k();
                        iVar = ((p) c2).a;
                    } else {
                        c = k.c();
                        if (c instanceof p) {
                            if (iVar2 != null) {
                                break;
                            }
                            k = h.a(k._prev);
                        } else if (c != this) {
                            if (c != null) {
                                i iVar3 = (i) c;
                                if (iVar3 != iVar) {
                                    i iVar4 = iVar3;
                                    iVar2 = k;
                                    k = iVar4;
                                } else {
                                    return;
                                }
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                            }
                        } else if (e.compareAndSet(k, this, iVar)) {
                            return;
                        }
                    }
                }
                k.k();
                e.compareAndSet(iVar2, k, ((p) c).a);
                k = iVar2;
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Removed");
        }
    }

    public final boolean h() {
        return c() instanceof p;
    }

    public boolean i() {
        Object c;
        i iVar;
        do {
            c = c();
            if ((c instanceof p) || c == this) {
                return false;
            }
            if (c != null) {
                iVar = (i) c;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        } while (!e.compareAndSet(this, c, iVar.l()));
        c(iVar);
        return true;
    }

    public String toString() {
        return getClass().getSimpleName() + '@' + Integer.toHexString(System.identityHashCode(this));
    }

    public final boolean a(i iVar) {
        kotlin.jvm.internal.i.b(iVar, "node");
        f1798f.lazySet(iVar, this);
        e.lazySet(iVar, this);
        while (c() == this) {
            if (e.compareAndSet(this, this, iVar)) {
                iVar.b(this);
                return true;
            }
        }
        return false;
    }

    public final Object c() {
        while (true) {
            Object obj = this._next;
            if (!(obj instanceof o)) {
                return obj;
            }
            ((o) obj).a(this);
        }
    }

    public final int a(i iVar, i iVar2, a aVar) {
        kotlin.jvm.internal.i.b(iVar, "node");
        kotlin.jvm.internal.i.b(iVar2, "next");
        kotlin.jvm.internal.i.b(aVar, "condAdd");
        f1798f.lazySet(iVar, this);
        e.lazySet(iVar, iVar2);
        aVar.b = iVar2;
        if (!e.compareAndSet(this, iVar2, aVar)) {
            return 0;
        }
        return aVar.a(this) == null ? 1 : 2;
    }

    private final i a(i iVar, o oVar) {
        Object obj;
        while (true) {
            i iVar2 = null;
            while (true) {
                obj = iVar._next;
                if (obj == oVar) {
                    return iVar;
                }
                if (obj instanceof o) {
                    ((o) obj).a(iVar);
                } else if (!(obj instanceof p)) {
                    Object obj2 = this._prev;
                    if (obj2 instanceof p) {
                        return null;
                    }
                    if (obj != this) {
                        if (obj != null) {
                            iVar2 = iVar;
                            iVar = (i) obj;
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                        }
                    } else if (obj2 == iVar) {
                        return null;
                    } else {
                        if (f1798f.compareAndSet(this, obj2, iVar) && !(iVar._prev instanceof p)) {
                            return null;
                        }
                    }
                } else if (iVar2 != null) {
                    break;
                } else {
                    iVar = h.a(iVar._prev);
                }
            }
            iVar.k();
            e.compareAndSet(iVar2, iVar, ((p) obj).a);
            iVar = iVar2;
        }
    }
}
