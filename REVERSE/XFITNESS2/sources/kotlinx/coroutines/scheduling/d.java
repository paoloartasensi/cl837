package kotlinx.coroutines.scheduling;

import kotlinx.coroutines.internal.j;

/* compiled from: Tasks.kt */
public class d extends j<h> {
    public d() {
        super(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008f, code lost:
        r7 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlinx.coroutines.scheduling.h a(kotlinx.coroutines.scheduling.TaskMode r12) {
        /*
            r11 = this;
            java.lang.String r0 = "mode"
            kotlin.jvm.internal.i.b(r12, r0)
        L_0x0005:
            java.lang.Object r0 = r11._cur$internal
            kotlinx.coroutines.internal.k r0 = (kotlinx.coroutines.internal.k) r0
        L_0x0009:
            long r3 = r0._state$internal
            r1 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r1 = r1 & r3
            r5 = 0
            r7 = 0
            int r8 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r8 == 0) goto L_0x0019
            kotlinx.coroutines.internal.t r7 = kotlinx.coroutines.internal.k.f1801g
            goto L_0x0090
        L_0x0019:
            kotlinx.coroutines.internal.k$a r1 = kotlinx.coroutines.internal.k.f1802h
            r1 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r1 = r1 & r3
            r5 = 0
            long r1 = r1 >> r5
            int r8 = (int) r1
            r1 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r1 = r1 & r3
            r6 = 30
            long r1 = r1 >> r6
            int r2 = (int) r1
            int r1 = r0.a
            r1 = r1 & r2
            int r2 = r0.a
            r2 = r2 & r8
            if (r1 != r2) goto L_0x0039
            goto L_0x0090
        L_0x0039:
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r0.b
            int r2 = r0.a
            r2 = r2 & r8
            java.lang.Object r9 = r1.get(r2)
            if (r9 != 0) goto L_0x004d
            boolean r1 = r0.d
            if (r1 == 0) goto L_0x0009
            goto L_0x0090
        L_0x004d:
            boolean r1 = r9 instanceof kotlinx.coroutines.internal.k.b
            if (r1 == 0) goto L_0x0052
            goto L_0x0090
        L_0x0052:
            r1 = r9
            kotlinx.coroutines.scheduling.h r1 = (kotlinx.coroutines.scheduling.h) r1
            kotlinx.coroutines.scheduling.TaskMode r1 = r1.a()
            if (r1 != r12) goto L_0x005c
            r5 = 1
        L_0x005c:
            if (r5 != 0) goto L_0x005f
            goto L_0x0090
        L_0x005f:
            int r1 = r8 + 1
            r2 = 1073741823(0x3fffffff, float:1.9999999)
            r10 = r1 & r2
            java.util.concurrent.atomic.AtomicLongFieldUpdater r1 = kotlinx.coroutines.internal.k.f1800f
            kotlinx.coroutines.internal.k$a r2 = kotlinx.coroutines.internal.k.f1802h
            long r5 = r2.a((long) r3, (int) r10)
            r2 = r0
            boolean r1 = r1.compareAndSet(r2, r3, r5)
            if (r1 == 0) goto L_0x0080
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r0.b
            int r2 = r0.a
            r2 = r2 & r8
            r1.set(r2, r7)
            goto L_0x008f
        L_0x0080:
            boolean r1 = r0.d
            if (r1 != 0) goto L_0x0087
            goto L_0x0009
        L_0x0087:
            r1 = r0
        L_0x0088:
            kotlinx.coroutines.internal.k r1 = r1.a((int) r8, (int) r10)
            if (r1 == 0) goto L_0x008f
            goto L_0x0088
        L_0x008f:
            r7 = r9
        L_0x0090:
            kotlinx.coroutines.internal.t r1 = kotlinx.coroutines.internal.k.f1801g
            if (r7 == r1) goto L_0x0097
            kotlinx.coroutines.scheduling.h r7 = (kotlinx.coroutines.scheduling.h) r7
            return r7
        L_0x0097:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = kotlinx.coroutines.internal.j.a
            kotlinx.coroutines.internal.k r2 = r0.d()
            r1.compareAndSet(r11, r0, r2)
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.d.a(kotlinx.coroutines.scheduling.TaskMode):kotlinx.coroutines.scheduling.h");
    }
}
