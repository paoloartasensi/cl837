package kotlin;

import java.io.Serializable;
import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;

/* compiled from: Lazy.kt */
public final class UnsafeLazyImpl<T> implements d<T>, Serializable {
    private Object _value = k.a;
    private a<? extends T> initializer;

    public UnsafeLazyImpl(a<? extends T> aVar) {
        i.b(aVar, "initializer");
        this.initializer = aVar;
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }

    public T getValue() {
        if (this._value == k.a) {
            a<? extends T> aVar = this.initializer;
            if (aVar != null) {
                this._value = aVar.invoke();
                this.initializer = null;
            } else {
                i.a();
                throw null;
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
