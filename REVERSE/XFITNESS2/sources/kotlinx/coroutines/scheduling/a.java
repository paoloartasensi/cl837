package kotlinx.coroutines.scheduling;

import kotlinx.coroutines.scheduling.CoroutineScheduler;

public final /* synthetic */ class a {
    public static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[CoroutineScheduler.WorkerState.values().length];
        a = iArr;
        iArr[CoroutineScheduler.WorkerState.PARKING.ordinal()] = 1;
        a[CoroutineScheduler.WorkerState.BLOCKING.ordinal()] = 2;
        a[CoroutineScheduler.WorkerState.CPU_ACQUIRED.ordinal()] = 3;
        a[CoroutineScheduler.WorkerState.RETIRING.ordinal()] = 4;
        a[CoroutineScheduler.WorkerState.TERMINATED.ordinal()] = 5;
    }
}
