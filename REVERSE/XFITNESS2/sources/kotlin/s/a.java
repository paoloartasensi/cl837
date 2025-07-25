package kotlin.s;

import kotlin.q.c;

/* compiled from: CharJVM.kt */
class a {
    public static final boolean a(char c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c);
    }

    public static final int a(char c, int i2) {
        return Character.digit(c, i2);
    }

    public static final int a(int i2) {
        if (2 <= i2 && 36 >= i2) {
            return i2;
        }
        throw new IllegalArgumentException("radix " + i2 + " was not in valid range " + new c(2, 36));
    }
}
