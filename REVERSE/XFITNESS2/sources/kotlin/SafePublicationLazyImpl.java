package kotlin;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.i;

/* compiled from: LazyJVM.kt */
final class SafePublicationLazyImpl<T> implements d<T>, Serializable {
    public static final a Companion = new a((f) null);
    private static final AtomicReferenceFieldUpdater<SafePublicationLazyImpl<?>, Object> e = AtomicReferenceFieldUpdater.newUpdater(SafePublicationLazyImpl.class, Object.class, "_value");
    private volatile Object _value = k.a;

    /* renamed from: final  reason: not valid java name */
    private final Object f0final = k.a;
    private volatile kotlin.jvm.b.a<? extends T> initializer;

    /* compiled from: LazyJVM.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    public SafePublicationLazyImpl(kotlin.jvm.b.a<? extends T> aVar) {
        i.b(aVar, "initializer");
        this.initializer = aVar;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    public T getValue() {
        T t = this._value;
        if (t != k.a) {
            return t;
        }
        kotlin.jvm.b.a<? extends T> aVar = this.initializer;
        if (aVar != null) {
            T invoke = aVar.invoke();
            if (e.compareAndSet(this, k.a, invoke)) {
                this.initializer = null;
                return invoke;
            }
        }
        return this._value;
    }

    public boolean isInitialized() {
        return this._value != k.a;
    }

    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
