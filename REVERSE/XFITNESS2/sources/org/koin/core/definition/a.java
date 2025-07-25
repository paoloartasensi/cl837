package org.koin.core.definition;

public final /* synthetic */ class a {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[Kind.values().length];
        a = iArr;
        iArr[Kind.Single.ordinal()] = 1;
        a[Kind.Factory.ordinal()] = 2;
        a[Kind.Scoped.ordinal()] = 3;
    }
}
