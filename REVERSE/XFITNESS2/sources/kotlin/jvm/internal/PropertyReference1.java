package kotlin.jvm.internal;

import kotlin.reflect.b;
import kotlin.reflect.i;

public abstract class PropertyReference1 extends PropertyReference implements i {
    public PropertyReference1() {
    }

    /* access modifiers changed from: protected */
    public b computeReflected() {
        k.a(this);
        return this;
    }

    public Object getDelegate(Object obj) {
        return ((i) getReflected()).getDelegate(obj);
    }

    public Object invoke(Object obj) {
        return get(obj);
    }

    public PropertyReference1(Object obj) {
        super(obj);
    }

    public i.a getGetter() {
        return ((i) getReflected()).getGetter();
    }
}
