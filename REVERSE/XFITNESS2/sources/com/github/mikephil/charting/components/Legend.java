package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import h.a.a.a.i.b;
import h.a.a.a.i.i;
import java.util.ArrayList;
import java.util.List;

public class Legend extends b {
    private boolean A = false;
    private List<b> B = new ArrayList(16);
    private List<Boolean> C = new ArrayList(16);
    private List<b> D = new ArrayList(16);

    /* renamed from: g  reason: collision with root package name */
    private e[] f1331g = new e[0];

    /* renamed from: h  reason: collision with root package name */
    private e[] f1332h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1333i = false;

    /* renamed from: j  reason: collision with root package name */
    private LegendHorizontalAlignment f1334j = LegendHorizontalAlignment.LEFT;
    private LegendVerticalAlignment k = LegendVerticalAlignment.BOTTOM;
    private LegendOrientation l = LegendOrientation.HORIZONTAL;
    private boolean m = false;
    private LegendDirection n = LegendDirection.LEFT_TO_RIGHT;
    private LegendForm o = LegendForm.SQUARE;
    private float p = 8.0f;
    private float q = 3.0f;
    private DashPathEffect r = null;
    private float s = 6.0f;
    private float t = 0.0f;
    private float u = 5.0f;
    private float v = 3.0f;
    private float w = 0.95f;
    public float x = 0.0f;
    public float y = 0.0f;
    public float z = 0.0f;

    public enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public enum LegendForm {
        NONE,
        EMPTY,
        DEFAULT,
        SQUARE,
        CIRCLE,
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum LegendOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r0 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.github.mikephil.charting.components.Legend$LegendOrientation r1 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.github.mikephil.charting.components.Legend$LegendOrientation r1 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.components.Legend.a.<clinit>():void");
        }
    }

    public Legend() {
        this.e = i.a(10.0f);
        this.b = i.a(5.0f);
        this.c = i.a(3.0f);
    }

    public void a(List<e> list) {
        this.f1331g = (e[]) list.toArray(new e[list.size()]);
    }

    public float b(Paint paint) {
        float a2 = i.a(this.u);
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (e eVar : this.f1331g) {
            float a3 = i.a(Float.isNaN(eVar.c) ? this.p : eVar.c);
            if (a3 > f3) {
                f3 = a3;
            }
            String str = eVar.a;
            if (str != null) {
                float c = (float) i.c(paint, str);
                if (c > f2) {
                    f2 = c;
                }
            }
        }
        return f2 + f3 + a2;
    }

    public List<Boolean> g() {
        return this.C;
    }

    public List<b> h() {
        return this.B;
    }

    public List<b> i() {
        return this.D;
    }

    public LegendDirection j() {
        return this.n;
    }

    public e[] k() {
        return this.f1331g;
    }

    public e[] l() {
        return this.f1332h;
    }

    public LegendForm m() {
        return this.o;
    }

    public DashPathEffect n() {
        return this.r;
    }

    public float o() {
        return this.q;
    }

    public float p() {
        return this.p;
    }

    public float q() {
        return this.u;
    }

    public LegendHorizontalAlignment r() {
        return this.f1334j;
    }

    public float s() {
        return this.w;
    }

    public LegendOrientation t() {
        return this.l;
    }

    public float u() {
        return this.v;
    }

    public LegendVerticalAlignment v() {
        return this.k;
    }

    public float w() {
        return this.s;
    }

    public float x() {
        return this.t;
    }

    public boolean y() {
        return this.m;
    }

    public boolean z() {
        return this.f1333i;
    }

    public float a(Paint paint) {
        float f2 = 0.0f;
        for (e eVar : this.f1331g) {
            String str = eVar.a;
            if (str != null) {
                float a2 = (float) i.a(paint, str);
                if (a2 > f2) {
                    f2 = a2;
                }
            }
        }
        return f2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:88:0x01da  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.graphics.Paint r28, h.a.a.a.i.j r29) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            float r2 = r0.p
            float r2 = h.a.a.a.i.i.a((float) r2)
            float r3 = r0.v
            float r3 = h.a.a.a.i.i.a((float) r3)
            float r4 = r0.u
            float r4 = h.a.a.a.i.i.a((float) r4)
            float r5 = r0.s
            float r5 = h.a.a.a.i.i.a((float) r5)
            float r6 = r0.t
            float r6 = h.a.a.a.i.i.a((float) r6)
            boolean r7 = r0.A
            com.github.mikephil.charting.components.e[] r8 = r0.f1331g
            int r9 = r8.length
            r27.b(r28)
            float r10 = r27.a((android.graphics.Paint) r28)
            r0.z = r10
            int[] r10 = com.github.mikephil.charting.components.Legend.a.a
            com.github.mikephil.charting.components.Legend$LegendOrientation r11 = r0.l
            int r11 = r11.ordinal()
            r10 = r10[r11]
            r12 = 1
            if (r10 == r12) goto L_0x0179
            r14 = 2
            if (r10 == r14) goto L_0x0042
            goto L_0x01f6
        L_0x0042:
            float r10 = h.a.a.a.i.i.a((android.graphics.Paint) r28)
            float r14 = h.a.a.a.i.i.b((android.graphics.Paint) r28)
            float r14 = r14 + r6
            float r6 = r29.j()
            float r15 = r0.w
            float r6 = r6 * r15
            java.util.List<java.lang.Boolean> r15 = r0.C
            r15.clear()
            java.util.List<h.a.a.a.i.b> r15 = r0.B
            r15.clear()
            java.util.List<h.a.a.a.i.b> r15 = r0.D
            r15.clear()
            r12 = 0
            r13 = -1
            r17 = 0
            r19 = 0
            r20 = 0
        L_0x006a:
            if (r12 >= r9) goto L_0x014e
            r15 = r8[r12]
            com.github.mikephil.charting.components.Legend$LegendForm r11 = r15.b
            r22 = r2
            com.github.mikephil.charting.components.Legend$LegendForm r2 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            if (r11 == r2) goto L_0x0078
            r2 = 1
            goto L_0x0079
        L_0x0078:
            r2 = 0
        L_0x0079:
            float r11 = r15.c
            boolean r11 = java.lang.Float.isNaN(r11)
            if (r11 == 0) goto L_0x0084
            r11 = r22
            goto L_0x008a
        L_0x0084:
            float r11 = r15.c
            float r11 = h.a.a.a.i.i.a((float) r11)
        L_0x008a:
            java.lang.String r15 = r15.a
            r23 = r5
            java.util.List<java.lang.Boolean> r5 = r0.C
            r24 = r8
            r21 = 0
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r21)
            r5.add(r8)
            r5 = -1
            if (r13 != r5) goto L_0x00a0
            r5 = 0
            goto L_0x00a2
        L_0x00a0:
            float r5 = r19 + r3
        L_0x00a2:
            if (r15 == 0) goto L_0x00c2
            java.util.List<h.a.a.a.i.b> r8 = r0.B
            r19 = r3
            h.a.a.a.i.b r3 = h.a.a.a.i.i.b((android.graphics.Paint) r1, (java.lang.String) r15)
            r8.add(r3)
            if (r2 == 0) goto L_0x00b4
            float r2 = r4 + r11
            goto L_0x00b5
        L_0x00b4:
            r2 = 0
        L_0x00b5:
            float r5 = r5 + r2
            java.util.List<h.a.a.a.i.b> r2 = r0.B
            java.lang.Object r2 = r2.get(r12)
            h.a.a.a.i.b r2 = (h.a.a.a.i.b) r2
            float r2 = r2.f1721g
            float r5 = r5 + r2
            goto L_0x00db
        L_0x00c2:
            r19 = r3
            java.util.List<h.a.a.a.i.b> r3 = r0.B
            r25 = r11
            r8 = 0
            h.a.a.a.i.b r11 = h.a.a.a.i.b.a(r8, r8)
            r3.add(r11)
            if (r2 == 0) goto L_0x00d5
            r11 = r25
            goto L_0x00d6
        L_0x00d5:
            r11 = 0
        L_0x00d6:
            float r5 = r5 + r11
            r2 = -1
            if (r13 != r2) goto L_0x00db
            r13 = r12
        L_0x00db:
            if (r15 != 0) goto L_0x00e1
            int r2 = r9 + -1
            if (r12 != r2) goto L_0x013d
        L_0x00e1:
            r2 = r20
            r8 = 0
            int r3 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r3 != 0) goto L_0x00ea
            r3 = 0
            goto L_0x00ec
        L_0x00ea:
            r3 = r23
        L_0x00ec:
            if (r7 == 0) goto L_0x011e
            int r11 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r11 == 0) goto L_0x011e
            float r11 = r6 - r2
            float r18 = r3 + r5
            int r11 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r11 < 0) goto L_0x00fb
            goto L_0x011e
        L_0x00fb:
            java.util.List<h.a.a.a.i.b> r3 = r0.D
            h.a.a.a.i.b r11 = h.a.a.a.i.b.a(r2, r10)
            r3.add(r11)
            r11 = r17
            float r2 = java.lang.Math.max(r11, r2)
            java.util.List<java.lang.Boolean> r3 = r0.C
            r11 = -1
            if (r13 <= r11) goto L_0x0111
            r8 = r13
            goto L_0x0112
        L_0x0111:
            r8 = r12
        L_0x0112:
            r16 = 1
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r16)
            r3.set(r8, r11)
            r3 = r5
            r8 = -1
            goto L_0x0127
        L_0x011e:
            r11 = r17
            r8 = -1
            float r3 = r3 + r5
            float r20 = r2 + r3
            r2 = r11
            r3 = r20
        L_0x0127:
            int r11 = r9 + -1
            if (r12 != r11) goto L_0x0139
            java.util.List<h.a.a.a.i.b> r11 = r0.D
            h.a.a.a.i.b r8 = h.a.a.a.i.b.a(r3, r10)
            r11.add(r8)
            float r17 = java.lang.Math.max(r2, r3)
            goto L_0x013b
        L_0x0139:
            r17 = r2
        L_0x013b:
            r20 = r3
        L_0x013d:
            if (r15 == 0) goto L_0x0140
            r13 = -1
        L_0x0140:
            int r12 = r12 + 1
            r3 = r19
            r2 = r22
            r8 = r24
            r19 = r5
            r5 = r23
            goto L_0x006a
        L_0x014e:
            r11 = r17
            r21 = 0
            r0.x = r11
            java.util.List<h.a.a.a.i.b> r1 = r0.D
            int r1 = r1.size()
            float r1 = (float) r1
            float r10 = r10 * r1
            java.util.List<h.a.a.a.i.b> r1 = r0.D
            int r1 = r1.size()
            if (r1 != 0) goto L_0x0167
            r11 = 0
            goto L_0x0171
        L_0x0167:
            java.util.List<h.a.a.a.i.b> r1 = r0.D
            int r1 = r1.size()
            r16 = 1
            int r11 = r1 + -1
        L_0x0171:
            float r1 = (float) r11
            float r14 = r14 * r1
            float r10 = r10 + r14
            r0.y = r10
            goto L_0x01f6
        L_0x0179:
            r22 = r2
            r19 = r3
            r24 = r8
            r16 = 1
            r21 = 0
            float r2 = h.a.a.a.i.i.a((android.graphics.Paint) r28)
            r3 = 0
            r5 = 0
            r7 = 0
            r8 = 0
            r10 = 0
        L_0x018c:
            if (r7 >= r9) goto L_0x01f2
            r11 = r24[r7]
            com.github.mikephil.charting.components.Legend$LegendForm r12 = r11.b
            com.github.mikephil.charting.components.Legend$LegendForm r13 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            if (r12 == r13) goto L_0x0198
            r12 = 1
            goto L_0x0199
        L_0x0198:
            r12 = 0
        L_0x0199:
            float r13 = r11.c
            boolean r13 = java.lang.Float.isNaN(r13)
            if (r13 == 0) goto L_0x01a4
            r13 = r22
            goto L_0x01aa
        L_0x01a4:
            float r13 = r11.c
            float r13 = h.a.a.a.i.i.a((float) r13)
        L_0x01aa:
            java.lang.String r11 = r11.a
            if (r10 != 0) goto L_0x01af
            r5 = 0
        L_0x01af:
            if (r12 == 0) goto L_0x01b6
            if (r10 == 0) goto L_0x01b5
            float r5 = r5 + r19
        L_0x01b5:
            float r5 = r5 + r13
        L_0x01b6:
            if (r11 == 0) goto L_0x01e3
            if (r12 == 0) goto L_0x01be
            if (r10 != 0) goto L_0x01be
            float r5 = r5 + r4
            goto L_0x01cb
        L_0x01be:
            if (r10 == 0) goto L_0x01cb
            float r8 = java.lang.Math.max(r8, r5)
            float r5 = r2 + r6
            float r3 = r3 + r5
            r5 = r8
            r8 = 0
            r10 = 0
            goto L_0x01d0
        L_0x01cb:
            r26 = r8
            r8 = r5
            r5 = r26
        L_0x01d0:
            int r11 = h.a.a.a.i.i.c(r1, r11)
            float r11 = (float) r11
            float r8 = r8 + r11
            int r11 = r9 + -1
            if (r7 >= r11) goto L_0x01dd
            float r11 = r2 + r6
            float r3 = r3 + r11
        L_0x01dd:
            r26 = r8
            r8 = r5
            r5 = r26
            goto L_0x01eb
        L_0x01e3:
            float r5 = r5 + r13
            int r10 = r9 + -1
            if (r7 >= r10) goto L_0x01ea
            float r5 = r5 + r19
        L_0x01ea:
            r10 = 1
        L_0x01eb:
            float r8 = java.lang.Math.max(r8, r5)
            int r7 = r7 + 1
            goto L_0x018c
        L_0x01f2:
            r0.x = r8
            r0.y = r3
        L_0x01f6:
            float r1 = r0.y
            float r2 = r0.c
            float r1 = r1 + r2
            r0.y = r1
            float r1 = r0.x
            float r2 = r0.b
            float r1 = r1 + r2
            r0.x = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.components.Legend.a(android.graphics.Paint, h.a.a.a.i.j):void");
    }
}
