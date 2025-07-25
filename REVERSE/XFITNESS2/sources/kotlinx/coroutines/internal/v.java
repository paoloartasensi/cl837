package kotlinx.coroutines.internal;

import kotlin.jvm.internal.i;

/* compiled from: SystemProps.kt */
final /* synthetic */ class v {
    private static final int a = Runtime.getRuntime().availableProcessors();

    public static final int a() {
        return a;
    }

    public static final String a(String str) {
        i.b(str, "propertyName");
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            return null;
        }
    }
}
