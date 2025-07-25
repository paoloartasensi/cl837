package kotlin.jvm.internal;

import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.e;
import kotlin.reflect.g;
import kotlin.reflect.i;

/* compiled from: Reflection */
public class k {
    private static final l a;

    static {
        l lVar = null;
        try {
            lVar = (l) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (lVar == null) {
            lVar = new l();
        }
        a = lVar;
    }

    public static d a(Class cls, String str) {
        return a.a(cls, str);
    }

    public static c a(Class cls) {
        return a.a(cls);
    }

    public static String a(Lambda lambda) {
        return a.a(lambda);
    }

    public static String a(h hVar) {
        return a.a(hVar);
    }

    public static e a(FunctionReference functionReference) {
        a.a(functionReference);
        return functionReference;
    }

    public static i a(PropertyReference1 propertyReference1) {
        a.a(propertyReference1);
        return propertyReference1;
    }

    public static g a(MutablePropertyReference1 mutablePropertyReference1) {
        a.a(mutablePropertyReference1);
        return mutablePropertyReference1;
    }
}
