package kotlin.reflect;

import kotlin.jvm.b.l;
import kotlin.reflect.h;

/* compiled from: KProperty.kt */
public interface i<T, R> extends h<R>, l<T, R> {

    /* compiled from: KProperty.kt */
    public interface a<T, R> extends h.a<R>, l<T, R> {
    }

    R get(T t);

    Object getDelegate(T t);

    a<T, R> getGetter();
}
