package i.a.a.a;

import org.koin.core.logger.Level;

public final /* synthetic */ class a {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[Level.values().length];
        a = iArr;
        iArr[Level.DEBUG.ordinal()] = 1;
        a[Level.INFO.ordinal()] = 2;
        a[Level.ERROR.ordinal()] = 3;
    }
}
