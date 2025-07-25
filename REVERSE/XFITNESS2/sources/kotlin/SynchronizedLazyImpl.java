package kotlin;

import java.io.Serializable;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;

/* compiled from: LazyJVM.kt */
final class SynchronizedLazyImpl<T> implements d<T>, Serializable {
    private volatile Object _value;
    private a<? extends T> initializer;
    private final Object lock;

    public SynchronizedLazyImpl(a<? extends T> aVar, Object obj) {
        i.b(aVar, "initializer");
        this.initializer = aVar;
        this._value = k.a;
        this.lock = obj == null ? this : obj;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    public T getValue() {
        T t;
        T t2 = this._value;
        if (t2 != k.a) {
            return t2;
        }
        synchronized (this.lock) {
            t = this._value;
            if (t == k.a) {
                a aVar = this.initializer;
                if (aVar != null) {
                    t = aVar.invoke();
                    this._value = t;
                    this.initializer = null;
                } else {
                    i.a();
                    throw null;
                }
            }
        }
        return t;
    }

    public boolean isInitialized() {
        return this._value != k.a;
    }

    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ SynchronizedLazyImpl(a aVar, Object obj, int i2, f fVar) {
        this(aVar, (i2 & 2) != 0 ? null : obj);
    }
}
