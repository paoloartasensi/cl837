package kotlin.jvm.internal;

import kotlin.reflect.d;

public class PropertyReference1Impl extends PropertyReference1 {
    private final String name;
    private final d owner;
    private final String signature;

    public PropertyReference1Impl(d dVar, String str, String str2) {
        this.owner = dVar;
        this.name = str;
        this.signature = str2;
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [kotlin.reflect.i$a, kotlin.reflect.b] */
    public Object get(Object obj) {
        return getGetter().call(obj);
    }

    public String getName() {
        return this.name;
    }

    public d getOwner() {
        return this.owner;
    }

    public String getSignature() {
        return this.signature;
    }
}
