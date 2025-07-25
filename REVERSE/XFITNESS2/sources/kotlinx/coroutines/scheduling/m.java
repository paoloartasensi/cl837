package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.jvm.internal.i;

/* compiled from: WorkQueue.kt */
public final class m {
    private static final AtomicReferenceFieldUpdater b;
    static final AtomicIntegerFieldUpdater c;
    static final AtomicIntegerFieldUpdater d;
    /* access modifiers changed from: private */
    public final AtomicReferenceArray<h> a = new AtomicReferenceArray<>(128);
    volatile int consumerIndex = 0;
    private volatile Object lastScheduledTask = null;
    volatile int producerIndex = 0;

    static {
        Class<m> cls = m.class;
        b = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "lastScheduledTask");
        c = AtomicIntegerFieldUpdater.newUpdater(cls, "producerIndex");
        d = AtomicIntegerFieldUpdater.newUpdater(cls, "consumerIndex");
    }

    public final h b() {
        h hVar = (h) b.getAndSet(this, (Object) null);
        if (hVar != null) {
            return hVar;
        }
        while (true) {
            int i2 = this.consumerIndex;
            if (i2 - this.producerIndex == 0) {
                return null;
            }
            int i3 = i2 & 127;
            if (((h) this.a.get(i3)) != null && d.compareAndSet(this, i2, i2 + 1)) {
                return (h) this.a.getAndSet(i3, (Object) null);
            }
        }
    }

    public final int c() {
        return this.lastScheduledTask != null ? a() + 1 : a();
    }

    public final int a() {
        return this.producerIndex - this.consumerIndex;
    }

    public final boolean a(h hVar, d dVar) {
        i.b(hVar, "task");
        i.b(dVar, "globalQueue");
        h hVar2 = (h) b.getAndSet(this, hVar);
        if (hVar2 != null) {
            return b(hVar2, dVar);
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: kotlinx.coroutines.scheduling.h} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(kotlinx.coroutines.scheduling.m r19, kotlinx.coroutines.scheduling.d r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            java.lang.String r3 = "victim"
            kotlin.jvm.internal.i.b(r1, r3)
            java.lang.String r3 = "globalQueue"
            kotlin.jvm.internal.i.b(r2, r3)
            kotlinx.coroutines.scheduling.l r3 = kotlinx.coroutines.scheduling.k.f1842f
            long r3 = r3.a()
            int r5 = r19.a()
            if (r5 != 0) goto L_0x0021
            boolean r1 = r0.a(r3, r1, r2)
            return r1
        L_0x0021:
            int r5 = r5 / 2
            r6 = 1
            int r5 = kotlin.q.f.a((int) r5, (int) r6)
            r7 = 0
            r8 = 0
            r9 = 0
        L_0x002b:
            if (r8 >= r5) goto L_0x007c
        L_0x002d:
            int r10 = r1.consumerIndex
            int r11 = r1.producerIndex
            int r11 = r10 - r11
            r12 = 0
            if (r11 != 0) goto L_0x0037
            goto L_0x0073
        L_0x0037:
            r11 = r10 & 127(0x7f, float:1.78E-43)
            java.util.concurrent.atomic.AtomicReferenceArray r13 = r19.a
            java.lang.Object r13 = r13.get(r11)
            kotlinx.coroutines.scheduling.h r13 = (kotlinx.coroutines.scheduling.h) r13
            if (r13 == 0) goto L_0x002d
            long r13 = r13.e
            long r13 = r3 - r13
            long r15 = kotlinx.coroutines.scheduling.k.a
            int r17 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r17 >= 0) goto L_0x005a
            int r13 = r19.a()
            int r14 = kotlinx.coroutines.scheduling.k.b
            if (r13 <= r14) goto L_0x0058
            goto L_0x005a
        L_0x0058:
            r13 = 0
            goto L_0x005b
        L_0x005a:
            r13 = 1
        L_0x005b:
            if (r13 != 0) goto L_0x005e
            goto L_0x0073
        L_0x005e:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r13 = d
            int r14 = r10 + 1
            boolean r10 = r13.compareAndSet(r1, r10, r14)
            if (r10 == 0) goto L_0x002d
            java.util.concurrent.atomic.AtomicReferenceArray r10 = r19.a
            java.lang.Object r10 = r10.getAndSet(r11, r12)
            r12 = r10
            kotlinx.coroutines.scheduling.h r12 = (kotlinx.coroutines.scheduling.h) r12
        L_0x0073:
            if (r12 == 0) goto L_0x007c
            r0.a((kotlinx.coroutines.scheduling.h) r12, (kotlinx.coroutines.scheduling.d) r2)
            int r8 = r8 + 1
            r9 = 1
            goto L_0x002b
        L_0x007c:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.m.a(kotlinx.coroutines.scheduling.m, kotlinx.coroutines.scheduling.d):boolean");
    }

    public final boolean b(h hVar, d dVar) {
        i.b(hVar, "task");
        i.b(dVar, "globalQueue");
        boolean z = true;
        while (!a(hVar)) {
            b(dVar);
            z = false;
        }
        return z;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: kotlinx.coroutines.scheduling.h} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void b(kotlinx.coroutines.scheduling.d r8) {
        /*
            r7 = this;
            int r0 = r7.a()
            int r0 = r0 / 2
            r1 = 1
            int r0 = kotlin.q.f.a((int) r0, (int) r1)
            r1 = 0
        L_0x000c:
            if (r1 >= r0) goto L_0x0043
        L_0x000e:
            int r2 = r7.consumerIndex
            int r3 = r7.producerIndex
            int r3 = r2 - r3
            r4 = 0
            if (r3 != 0) goto L_0x0018
            goto L_0x003b
        L_0x0018:
            r3 = r2 & 127(0x7f, float:1.78E-43)
            java.util.concurrent.atomic.AtomicReferenceArray r5 = r7.a
            java.lang.Object r5 = r5.get(r3)
            kotlinx.coroutines.scheduling.h r5 = (kotlinx.coroutines.scheduling.h) r5
            if (r5 == 0) goto L_0x000e
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = d
            int r6 = r2 + 1
            boolean r2 = r5.compareAndSet(r7, r2, r6)
            if (r2 == 0) goto L_0x000e
            java.util.concurrent.atomic.AtomicReferenceArray r2 = r7.a
            java.lang.Object r2 = r2.getAndSet(r3, r4)
            r4 = r2
            kotlinx.coroutines.scheduling.h r4 = (kotlinx.coroutines.scheduling.h) r4
        L_0x003b:
            if (r4 == 0) goto L_0x0043
            r7.a((kotlinx.coroutines.scheduling.d) r8, (kotlinx.coroutines.scheduling.h) r4)
            int r1 = r1 + 1
            goto L_0x000c
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.m.b(kotlinx.coroutines.scheduling.d):void");
    }

    private final boolean a(long j2, m mVar, d dVar) {
        h hVar = (h) mVar.lastScheduledTask;
        if (hVar == null || j2 - hVar.e < k.a || !b.compareAndSet(mVar, hVar, (Object) null)) {
            return false;
        }
        a(hVar, dVar);
        return true;
    }

    private final void a(d dVar, h hVar) {
        if (!dVar.a(hVar)) {
            throw new IllegalStateException("GlobalQueue could not be closed yet".toString());
        }
    }

    public final void a(d dVar) {
        h hVar;
        i.b(dVar, "globalQueue");
        h hVar2 = (h) b.getAndSet(this, (Object) null);
        if (hVar2 != null) {
            a(dVar, hVar2);
        }
        while (true) {
            int i2 = this.consumerIndex;
            if (i2 - this.producerIndex == 0) {
                hVar = null;
            } else {
                int i3 = i2 & 127;
                if (((h) this.a.get(i3)) != null && d.compareAndSet(this, i2, i2 + 1)) {
                    hVar = (h) this.a.getAndSet(i3, (Object) null);
                }
            }
            if (hVar != null) {
                a(dVar, hVar);
            } else {
                return;
            }
        }
    }

    private final boolean a(h hVar) {
        if (a() == 127) {
            return false;
        }
        int i2 = this.producerIndex & 127;
        if (this.a.get(i2) != null) {
            return false;
        }
        this.a.lazySet(i2, hVar);
        c.incrementAndGet(this);
        return true;
    }
}
