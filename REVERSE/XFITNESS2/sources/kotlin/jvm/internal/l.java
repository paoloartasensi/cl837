package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.e;
import kotlin.reflect.g;
import kotlin.reflect.i;

/* compiled from: ReflectionFactory */
public class l {
    public d a(Class cls, String str) {
        return new j(cls, str);
    }

    public e a(FunctionReference functionReference) {
        return functionReference;
    }

    public g a(MutablePropertyReference1 mutablePropertyReference1) {
        return mutablePropertyReference1;
    }

    public i a(PropertyReference1 propertyReference1) {
        return propertyReference1;
    }

    public c a(Class cls) {
        return new d(cls);
    }

    public String a(Lambda lambda) {
        return a((h) lambda);
    }

    public String a(h hVar) {
        String obj = hVar.getClass().getGenericInterfaces()[0].toString();
        return obj.startsWith("kotlin.jvm.functions.") ? obj.substring(21) : obj;
    }
}
