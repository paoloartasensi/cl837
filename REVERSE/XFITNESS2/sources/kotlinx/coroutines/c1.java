package kotlinx.coroutines;

import kotlinx.coroutines.internal.t;

/* compiled from: EventLoop.common.kt */
public final class c1 {
    /* access modifiers changed from: private */
    public static final t a = new t("REMOVED_TASK");
    /* access modifiers changed from: private */
    public static final t b = new t("CLOSED_EMPTY");

    public static final long a(long j2) {
        if (j2 <= 0) {
            return 0;
        }
        if (j2 >= 9223372036854L) {
            return Long.MAX_VALUE;
        }
        return 1000000 * j2;
    }
}
