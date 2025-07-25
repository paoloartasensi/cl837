package kotlin;

import java.io.Serializable;

/* compiled from: Lazy.kt */
public final class InitializedLazyImpl<T> implements d<T>, Serializable {
    private final T value;

    public InitializedLazyImpl(T t) {
        this.value = t;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isInitialized() {
        return true;
    }

    public String toString() {
        return String.valueOf(getValue());
    }
}
