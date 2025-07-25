package kotlinx.coroutines;

public final /* synthetic */ class i0 {
    public static final /* synthetic */ int[] a;
    public static final /* synthetic */ int[] b;

    static {
        int[] iArr = new int[CoroutineStart.values().length];
        a = iArr;
        iArr[CoroutineStart.DEFAULT.ordinal()] = 1;
        a[CoroutineStart.ATOMIC.ordinal()] = 2;
        a[CoroutineStart.UNDISPATCHED.ordinal()] = 3;
        a[CoroutineStart.LAZY.ordinal()] = 4;
        int[] iArr2 = new int[CoroutineStart.values().length];
        b = iArr2;
        iArr2[CoroutineStart.DEFAULT.ordinal()] = 1;
        b[CoroutineStart.ATOMIC.ordinal()] = 2;
        b[CoroutineStart.UNDISPATCHED.ordinal()] = 3;
        b[CoroutineStart.LAZY.ordinal()] = 4;
    }
}
