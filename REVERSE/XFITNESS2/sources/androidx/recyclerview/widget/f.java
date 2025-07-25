package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: DiffUtil */
public class f {
    private static final Comparator<C0044f> a = new a();

    /* compiled from: DiffUtil */
    static class a implements Comparator<C0044f> {
        a() {
        }

        /* renamed from: a */
        public int compare(C0044f fVar, C0044f fVar2) {
            int i2 = fVar.a - fVar2.a;
            return i2 == 0 ? fVar.b - fVar2.b : i2;
        }
    }

    /* compiled from: DiffUtil */
    public static abstract class b {
        public abstract boolean areContentsTheSame(int i2, int i3);

        public abstract boolean areItemsTheSame(int i2, int i3);

        public Object getChangePayload(int i2, int i3) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    /* compiled from: DiffUtil */
    private static class d {
        int a;
        int b;
        boolean c;

        public d(int i2, int i3, boolean z) {
            this.a = i2;
            this.b = i3;
            this.c = z;
        }
    }

    /* compiled from: DiffUtil */
    static class e {
        int a;
        int b;
        int c;
        int d;

        public e() {
        }

        public e(int i2, int i3, int i4, int i5) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = i5;
        }
    }

    /* renamed from: androidx.recyclerview.widget.f$f  reason: collision with other inner class name */
    /* compiled from: DiffUtil */
    static class C0044f {
        int a;
        int b;
        int c;
        boolean d;
        boolean e;

        C0044f() {
        }
    }

    public static c a(b bVar) {
        return a(bVar, true);
    }

    public static c a(b bVar, boolean z) {
        e eVar;
        int oldListSize = bVar.getOldListSize();
        int newListSize = bVar.getNewListSize();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new e(0, oldListSize, 0, newListSize));
        int abs = oldListSize + newListSize + Math.abs(oldListSize - newListSize);
        int i2 = abs * 2;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        ArrayList arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            e eVar2 = (e) arrayList2.remove(arrayList2.size() - 1);
            C0044f a2 = a(bVar, eVar2.a, eVar2.b, eVar2.c, eVar2.d, iArr, iArr2, abs);
            if (a2 != null) {
                if (a2.c > 0) {
                    arrayList.add(a2);
                }
                a2.a += eVar2.a;
                a2.b += eVar2.c;
                if (arrayList3.isEmpty()) {
                    eVar = new e();
                } else {
                    eVar = (e) arrayList3.remove(arrayList3.size() - 1);
                }
                eVar.a = eVar2.a;
                eVar.c = eVar2.c;
                if (a2.e) {
                    eVar.b = a2.a;
                    eVar.d = a2.b;
                } else if (a2.d) {
                    eVar.b = a2.a - 1;
                    eVar.d = a2.b;
                } else {
                    eVar.b = a2.a;
                    eVar.d = a2.b - 1;
                }
                arrayList2.add(eVar);
                if (!a2.e) {
                    int i3 = a2.a;
                    int i4 = a2.c;
                    eVar2.a = i3 + i4;
                    eVar2.c = a2.b + i4;
                } else if (a2.d) {
                    int i5 = a2.a;
                    int i6 = a2.c;
                    eVar2.a = i5 + i6 + 1;
                    eVar2.c = a2.b + i6;
                } else {
                    int i7 = a2.a;
                    int i8 = a2.c;
                    eVar2.a = i7 + i8;
                    eVar2.c = a2.b + i8 + 1;
                }
                arrayList2.add(eVar2);
            } else {
                arrayList3.add(eVar2);
            }
        }
        Collections.sort(arrayList, a);
        return new c(bVar, arrayList, iArr, iArr2, z);
    }

    /* compiled from: DiffUtil */
    public static class c {
        private final List<C0044f> a;
        private final int[] b;
        private final int[] c;
        private final b d;
        private final int e;

        /* renamed from: f  reason: collision with root package name */
        private final int f832f;

        /* renamed from: g  reason: collision with root package name */
        private final boolean f833g;

        c(b bVar, List<C0044f> list, int[] iArr, int[] iArr2, boolean z) {
            this.a = list;
            this.b = iArr;
            this.c = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(this.c, 0);
            this.d = bVar;
            this.e = bVar.getOldListSize();
            this.f832f = bVar.getNewListSize();
            this.f833g = z;
            a();
            b();
        }

        private void a() {
            C0044f fVar = this.a.isEmpty() ? null : this.a.get(0);
            if (fVar == null || fVar.a != 0 || fVar.b != 0) {
                C0044f fVar2 = new C0044f();
                fVar2.a = 0;
                fVar2.b = 0;
                fVar2.d = false;
                fVar2.c = 0;
                fVar2.e = false;
                this.a.add(0, fVar2);
            }
        }

        private void b() {
            int i2 = this.e;
            int i3 = this.f832f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                C0044f fVar = this.a.get(size);
                int i4 = fVar.a;
                int i5 = fVar.c;
                int i6 = i4 + i5;
                int i7 = fVar.b + i5;
                if (this.f833g) {
                    while (i2 > i6) {
                        a(i2, i3, size);
                        i2--;
                    }
                    while (i3 > i7) {
                        b(i2, i3, size);
                        i3--;
                    }
                }
                for (int i8 = 0; i8 < fVar.c; i8++) {
                    int i9 = fVar.a + i8;
                    int i10 = fVar.b + i8;
                    int i11 = this.d.areContentsTheSame(i9, i10) ? 1 : 2;
                    this.b[i9] = (i10 << 5) | i11;
                    this.c[i10] = (i9 << 5) | i11;
                }
                i2 = fVar.a;
                i3 = fVar.b;
            }
        }

        private void a(int i2, int i3, int i4) {
            if (this.b[i2 - 1] == 0) {
                a(i2, i3, i4, false);
            }
        }

        private boolean a(int i2, int i3, int i4, boolean z) {
            int i5;
            int i6;
            if (z) {
                i3--;
                i5 = i2;
                i6 = i3;
            } else {
                i6 = i2 - 1;
                i5 = i6;
            }
            while (i4 >= 0) {
                C0044f fVar = this.a.get(i4);
                int i7 = fVar.a;
                int i8 = fVar.c;
                int i9 = i7 + i8;
                int i10 = fVar.b + i8;
                int i11 = 8;
                if (z) {
                    for (int i12 = i5 - 1; i12 >= i9; i12--) {
                        if (this.d.areItemsTheSame(i12, i6)) {
                            if (!this.d.areContentsTheSame(i12, i6)) {
                                i11 = 4;
                            }
                            this.c[i6] = (i12 << 5) | 16;
                            this.b[i12] = (i6 << 5) | i11;
                            return true;
                        }
                    }
                    continue;
                } else {
                    for (int i13 = i3 - 1; i13 >= i10; i13--) {
                        if (this.d.areItemsTheSame(i6, i13)) {
                            if (!this.d.areContentsTheSame(i6, i13)) {
                                i11 = 4;
                            }
                            int i14 = i2 - 1;
                            this.b[i14] = (i13 << 5) | 16;
                            this.c[i13] = (i14 << 5) | i11;
                            return true;
                        }
                    }
                    continue;
                }
                i5 = fVar.a;
                i3 = fVar.b;
                i4--;
            }
            return false;
        }

        private void b(int i2, int i3, int i4) {
            if (this.c[i3 - 1] == 0) {
                a(i2, i3, i4, true);
            }
        }

        private void b(List<d> list, o oVar, int i2, int i3, int i4) {
            if (!this.f833g) {
                oVar.onRemoved(i2, i3);
                return;
            }
            for (int i5 = i3 - 1; i5 >= 0; i5--) {
                int i6 = i4 + i5;
                int i7 = this.b[i6] & 31;
                if (i7 == 0) {
                    oVar.onRemoved(i2 + i5, 1);
                    for (d dVar : list) {
                        dVar.b--;
                    }
                } else if (i7 == 4 || i7 == 8) {
                    int i8 = this.b[i6] >> 5;
                    d a2 = a(list, i8, false);
                    oVar.onMoved(i2 + i5, a2.b - 1);
                    if (i7 == 4) {
                        oVar.onChanged(a2.b - 1, 1, this.d.getChangePayload(i6, i8));
                    }
                } else if (i7 == 16) {
                    list.add(new d(i6, i2 + i5, true));
                } else {
                    throw new IllegalStateException("unknown flag for pos " + i6 + " " + Long.toBinaryString((long) i7));
                }
            }
        }

        public void a(RecyclerView.g gVar) {
            a((o) new b(gVar));
        }

        public void a(o oVar) {
            c cVar;
            if (oVar instanceof c) {
                cVar = (c) oVar;
            } else {
                cVar = new c(oVar);
            }
            ArrayList arrayList = new ArrayList();
            int i2 = this.e;
            int i3 = this.f832f;
            for (int size = this.a.size() - 1; size >= 0; size--) {
                C0044f fVar = this.a.get(size);
                int i4 = fVar.c;
                int i5 = fVar.a + i4;
                int i6 = fVar.b + i4;
                if (i5 < i2) {
                    b(arrayList, cVar, i5, i2 - i5, i5);
                }
                if (i6 < i3) {
                    a(arrayList, cVar, i5, i3 - i6, i6);
                }
                for (int i7 = i4 - 1; i7 >= 0; i7--) {
                    int[] iArr = this.b;
                    int i8 = fVar.a;
                    if ((iArr[i8 + i7] & 31) == 2) {
                        cVar.onChanged(i8 + i7, 1, this.d.getChangePayload(i8 + i7, fVar.b + i7));
                    }
                }
                i2 = fVar.a;
                i3 = fVar.b;
            }
            cVar.a();
        }

        private static d a(List<d> list, int i2, boolean z) {
            int size = list.size() - 1;
            while (size >= 0) {
                d dVar = list.get(size);
                if (dVar.a == i2 && dVar.c == z) {
                    list.remove(size);
                    while (size < list.size()) {
                        list.get(size).b += z ? 1 : -1;
                        size++;
                    }
                    return dVar;
                }
                size--;
            }
            return null;
        }

        private void a(List<d> list, o oVar, int i2, int i3, int i4) {
            if (!this.f833g) {
                oVar.onInserted(i2, i3);
                return;
            }
            for (int i5 = i3 - 1; i5 >= 0; i5--) {
                int i6 = i4 + i5;
                int i7 = this.c[i6] & 31;
                if (i7 == 0) {
                    oVar.onInserted(i2, 1);
                    for (d dVar : list) {
                        dVar.b++;
                    }
                } else if (i7 == 4 || i7 == 8) {
                    int i8 = this.c[i6] >> 5;
                    oVar.onMoved(a(list, i8, true).b, i2);
                    if (i7 == 4) {
                        oVar.onChanged(i2, 1, this.d.getChangePayload(i8, i6));
                    }
                } else if (i7 == 16) {
                    list.add(new d(i6, i2, false));
                } else {
                    throw new IllegalStateException("unknown flag for pos " + i6 + " " + Long.toBinaryString((long) i7));
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        if (r1[r13 - 1] < r1[r13 + r5]) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b8, code lost:
        if (r2[r12 - 1] < r2[r12 + 1]) goto L_0x00c5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x009a A[LOOP:1: B:10:0x0033->B:33:0x009a, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0081 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.recyclerview.widget.f.C0044f a(androidx.recyclerview.widget.f.b r19, int r20, int r21, int r22, int r23, int[] r24, int[] r25, int r26) {
        /*
            r0 = r19
            r1 = r24
            r2 = r25
            int r3 = r21 - r20
            int r4 = r23 - r22
            r5 = 1
            if (r3 < r5) goto L_0x012f
            if (r4 >= r5) goto L_0x0011
            goto L_0x012f
        L_0x0011:
            int r6 = r3 - r4
            int r7 = r3 + r4
            int r7 = r7 + r5
            int r7 = r7 / 2
            int r8 = r26 - r7
            int r8 = r8 - r5
            int r9 = r26 + r7
            int r9 = r9 + r5
            r10 = 0
            java.util.Arrays.fill(r1, r8, r9, r10)
            int r8 = r8 + r6
            int r9 = r9 + r6
            java.util.Arrays.fill(r2, r8, r9, r3)
            int r8 = r6 % 2
            if (r8 == 0) goto L_0x002d
            r8 = 1
            goto L_0x002e
        L_0x002d:
            r8 = 0
        L_0x002e:
            r9 = 0
        L_0x002f:
            if (r9 > r7) goto L_0x0127
            int r11 = -r9
            r12 = r11
        L_0x0033:
            if (r12 > r9) goto L_0x00a0
            if (r12 == r11) goto L_0x004d
            if (r12 == r9) goto L_0x0045
            int r13 = r26 + r12
            int r14 = r13 + -1
            r14 = r1[r14]
            int r13 = r13 + r5
            r13 = r1[r13]
            if (r14 >= r13) goto L_0x0045
            goto L_0x004d
        L_0x0045:
            int r13 = r26 + r12
            int r13 = r13 - r5
            r13 = r1[r13]
            int r13 = r13 + r5
            r14 = 1
            goto L_0x0053
        L_0x004d:
            int r13 = r26 + r12
            int r13 = r13 + r5
            r13 = r1[r13]
            r14 = 0
        L_0x0053:
            int r15 = r13 - r12
        L_0x0055:
            if (r13 >= r3) goto L_0x006a
            if (r15 >= r4) goto L_0x006a
            int r10 = r20 + r13
            int r5 = r22 + r15
            boolean r5 = r0.areItemsTheSame(r10, r5)
            if (r5 == 0) goto L_0x006a
            int r13 = r13 + 1
            int r15 = r15 + 1
            r5 = 1
            r10 = 0
            goto L_0x0055
        L_0x006a:
            int r5 = r26 + r12
            r1[r5] = r13
            if (r8 == 0) goto L_0x009a
            int r10 = r6 - r9
            r13 = 1
            int r10 = r10 + r13
            if (r12 < r10) goto L_0x009a
            int r10 = r6 + r9
            int r10 = r10 - r13
            if (r12 > r10) goto L_0x009a
            r10 = r1[r5]
            r13 = r2[r5]
            if (r10 < r13) goto L_0x009a
            androidx.recyclerview.widget.f$f r0 = new androidx.recyclerview.widget.f$f
            r0.<init>()
            r3 = r2[r5]
            r0.a = r3
            int r3 = r3 - r12
            r0.b = r3
            r1 = r1[r5]
            r2 = r2[r5]
            int r1 = r1 - r2
            r0.c = r1
            r0.d = r14
            r13 = 0
            r0.e = r13
            return r0
        L_0x009a:
            r13 = 0
            int r12 = r12 + 2
            r5 = 1
            r10 = 0
            goto L_0x0033
        L_0x00a0:
            r13 = 0
            r5 = r11
        L_0x00a2:
            if (r5 > r9) goto L_0x011c
            int r10 = r5 + r6
            int r12 = r9 + r6
            if (r10 == r12) goto L_0x00c4
            int r12 = r11 + r6
            if (r10 == r12) goto L_0x00bb
            int r12 = r26 + r10
            int r14 = r12 + -1
            r14 = r2[r14]
            r15 = 1
            int r12 = r12 + r15
            r12 = r2[r12]
            if (r14 >= r12) goto L_0x00bc
            goto L_0x00c5
        L_0x00bb:
            r15 = 1
        L_0x00bc:
            int r12 = r26 + r10
            int r12 = r12 + r15
            r12 = r2[r12]
            int r12 = r12 - r15
            r14 = 1
            goto L_0x00cb
        L_0x00c4:
            r15 = 1
        L_0x00c5:
            int r12 = r26 + r10
            int r12 = r12 - r15
            r12 = r2[r12]
            r14 = 0
        L_0x00cb:
            int r16 = r12 - r10
        L_0x00cd:
            if (r12 <= 0) goto L_0x00ea
            if (r16 <= 0) goto L_0x00ea
            int r17 = r20 + r12
            int r13 = r17 + -1
            int r17 = r22 + r16
            r18 = r3
            int r3 = r17 + -1
            boolean r3 = r0.areItemsTheSame(r13, r3)
            if (r3 == 0) goto L_0x00ec
            int r12 = r12 + -1
            int r16 = r16 + -1
            r3 = r18
            r13 = 0
            r15 = 1
            goto L_0x00cd
        L_0x00ea:
            r18 = r3
        L_0x00ec:
            int r3 = r26 + r10
            r2[r3] = r12
            if (r8 != 0) goto L_0x0115
            if (r10 < r11) goto L_0x0115
            if (r10 > r9) goto L_0x0115
            r12 = r1[r3]
            r13 = r2[r3]
            if (r12 < r13) goto L_0x0115
            androidx.recyclerview.widget.f$f r0 = new androidx.recyclerview.widget.f$f
            r0.<init>()
            r4 = r2[r3]
            r0.a = r4
            int r4 = r4 - r10
            r0.b = r4
            r1 = r1[r3]
            r2 = r2[r3]
            int r1 = r1 - r2
            r0.c = r1
            r0.d = r14
            r3 = 1
            r0.e = r3
            return r0
        L_0x0115:
            r3 = 1
            int r5 = r5 + 2
            r3 = r18
            r13 = 0
            goto L_0x00a2
        L_0x011c:
            r18 = r3
            r3 = 1
            int r9 = r9 + 1
            r3 = r18
            r5 = 1
            r10 = 0
            goto L_0x002f
        L_0x0127:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation."
            r0.<init>(r1)
            throw r0
        L_0x012f:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.f.a(androidx.recyclerview.widget.f$b, int, int, int, int, int[], int[], int):androidx.recyclerview.widget.f$f");
    }
}
