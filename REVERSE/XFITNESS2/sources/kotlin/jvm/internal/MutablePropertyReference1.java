package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.g;
import kotlin.reflect.i;

public abstract class MutablePropertyReference1 extends MutablePropertyReference implements g {
    public MutablePropertyReference1() {
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        k.a(this);
        return this;
    }

    public Object getDelegate(Object obj) {
        return ((g) getReflected()).getDelegate(obj);
    }

    public Object invoke(Object obj) {
        return get(obj);
    }

    public MutablePropertyReference1(Object obj) {
        super(obj);
    }

    public i.a getGetter() {
        return ((g) getReflected()).getGetter();
    }

    public g.a getSetter() {
        return ((g) getReflected()).getSetter();
    }
}
