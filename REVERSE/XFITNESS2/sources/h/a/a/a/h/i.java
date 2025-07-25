package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.e;
import h.a.a.a.i.j;
import java.util.ArrayList;
import java.util.List;

/* compiled from: LegendRenderer */
public class i extends o {
    protected Paint b;
    protected Paint c;
    protected Legend d;
    protected List<e> e = new ArrayList(16);

    /* renamed from: f  reason: collision with root package name */
    protected Paint.FontMetrics f1698f = new Paint.FontMetrics();

    /* renamed from: g  reason: collision with root package name */
    private Path f1699g = new Path();

    /* compiled from: LegendRenderer */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;
        static final /* synthetic */ int[] d;

        /* JADX WARNING: Can't wrap try/catch for region: R(32:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|19|20|(2:21|22)|23|25|26|27|28|29|30|31|33|34|35|36|37|38|40) */
        /* JADX WARNING: Can't wrap try/catch for region: R(34:0|1|2|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|19|20|21|22|23|25|26|27|28|29|30|31|33|34|35|36|37|38|40) */
        /* JADX WARNING: Can't wrap try/catch for region: R(35:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|19|20|21|22|23|25|26|27|28|29|30|31|33|34|35|36|37|38|40) */
        /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0033 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x005a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0075 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x007f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x009a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00a4 */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendForm[] r0 = com.github.mikephil.charting.components.Legend.LegendForm.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                d = r0
                r1 = 1
                com.github.mikephil.charting.components.Legend$LegendForm r2 = com.github.mikephil.charting.components.Legend.LegendForm.NONE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = d     // Catch:{ NoSuchFieldError -> 0x001d }
                com.github.mikephil.charting.components.Legend$LegendForm r3 = com.github.mikephil.charting.components.Legend.LegendForm.EMPTY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                r2 = 3
                int[] r3 = d     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.github.mikephil.charting.components.Legend$LegendForm r4 = com.github.mikephil.charting.components.Legend.LegendForm.DEFAULT     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r3 = d     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.github.mikephil.charting.components.Legend$LegendForm r4 = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r5 = 4
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r3 = d     // Catch:{ NoSuchFieldError -> 0x003e }
                com.github.mikephil.charting.components.Legend$LegendForm r4 = com.github.mikephil.charting.components.Legend.LegendForm.SQUARE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r5 = 5
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r3 = d     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.github.mikephil.charting.components.Legend$LegendForm r4 = com.github.mikephil.charting.components.Legend.LegendForm.LINE     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r5 = 6
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                c = r3
                com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x005a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x005a }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x005a }
            L_0x005a:
                int[] r3 = c     // Catch:{ NoSuchFieldError -> 0x0064 }
                com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment[] r3 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                b = r3
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP     // Catch:{ NoSuchFieldError -> 0x0075 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0075 }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0075 }
            L_0x0075:
                int[] r3 = b     // Catch:{ NoSuchFieldError -> 0x007f }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM     // Catch:{ NoSuchFieldError -> 0x007f }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x007f }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x007f }
            L_0x007f:
                int[] r3 = b     // Catch:{ NoSuchFieldError -> 0x0089 }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x0089 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0089 }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x0089 }
            L_0x0089:
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment[] r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                a = r3
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT     // Catch:{ NoSuchFieldError -> 0x009a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x009a }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x009a }
            L_0x009a:
                int[] r1 = a     // Catch:{ NoSuchFieldError -> 0x00a4 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT     // Catch:{ NoSuchFieldError -> 0x00a4 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a4 }
                r1[r3] = r0     // Catch:{ NoSuchFieldError -> 0x00a4 }
            L_0x00a4:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00ae }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r1 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x00ae }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ae }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00ae }
            L_0x00ae:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.i.a.<clinit>():void");
        }
    }

    public i(j jVar, Legend legend) {
        super(jVar);
        this.d = legend;
        Paint paint = new Paint(1);
        this.b = paint;
        paint.setTextSize(h.a.a.a.i.i.a(9.0f));
        this.b.setTextAlign(Paint.Align.LEFT);
        Paint paint2 = new Paint(1);
        this.c = paint2;
        paint2.setStyle(Paint.Style.FILL);
    }

    public Paint a() {
        return this.b;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: com.github.mikephil.charting.data.h<?>} */
    /* JADX WARNING: type inference failed for: r7v2, types: [h.a.a.a.e.b.e] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.github.mikephil.charting.data.h<?> r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            com.github.mikephil.charting.components.Legend r2 = r0.d
            boolean r2 = r2.z()
            if (r2 != 0) goto L_0x01c1
            java.util.List<com.github.mikephil.charting.components.e> r2 = r0.e
            r2.clear()
            r3 = 0
        L_0x0012:
            int r4 = r19.b()
            if (r3 >= r4) goto L_0x01a7
            h.a.a.a.e.b.e r4 = r1.a((int) r3)
            java.util.List r5 = r4.p0()
            int r6 = r4.X()
            boolean r7 = r4 instanceof h.a.a.a.e.b.a
            if (r7 == 0) goto L_0x0096
            r7 = r4
            h.a.a.a.e.b.a r7 = (h.a.a.a.e.b.a) r7
            boolean r8 = r7.D()
            if (r8 == 0) goto L_0x0096
            java.lang.String[] r6 = r7.H()
            r8 = 0
        L_0x0036:
            int r9 = r5.size()
            if (r8 >= r9) goto L_0x0074
            int r9 = r7.t0()
            if (r8 >= r9) goto L_0x0074
            java.util.List<com.github.mikephil.charting.components.e> r9 = r0.e
            com.github.mikephil.charting.components.e r15 = new com.github.mikephil.charting.components.e
            int r10 = r6.length
            int r10 = r8 % r10
            r11 = r6[r10]
            com.github.mikephil.charting.components.Legend$LegendForm r12 = r4.c()
            float r13 = r4.y0()
            float r14 = r4.l0()
            android.graphics.DashPathEffect r16 = r4.z0()
            java.lang.Object r10 = r5.get(r8)
            java.lang.Integer r10 = (java.lang.Integer) r10
            int r17 = r10.intValue()
            r10 = r15
            r2 = r15
            r15 = r16
            r16 = r17
            r10.<init>(r11, r12, r13, r14, r15, r16)
            r9.add(r2)
            int r8 = r8 + 1
            goto L_0x0036
        L_0x0074:
            java.lang.String r2 = r7.q()
            if (r2 == 0) goto L_0x0093
            java.util.List<com.github.mikephil.charting.components.e> r2 = r0.e
            com.github.mikephil.charting.components.e r12 = new com.github.mikephil.charting.components.e
            java.lang.String r6 = r4.q()
            com.github.mikephil.charting.components.Legend$LegendForm r7 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            r8 = 2143289344(0x7fc00000, float:NaN)
            r9 = 2143289344(0x7fc00000, float:NaN)
            r10 = 0
            r11 = 1122867(0x112233, float:1.573472E-39)
            r5 = r12
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r2.add(r12)
        L_0x0093:
            r2 = r1
            goto L_0x01a2
        L_0x0096:
            boolean r2 = r4 instanceof h.a.a.a.e.b.i
            if (r2 == 0) goto L_0x00fe
            r2 = r4
            h.a.a.a.e.b.i r2 = (h.a.a.a.e.b.i) r2
            r7 = 0
        L_0x009e:
            int r8 = r5.size()
            if (r7 >= r8) goto L_0x00dd
            if (r7 >= r6) goto L_0x00dd
            java.util.List<com.github.mikephil.charting.components.e> r8 = r0.e
            com.github.mikephil.charting.components.e r15 = new com.github.mikephil.charting.components.e
            com.github.mikephil.charting.data.Entry r9 = r2.c(r7)
            com.github.mikephil.charting.data.PieEntry r9 = (com.github.mikephil.charting.data.PieEntry) r9
            java.lang.String r10 = r9.e()
            com.github.mikephil.charting.components.Legend$LegendForm r11 = r4.c()
            float r12 = r4.y0()
            float r13 = r4.l0()
            android.graphics.DashPathEffect r14 = r4.z0()
            java.lang.Object r9 = r5.get(r7)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r16 = r9.intValue()
            r9 = r15
            r1 = r15
            r15 = r16
            r9.<init>(r10, r11, r12, r13, r14, r15)
            r8.add(r1)
            int r7 = r7 + 1
            r1 = r19
            goto L_0x009e
        L_0x00dd:
            java.lang.String r1 = r2.q()
            if (r1 == 0) goto L_0x01a0
            java.util.List<com.github.mikephil.charting.components.e> r1 = r0.e
            com.github.mikephil.charting.components.e r2 = new com.github.mikephil.charting.components.e
            java.lang.String r6 = r4.q()
            com.github.mikephil.charting.components.Legend$LegendForm r7 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            r8 = 2143289344(0x7fc00000, float:NaN)
            r9 = 2143289344(0x7fc00000, float:NaN)
            r10 = 0
            r11 = 1122867(0x112233, float:1.573472E-39)
            r5 = r2
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r1.add(r2)
            goto L_0x01a0
        L_0x00fe:
            boolean r1 = r4 instanceof h.a.a.a.e.b.d
            if (r1 == 0) goto L_0x0153
            r1 = r4
            h.a.a.a.e.b.d r1 = (h.a.a.a.e.b.d) r1
            int r2 = r1.D0()
            r7 = 1122867(0x112233, float:1.573472E-39)
            if (r2 == r7) goto L_0x0153
            int r14 = r1.D0()
            int r1 = r1.J()
            java.util.List<com.github.mikephil.charting.components.e> r2 = r0.e
            com.github.mikephil.charting.components.e r5 = new com.github.mikephil.charting.components.e
            r9 = 0
            com.github.mikephil.charting.components.Legend$LegendForm r10 = r4.c()
            float r11 = r4.y0()
            float r12 = r4.l0()
            android.graphics.DashPathEffect r13 = r4.z0()
            r8 = r5
            r8.<init>(r9, r10, r11, r12, r13, r14)
            r2.add(r5)
            java.util.List<com.github.mikephil.charting.components.e> r2 = r0.e
            com.github.mikephil.charting.components.e r12 = new com.github.mikephil.charting.components.e
            java.lang.String r6 = r4.q()
            com.github.mikephil.charting.components.Legend$LegendForm r7 = r4.c()
            float r8 = r4.y0()
            float r9 = r4.l0()
            android.graphics.DashPathEffect r10 = r4.z0()
            r5 = r12
            r11 = r1
            r5.<init>(r6, r7, r8, r9, r10, r11)
            r2.add(r12)
            goto L_0x01a0
        L_0x0153:
            r1 = 0
        L_0x0154:
            int r2 = r5.size()
            if (r1 >= r2) goto L_0x01a0
            if (r1 >= r6) goto L_0x01a0
            int r2 = r5.size()
            int r2 = r2 + -1
            if (r1 >= r2) goto L_0x016d
            int r2 = r6 + -1
            if (r1 >= r2) goto L_0x016d
            r2 = 0
            r9 = r2
            r2 = r19
            goto L_0x0178
        L_0x016d:
            r2 = r19
            h.a.a.a.e.b.e r7 = r2.a((int) r3)
            java.lang.String r7 = r7.q()
            r9 = r7
        L_0x0178:
            java.util.List<com.github.mikephil.charting.components.e> r7 = r0.e
            com.github.mikephil.charting.components.e r15 = new com.github.mikephil.charting.components.e
            com.github.mikephil.charting.components.Legend$LegendForm r10 = r4.c()
            float r11 = r4.y0()
            float r12 = r4.l0()
            android.graphics.DashPathEffect r13 = r4.z0()
            java.lang.Object r8 = r5.get(r1)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r14 = r8.intValue()
            r8 = r15
            r8.<init>(r9, r10, r11, r12, r13, r14)
            r7.add(r15)
            int r1 = r1 + 1
            goto L_0x0154
        L_0x01a0:
            r2 = r19
        L_0x01a2:
            int r3 = r3 + 1
            r1 = r2
            goto L_0x0012
        L_0x01a7:
            com.github.mikephil.charting.components.Legend r1 = r0.d
            com.github.mikephil.charting.components.e[] r1 = r1.l()
            if (r1 == 0) goto L_0x01ba
            java.util.List<com.github.mikephil.charting.components.e> r1 = r0.e
            com.github.mikephil.charting.components.Legend r2 = r0.d
            com.github.mikephil.charting.components.e[] r2 = r2.l()
            java.util.Collections.addAll(r1, r2)
        L_0x01ba:
            com.github.mikephil.charting.components.Legend r1 = r0.d
            java.util.List<com.github.mikephil.charting.components.e> r2 = r0.e
            r1.a((java.util.List<com.github.mikephil.charting.components.e>) r2)
        L_0x01c1:
            com.github.mikephil.charting.components.Legend r1 = r0.d
            android.graphics.Typeface r1 = r1.c()
            if (r1 == 0) goto L_0x01ce
            android.graphics.Paint r2 = r0.b
            r2.setTypeface(r1)
        L_0x01ce:
            android.graphics.Paint r1 = r0.b
            com.github.mikephil.charting.components.Legend r2 = r0.d
            float r2 = r2.b()
            r1.setTextSize(r2)
            android.graphics.Paint r1 = r0.b
            com.github.mikephil.charting.components.Legend r2 = r0.d
            int r2 = r2.a()
            r1.setColor(r2)
            com.github.mikephil.charting.components.Legend r1 = r0.d
            android.graphics.Paint r2 = r0.b
            h.a.a.a.i.j r3 = r0.a
            r1.a(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.i.a(com.github.mikephil.charting.data.h):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x0273  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x016e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.graphics.Canvas r35) {
        /*
            r34 = this;
            r6 = r34
            r7 = r35
            com.github.mikephil.charting.components.Legend r0 = r6.d
            boolean r0 = r0.f()
            if (r0 != 0) goto L_0x000d
            return
        L_0x000d:
            com.github.mikephil.charting.components.Legend r0 = r6.d
            android.graphics.Typeface r0 = r0.c()
            if (r0 == 0) goto L_0x001a
            android.graphics.Paint r1 = r6.b
            r1.setTypeface(r0)
        L_0x001a:
            android.graphics.Paint r0 = r6.b
            com.github.mikephil.charting.components.Legend r1 = r6.d
            float r1 = r1.b()
            r0.setTextSize(r1)
            android.graphics.Paint r0 = r6.b
            com.github.mikephil.charting.components.Legend r1 = r6.d
            int r1 = r1.a()
            r0.setColor(r1)
            android.graphics.Paint r0 = r6.b
            android.graphics.Paint$FontMetrics r1 = r6.f1698f
            float r8 = h.a.a.a.i.i.a((android.graphics.Paint) r0, (android.graphics.Paint.FontMetrics) r1)
            android.graphics.Paint r0 = r6.b
            android.graphics.Paint$FontMetrics r1 = r6.f1698f
            float r0 = h.a.a.a.i.i.b((android.graphics.Paint) r0, (android.graphics.Paint.FontMetrics) r1)
            com.github.mikephil.charting.components.Legend r1 = r6.d
            float r1 = r1.x()
            float r1 = h.a.a.a.i.i.a((float) r1)
            float r9 = r0 + r1
            android.graphics.Paint r0 = r6.b
            java.lang.String r1 = "ABC"
            int r0 = h.a.a.a.i.i.a((android.graphics.Paint) r0, (java.lang.String) r1)
            float r0 = (float) r0
            r10 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r10
            float r11 = r8 - r0
            com.github.mikephil.charting.components.Legend r0 = r6.d
            com.github.mikephil.charting.components.e[] r12 = r0.k()
            com.github.mikephil.charting.components.Legend r0 = r6.d
            float r0 = r0.q()
            float r13 = h.a.a.a.i.i.a((float) r0)
            com.github.mikephil.charting.components.Legend r0 = r6.d
            float r0 = r0.w()
            float r14 = h.a.a.a.i.i.a((float) r0)
            com.github.mikephil.charting.components.Legend r0 = r6.d
            com.github.mikephil.charting.components.Legend$LegendOrientation r0 = r0.t()
            com.github.mikephil.charting.components.Legend r1 = r6.d
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r15 = r1.r()
            com.github.mikephil.charting.components.Legend r1 = r6.d
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r1 = r1.v()
            com.github.mikephil.charting.components.Legend r2 = r6.d
            com.github.mikephil.charting.components.Legend$LegendDirection r5 = r2.j()
            com.github.mikephil.charting.components.Legend r2 = r6.d
            float r2 = r2.p()
            float r16 = h.a.a.a.i.i.a((float) r2)
            com.github.mikephil.charting.components.Legend r2 = r6.d
            float r2 = r2.u()
            float r4 = h.a.a.a.i.i.a((float) r2)
            com.github.mikephil.charting.components.Legend r2 = r6.d
            float r2 = r2.e()
            com.github.mikephil.charting.components.Legend r3 = r6.d
            float r3 = r3.d()
            int[] r17 = h.a.a.a.h.i.a.a
            int r18 = r15.ordinal()
            r10 = r17[r18]
            r17 = r4
            r4 = 2
            r20 = 0
            r21 = r14
            r14 = 1
            if (r10 == r14) goto L_0x0149
            if (r10 == r4) goto L_0x0129
            r4 = 3
            if (r10 == r4) goto L_0x00c9
            r26 = r8
            r14 = r9
            r7 = 0
            goto L_0x0163
        L_0x00c9:
            com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r4) goto L_0x00d7
            h.a.a.a.i.j r4 = r6.a
            float r4 = r4.l()
            r10 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r10
            goto L_0x00e7
        L_0x00d7:
            r10 = 1073741824(0x40000000, float:2.0)
            h.a.a.a.i.j r4 = r6.a
            float r4 = r4.g()
            h.a.a.a.i.j r14 = r6.a
            float r14 = r14.j()
            float r14 = r14 / r10
            float r4 = r4 + r14
        L_0x00e7:
            com.github.mikephil.charting.components.Legend$LegendDirection r10 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r5 != r10) goto L_0x00ed
            r10 = r3
            goto L_0x00ee
        L_0x00ed:
            float r10 = -r3
        L_0x00ee:
            float r4 = r4 + r10
            com.github.mikephil.charting.components.Legend$LegendOrientation r10 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r10) goto L_0x0125
            r14 = r9
            double r9 = (double) r4
            com.github.mikephil.charting.components.Legend$LegendDirection r4 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            r24 = 4611686018427387904(0x4000000000000000, double:2.0)
            if (r5 != r4) goto L_0x010e
            com.github.mikephil.charting.components.Legend r4 = r6.d
            float r4 = r4.x
            float r4 = -r4
            r26 = r8
            double r7 = (double) r4
            java.lang.Double.isNaN(r7)
            double r7 = r7 / r24
            double r3 = (double) r3
            java.lang.Double.isNaN(r3)
            double r7 = r7 + r3
            goto L_0x011f
        L_0x010e:
            r26 = r8
            com.github.mikephil.charting.components.Legend r4 = r6.d
            float r4 = r4.x
            double r7 = (double) r4
            java.lang.Double.isNaN(r7)
            double r7 = r7 / r24
            double r3 = (double) r3
            java.lang.Double.isNaN(r3)
            double r7 = r7 - r3
        L_0x011f:
            java.lang.Double.isNaN(r9)
            double r9 = r9 + r7
            float r4 = (float) r9
            goto L_0x0147
        L_0x0125:
            r26 = r8
            r14 = r9
            goto L_0x0147
        L_0x0129:
            r26 = r8
            r14 = r9
            com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r4) goto L_0x0137
            h.a.a.a.i.j r4 = r6.a
            float r4 = r4.l()
            goto L_0x013d
        L_0x0137:
            h.a.a.a.i.j r4 = r6.a
            float r4 = r4.h()
        L_0x013d:
            float r4 = r4 - r3
            com.github.mikephil.charting.components.Legend$LegendDirection r3 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r5 != r3) goto L_0x0147
            com.github.mikephil.charting.components.Legend r3 = r6.d
            float r3 = r3.x
            float r4 = r4 - r3
        L_0x0147:
            r7 = r4
            goto L_0x0163
        L_0x0149:
            r26 = r8
            r14 = r9
            com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r4) goto L_0x0151
            goto L_0x0158
        L_0x0151:
            h.a.a.a.i.j r4 = r6.a
            float r4 = r4.g()
            float r3 = r3 + r4
        L_0x0158:
            com.github.mikephil.charting.components.Legend$LegendDirection r4 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r5 != r4) goto L_0x0162
            com.github.mikephil.charting.components.Legend r4 = r6.d
            float r4 = r4.x
            float r4 = r4 + r3
            goto L_0x0147
        L_0x0162:
            r7 = r3
        L_0x0163:
            int[] r3 = h.a.a.a.h.i.a.c
            int r0 = r0.ordinal()
            r0 = r3[r0]
            r3 = 1
            if (r0 == r3) goto L_0x0273
            r4 = 2
            if (r0 == r4) goto L_0x0173
            goto L_0x03d7
        L_0x0173:
            int[] r0 = h.a.a.a.h.i.a.b
            int r1 = r1.ordinal()
            r0 = r0[r1]
            if (r0 == r3) goto L_0x01b1
            if (r0 == r4) goto L_0x0199
            r1 = 3
            if (r0 == r1) goto L_0x0184
            r0 = 0
            goto L_0x01be
        L_0x0184:
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.k()
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            com.github.mikephil.charting.components.Legend r2 = r6.d
            float r3 = r2.y
            float r3 = r3 / r1
            float r0 = r0 - r3
            float r1 = r2.e()
            float r0 = r0 + r1
            goto L_0x01be
        L_0x0199:
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r0 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r15 != r0) goto L_0x01a4
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.k()
            goto L_0x01aa
        L_0x01a4:
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.e()
        L_0x01aa:
            com.github.mikephil.charting.components.Legend r1 = r6.d
            float r1 = r1.y
            float r1 = r1 + r2
            float r0 = r0 - r1
            goto L_0x01be
        L_0x01b1:
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r0 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r15 != r0) goto L_0x01b7
            r0 = 0
            goto L_0x01bd
        L_0x01b7:
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.i()
        L_0x01bd:
            float r0 = r0 + r2
        L_0x01be:
            r9 = r0
            r10 = 0
            r15 = 0
            r18 = 0
        L_0x01c3:
            int r0 = r12.length
            if (r15 >= r0) goto L_0x03d7
            r4 = r12[r15]
            com.github.mikephil.charting.components.Legend$LegendForm r0 = r4.b
            com.github.mikephil.charting.components.Legend$LegendForm r1 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            if (r0 == r1) goto L_0x01d1
            r19 = 1
            goto L_0x01d3
        L_0x01d1:
            r19 = 0
        L_0x01d3:
            float r0 = r4.c
            boolean r0 = java.lang.Float.isNaN(r0)
            if (r0 == 0) goto L_0x01de
            r21 = r16
            goto L_0x01e6
        L_0x01de:
            float r0 = r4.c
            float r0 = h.a.a.a.i.i.a((float) r0)
            r21 = r0
        L_0x01e6:
            if (r19 == 0) goto L_0x0216
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r5 != r0) goto L_0x01ef
            float r0 = r7 + r18
            goto L_0x01f3
        L_0x01ef:
            float r0 = r21 - r18
            float r0 = r7 - r0
        L_0x01f3:
            r22 = r0
            float r3 = r9 + r11
            com.github.mikephil.charting.components.Legend r2 = r6.d
            r0 = r34
            r1 = r35
            r24 = r2
            r2 = r22
            r8 = r17
            r17 = r4
            r27 = r11
            r11 = r5
            r5 = r24
            r0.a(r1, r2, r3, r4, r5)
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r11 != r0) goto L_0x0213
            float r22 = r22 + r21
        L_0x0213:
            r0 = r17
            goto L_0x021e
        L_0x0216:
            r27 = r11
            r8 = r17
            r11 = r5
            r0 = r4
            r22 = r7
        L_0x021e:
            java.lang.String r1 = r0.a
            if (r1 == 0) goto L_0x0263
            if (r19 == 0) goto L_0x0230
            if (r10 != 0) goto L_0x0230
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r11 != r1) goto L_0x022c
            r1 = r13
            goto L_0x022d
        L_0x022c:
            float r1 = -r13
        L_0x022d:
            float r22 = r22 + r1
            goto L_0x0234
        L_0x0230:
            if (r10 == 0) goto L_0x0234
            r22 = r7
        L_0x0234:
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r1) goto L_0x0243
            android.graphics.Paint r1 = r6.b
            java.lang.String r2 = r0.a
            int r1 = h.a.a.a.i.i.c(r1, r2)
            float r1 = (float) r1
            float r22 = r22 - r1
        L_0x0243:
            r1 = r22
            if (r10 != 0) goto L_0x0251
            float r2 = r9 + r26
            java.lang.String r0 = r0.a
            r5 = r35
            r6.a(r5, r1, r2, r0)
            goto L_0x025d
        L_0x0251:
            r5 = r35
            float r2 = r26 + r14
            float r9 = r9 + r2
            float r2 = r9 + r26
            java.lang.String r0 = r0.a
            r6.a(r5, r1, r2, r0)
        L_0x025d:
            float r0 = r26 + r14
            float r9 = r9 + r0
            r18 = 0
            goto L_0x026a
        L_0x0263:
            r5 = r35
            float r21 = r21 + r8
            float r18 = r18 + r21
            r10 = 1
        L_0x026a:
            int r15 = r15 + 1
            r17 = r8
            r5 = r11
            r11 = r27
            goto L_0x01c3
        L_0x0273:
            r27 = r11
            r8 = r17
            r11 = r5
            r5 = r35
            com.github.mikephil.charting.components.Legend r0 = r6.d
            java.util.List r9 = r0.i()
            com.github.mikephil.charting.components.Legend r0 = r6.d
            java.util.List r10 = r0.h()
            com.github.mikephil.charting.components.Legend r0 = r6.d
            java.util.List r4 = r0.g()
            int[] r0 = h.a.a.a.h.i.a.b
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r3 = 1
            if (r0 == r3) goto L_0x02bc
            r1 = 2
            if (r0 == r1) goto L_0x02af
            r1 = 3
            if (r0 == r1) goto L_0x029f
            r2 = 0
            goto L_0x02bc
        L_0x029f:
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.k()
            com.github.mikephil.charting.components.Legend r1 = r6.d
            float r1 = r1.y
            float r0 = r0 - r1
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            float r2 = r2 + r0
            goto L_0x02bc
        L_0x02af:
            h.a.a.a.i.j r0 = r6.a
            float r0 = r0.k()
            float r0 = r0 - r2
            com.github.mikephil.charting.components.Legend r1 = r6.d
            float r1 = r1.y
            float r2 = r0 - r1
        L_0x02bc:
            int r1 = r12.length
            r17 = r7
            r0 = 0
            r3 = 0
        L_0x02c1:
            if (r0 >= r1) goto L_0x03d7
            r18 = r8
            r8 = r12[r0]
            r20 = r1
            com.github.mikephil.charting.components.Legend$LegendForm r1 = r8.b
            com.github.mikephil.charting.components.Legend$LegendForm r5 = com.github.mikephil.charting.components.Legend.LegendForm.NONE
            if (r1 == r5) goto L_0x02d2
            r22 = 1
            goto L_0x02d4
        L_0x02d2:
            r22 = 0
        L_0x02d4:
            float r1 = r8.c
            boolean r1 = java.lang.Float.isNaN(r1)
            if (r1 == 0) goto L_0x02df
            r24 = r16
            goto L_0x02e7
        L_0x02df:
            float r1 = r8.c
            float r1 = h.a.a.a.i.i.a((float) r1)
            r24 = r1
        L_0x02e7:
            int r1 = r4.size()
            if (r0 >= r1) goto L_0x0300
            java.lang.Object r1 = r4.get(r0)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0300
            float r1 = r26 + r14
            float r2 = r2 + r1
            r17 = r2
            r1 = r7
            goto L_0x0304
        L_0x0300:
            r1 = r17
            r17 = r2
        L_0x0304:
            int r2 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r2 != 0) goto L_0x0330
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r2 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r15 != r2) goto L_0x0330
            int r2 = r9.size()
            if (r3 >= r2) goto L_0x0330
            com.github.mikephil.charting.components.Legend$LegendDirection r2 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r2) goto L_0x031f
            java.lang.Object r2 = r9.get(r3)
            h.a.a.a.i.b r2 = (h.a.a.a.i.b) r2
            float r2 = r2.f1721g
            goto L_0x0328
        L_0x031f:
            java.lang.Object r2 = r9.get(r3)
            h.a.a.a.i.b r2 = (h.a.a.a.i.b) r2
            float r2 = r2.f1721g
            float r2 = -r2
        L_0x0328:
            r19 = 1073741824(0x40000000, float:2.0)
            float r2 = r2 / r19
            float r1 = r1 + r2
            int r3 = r3 + 1
            goto L_0x0332
        L_0x0330:
            r19 = 1073741824(0x40000000, float:2.0)
        L_0x0332:
            r28 = r3
            java.lang.String r2 = r8.a
            if (r2 != 0) goto L_0x033b
            r29 = 1
            goto L_0x033d
        L_0x033b:
            r29 = 0
        L_0x033d:
            if (r22 == 0) goto L_0x036b
            com.github.mikephil.charting.components.Legend$LegendDirection r2 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r2) goto L_0x0345
            float r1 = r1 - r24
        L_0x0345:
            r30 = r1
            float r3 = r17 + r27
            com.github.mikephil.charting.components.Legend r5 = r6.d
            r2 = r0
            r0 = r34
            r1 = r35
            r31 = r7
            r7 = r2
            r2 = r30
            r23 = 1
            r32 = r4
            r4 = r8
            r33 = r9
            r9 = r35
            r0.a(r1, r2, r3, r4, r5)
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r11 != r0) goto L_0x0368
            float r1 = r30 + r24
            goto L_0x0376
        L_0x0368:
            r1 = r30
            goto L_0x0376
        L_0x036b:
            r32 = r4
            r31 = r7
            r33 = r9
            r23 = 1
            r9 = r35
            r7 = r0
        L_0x0376:
            if (r29 != 0) goto L_0x03b2
            if (r22 == 0) goto L_0x0382
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r0) goto L_0x0380
            float r0 = -r13
            goto L_0x0381
        L_0x0380:
            r0 = r13
        L_0x0381:
            float r1 = r1 + r0
        L_0x0382:
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r0) goto L_0x038f
            java.lang.Object r0 = r10.get(r7)
            h.a.a.a.i.b r0 = (h.a.a.a.i.b) r0
            float r0 = r0.f1721g
            float r1 = r1 - r0
        L_0x038f:
            float r0 = r17 + r26
            java.lang.String r2 = r8.a
            r6.a(r9, r1, r0, r2)
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r11 != r0) goto L_0x03a3
            java.lang.Object r0 = r10.get(r7)
            h.a.a.a.i.b r0 = (h.a.a.a.i.b) r0
            float r0 = r0.f1721g
            float r1 = r1 + r0
        L_0x03a3:
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r0) goto L_0x03ab
            r0 = r21
            float r2 = -r0
            goto L_0x03ae
        L_0x03ab:
            r0 = r21
            r2 = r0
        L_0x03ae:
            float r1 = r1 + r2
            r2 = r18
            goto L_0x03c0
        L_0x03b2:
            r0 = r21
            com.github.mikephil.charting.components.Legend$LegendDirection r2 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r11 != r2) goto L_0x03bc
            r2 = r18
            float r4 = -r2
            goto L_0x03bf
        L_0x03bc:
            r2 = r18
            r4 = r2
        L_0x03bf:
            float r1 = r1 + r4
        L_0x03c0:
            int r3 = r7 + 1
            r21 = r0
            r8 = r2
            r0 = r3
            r5 = r9
            r2 = r17
            r3 = r28
            r7 = r31
            r4 = r32
            r9 = r33
            r17 = r1
            r1 = r20
            goto L_0x02c1
        L_0x03d7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.i.a(android.graphics.Canvas):void");
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float f3, e eVar, Legend legend) {
        int i2 = eVar.f1349f;
        if (i2 != 1122868 && i2 != 1122867 && i2 != 0) {
            int save = canvas.save();
            Legend.LegendForm legendForm = eVar.b;
            if (legendForm == Legend.LegendForm.DEFAULT) {
                legendForm = legend.m();
            }
            this.c.setColor(eVar.f1349f);
            float a2 = h.a.a.a.i.i.a(Float.isNaN(eVar.c) ? legend.p() : eVar.c);
            float f4 = a2 / 2.0f;
            int i3 = a.d[legendForm.ordinal()];
            if (i3 == 3 || i3 == 4) {
                this.c.setStyle(Paint.Style.FILL);
                canvas.drawCircle(f2 + f4, f3, f4, this.c);
            } else if (i3 == 5) {
                this.c.setStyle(Paint.Style.FILL);
                canvas.drawRect(f2, f3 - f4, f2 + a2, f3 + f4, this.c);
            } else if (i3 == 6) {
                float a3 = h.a.a.a.i.i.a(Float.isNaN(eVar.d) ? legend.o() : eVar.d);
                DashPathEffect dashPathEffect = eVar.e;
                if (dashPathEffect == null) {
                    dashPathEffect = legend.n();
                }
                this.c.setStyle(Paint.Style.STROKE);
                this.c.setStrokeWidth(a3);
                this.c.setPathEffect(dashPathEffect);
                this.f1699g.reset();
                this.f1699g.moveTo(f2, f3);
                this.f1699g.lineTo(f2 + a2, f3);
                canvas.drawPath(this.f1699g, this.c);
            }
            canvas.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float f3, String str) {
        canvas.drawText(str, f2, f3, this.b);
    }
}
