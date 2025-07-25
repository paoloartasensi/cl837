package kotlin.jvm.internal;

import kotlin.reflect.f;

public abstract class MutablePropertyReference extends PropertyReference implements f {
    public MutablePropertyReference() {
    }

    public MutablePropertyReference(Object obj) {
        super(obj);
    }
}
