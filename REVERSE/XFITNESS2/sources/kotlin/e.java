package kotlin;

public final /* synthetic */ class e {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[LazyThreadSafetyMode.values().length];
        a = iArr;
        iArr[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
        a[LazyThreadSafetyMode.PUBLICATION.ordinal()] = 2;
        a[LazyThreadSafetyMode.NONE.ordinal()] = 3;
    }
}
