package kotlinx.coroutines.internal;

import kotlin.jvm.internal.i;

/* compiled from: Symbol.kt */
public final class t {
    private final String a;

    public t(String str) {
        i.b(str, "symbol");
        this.a = str;
    }

    public String toString() {
        return this.a;
    }
}
