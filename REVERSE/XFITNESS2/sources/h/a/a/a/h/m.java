package h.a.a.a.h;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import h.a.a.a.a.a;
import h.a.a.a.d.d;
import h.a.a.a.i.e;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.lang.ref.WeakReference;

/* compiled from: PieChartRenderer */
public class m extends g {

    /* renamed from: f  reason: collision with root package name */
    protected PieChart f1704f;

    /* renamed from: g  reason: collision with root package name */
    protected Paint f1705g;

    /* renamed from: h  reason: collision with root package name */
    protected Paint f1706h;

    /* renamed from: i  reason: collision with root package name */
    protected Paint f1707i;

    /* renamed from: j  reason: collision with root package name */
    private TextPaint f1708j;
    private Paint k;
    private StaticLayout l;
    private CharSequence m;
    private RectF n = new RectF();
    private RectF[] o = {new RectF(), new RectF(), new RectF()};
    protected WeakReference<Bitmap> p;
    protected Canvas q;
    private Path r = new Path();
    private RectF s = new RectF();
    private Path t = new Path();
    protected Path u = new Path();
    protected RectF v = new RectF();

    public m(PieChart pieChart, a aVar, j jVar) {
        super(aVar, jVar);
        this.f1704f = pieChart;
        Paint paint = new Paint(1);
        this.f1705g = paint;
        paint.setColor(-1);
        this.f1705g.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.f1706h = paint2;
        paint2.setColor(-1);
        this.f1706h.setStyle(Paint.Style.FILL);
        this.f1706h.setAlpha(105);
        TextPaint textPaint = new TextPaint(1);
        this.f1708j = textPaint;
        textPaint.setColor(-16777216);
        this.f1708j.setTextSize(i.a(12.0f));
        this.e.setTextSize(i.a(13.0f));
        this.e.setColor(-1);
        this.e.setTextAlign(Paint.Align.CENTER);
        Paint paint3 = new Paint(1);
        this.k = paint3;
        paint3.setColor(-1);
        this.k.setTextAlign(Paint.Align.CENTER);
        this.k.setTextSize(i.a(13.0f));
        Paint paint4 = new Paint(1);
        this.f1707i = paint4;
        paint4.setStyle(Paint.Style.STROKE);
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        int l2 = (int) this.a.l();
        int k2 = (int) this.a.k();
        WeakReference<Bitmap> weakReference = this.p;
        Bitmap bitmap = weakReference == null ? null : (Bitmap) weakReference.get();
        if (!(bitmap != null && bitmap.getWidth() == l2 && bitmap.getHeight() == k2)) {
            if (l2 > 0 && k2 > 0) {
                bitmap = Bitmap.createBitmap(l2, k2, Bitmap.Config.ARGB_4444);
                this.p = new WeakReference<>(bitmap);
                this.q = new Canvas(bitmap);
            } else {
                return;
            }
        }
        bitmap.eraseColor(0);
        for (h.a.a.a.e.b.i iVar : ((com.github.mikephil.charting.data.m) this.f1704f.getData()).c()) {
            if (iVar.isVisible() && iVar.X() > 0) {
                a(canvas, iVar);
            }
        }
    }

    public TextPaint b() {
        return this.f1708j;
    }

    public Paint c() {
        return this.k;
    }

    public Paint d() {
        return this.f1705g;
    }

    public Paint e() {
        return this.f1706h;
    }

    public void f() {
        Canvas canvas = this.q;
        if (canvas != null) {
            canvas.setBitmap((Bitmap) null);
            this.q = null;
        }
        WeakReference<Bitmap> weakReference = this.p;
        if (weakReference != null) {
            Bitmap bitmap = (Bitmap) weakReference.get();
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.p.clear();
            this.p = null;
        }
    }

    public void b(Canvas canvas) {
        e(canvas);
        canvas.drawBitmap((Bitmap) this.p.get(), 0.0f, 0.0f, (Paint) null);
        d(canvas);
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x03bf  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x03e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(android.graphics.Canvas r54) {
        /*
            r53 = this;
            r6 = r53
            r7 = r54
            com.github.mikephil.charting.charts.PieChart r0 = r6.f1704f
            h.a.a.a.i.e r8 = r0.getCenterCircleBox()
            com.github.mikephil.charting.charts.PieChart r0 = r6.f1704f
            float r9 = r0.getRadius()
            com.github.mikephil.charting.charts.PieChart r0 = r6.f1704f
            float r0 = r0.getRotationAngle()
            com.github.mikephil.charting.charts.PieChart r1 = r6.f1704f
            float[] r10 = r1.getDrawAngles()
            com.github.mikephil.charting.charts.PieChart r1 = r6.f1704f
            float[] r11 = r1.getAbsoluteAngles()
            h.a.a.a.a.a r1 = r6.b
            float r12 = r1.a()
            h.a.a.a.a.a r1 = r6.b
            float r13 = r1.b()
            com.github.mikephil.charting.charts.PieChart r1 = r6.f1704f
            float r1 = r1.getHoleRadius()
            float r1 = r1 * r9
            r14 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 / r14
            float r1 = r9 - r1
            r15 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r15
            com.github.mikephil.charting.charts.PieChart r2 = r6.f1704f
            float r2 = r2.getHoleRadius()
            float r16 = r2 / r14
            r2 = 1092616192(0x41200000, float:10.0)
            float r2 = r9 / r2
            r3 = 1080452710(0x40666666, float:3.6)
            float r2 = r2 * r3
            com.github.mikephil.charting.charts.PieChart r3 = r6.f1704f
            boolean r3 = r3.r()
            if (r3 == 0) goto L_0x0086
            float r2 = r9 * r16
            float r2 = r9 - r2
            float r2 = r2 / r15
            com.github.mikephil.charting.charts.PieChart r3 = r6.f1704f
            boolean r3 = r3.t()
            if (r3 != 0) goto L_0x0086
            com.github.mikephil.charting.charts.PieChart r3 = r6.f1704f
            boolean r3 = r3.s()
            if (r3 == 0) goto L_0x0086
            double r3 = (double) r0
            r0 = 1135869952(0x43b40000, float:360.0)
            float r1 = r1 * r0
            double r0 = (double) r1
            r17 = 4618760256179416344(0x401921fb54442d18, double:6.283185307179586)
            double r14 = (double) r9
            java.lang.Double.isNaN(r14)
            double r14 = r14 * r17
            java.lang.Double.isNaN(r0)
            double r0 = r0 / r14
            java.lang.Double.isNaN(r3)
            double r3 = r3 + r0
            float r0 = (float) r3
        L_0x0086:
            r14 = r0
            float r15 = r9 - r2
            com.github.mikephil.charting.charts.PieChart r0 = r6.f1704f
            com.github.mikephil.charting.data.h r0 = r0.getData()
            r17 = r0
            com.github.mikephil.charting.data.m r17 = (com.github.mikephil.charting.data.m) r17
            java.util.List r5 = r17.c()
            float r18 = r17.l()
            com.github.mikephil.charting.charts.PieChart r0 = r6.f1704f
            boolean r21 = r0.q()
            r54.save()
            r0 = 1084227584(0x40a00000, float:5.0)
            float r22 = h.a.a.a.i.i.a((float) r0)
            r23 = 0
            r0 = 0
            r4 = 0
        L_0x00ae:
            int r1 = r5.size()
            if (r4 >= r1) goto L_0x043b
            java.lang.Object r1 = r5.get(r4)
            r3 = r1
            h.a.a.a.e.b.i r3 = (h.a.a.a.e.b.i) r3
            boolean r24 = r3.E()
            if (r24 != 0) goto L_0x00db
            if (r21 != 0) goto L_0x00db
            r26 = r4
            r33 = r5
            r29 = r9
            r34 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r40 = r14
            r10 = 1073741824(0x40000000, float:2.0)
            r19 = 1120403456(0x42c80000, float:100.0)
            r9 = r7
            r13 = r8
            goto L_0x0427
        L_0x00db:
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r2 = r3.f()
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r1 = r3.i0()
            r6.a((h.a.a.a.e.b.e) r3)
            r25 = r0
            android.graphics.Paint r0 = r6.e
            r26 = r4
            java.lang.String r4 = "Q"
            int r0 = h.a.a.a.i.i.a((android.graphics.Paint) r0, (java.lang.String) r4)
            float r0 = (float) r0
            r4 = 1082130432(0x40800000, float:4.0)
            float r4 = h.a.a.a.i.i.a((float) r4)
            float r27 = r0 + r4
            h.a.a.a.c.e r4 = r3.W()
            int r0 = r3.X()
            r28 = r5
            android.graphics.Paint r5 = r6.f1707i
            int r7 = r3.x0()
            r5.setColor(r7)
            android.graphics.Paint r5 = r6.f1707i
            float r7 = r3.a()
            float r7 = h.a.a.a.i.i.a((float) r7)
            r5.setStrokeWidth(r7)
            float r7 = r6.a((h.a.a.a.e.b.i) r3)
            h.a.a.a.i.e r5 = r3.Y()
            h.a.a.a.i.e r5 = h.a.a.a.i.e.a((h.a.a.a.i.e) r5)
            r29 = r8
            float r8 = r5.f1727g
            float r8 = h.a.a.a.i.i.a((float) r8)
            r5.f1727g = r8
            float r8 = r5.f1728h
            float r8 = h.a.a.a.i.i.a((float) r8)
            r5.f1728h = r8
            r8 = 0
        L_0x013a:
            if (r8 >= r0) goto L_0x040b
            com.github.mikephil.charting.data.Entry r30 = r3.c(r8)
            r31 = r5
            r5 = r30
            com.github.mikephil.charting.data.PieEntry r5 = (com.github.mikephil.charting.data.PieEntry) r5
            if (r25 != 0) goto L_0x014b
            r30 = 0
            goto L_0x0151
        L_0x014b:
            int r30 = r25 + -1
            r30 = r11[r30]
            float r30 = r30 * r12
        L_0x0151:
            r32 = r10[r25]
            r33 = 1016003125(0x3c8efa35, float:0.017453292)
            float r34 = r15 * r33
            float r34 = r7 / r34
            r20 = 1073741824(0x40000000, float:2.0)
            float r34 = r34 / r20
            float r32 = r32 - r34
            float r32 = r32 / r20
            float r30 = r30 + r32
            float r30 = r30 * r13
            r32 = r0
            float r0 = r14 + r30
            r30 = r7
            com.github.mikephil.charting.charts.PieChart r7 = r6.f1704f
            boolean r7 = r7.u()
            if (r7 == 0) goto L_0x017f
            float r7 = r5.c()
            float r7 = r7 / r18
            r19 = 1120403456(0x42c80000, float:100.0)
            float r7 = r7 * r19
            goto L_0x0183
        L_0x017f:
            float r7 = r5.c()
        L_0x0183:
            java.lang.String r7 = r4.a((float) r7, (com.github.mikephil.charting.data.PieEntry) r5)
            r34 = r10
            java.lang.String r10 = r5.e()
            r35 = r4
            float r4 = r0 * r33
            r33 = r5
            double r4 = (double) r4
            r36 = r11
            r37 = r12
            double r11 = java.lang.Math.cos(r4)
            float r11 = (float) r11
            r38 = r13
            double r12 = java.lang.Math.sin(r4)
            float r12 = (float) r12
            if (r21 == 0) goto L_0x01ac
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r13 = com.github.mikephil.charting.data.PieDataSet$ValuePosition.OUTSIDE_SLICE
            if (r2 != r13) goto L_0x01ac
            r13 = 1
            goto L_0x01ad
        L_0x01ac:
            r13 = 0
        L_0x01ad:
            r40 = r14
            if (r24 == 0) goto L_0x01b7
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r14 = com.github.mikephil.charting.data.PieDataSet$ValuePosition.OUTSIDE_SLICE
            if (r1 != r14) goto L_0x01b7
            r14 = 1
            goto L_0x01b8
        L_0x01b7:
            r14 = 0
        L_0x01b8:
            r41 = r10
            if (r21 == 0) goto L_0x01c2
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r10 = com.github.mikephil.charting.data.PieDataSet$ValuePosition.INSIDE_SLICE
            if (r2 != r10) goto L_0x01c2
            r10 = 1
            goto L_0x01c3
        L_0x01c2:
            r10 = 0
        L_0x01c3:
            r42 = r2
            if (r24 == 0) goto L_0x01ce
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r2 = com.github.mikephil.charting.data.PieDataSet$ValuePosition.INSIDE_SLICE
            if (r1 != r2) goto L_0x01ce
            r39 = 1
            goto L_0x01d0
        L_0x01ce:
            r39 = 0
        L_0x01d0:
            if (r13 != 0) goto L_0x01f0
            if (r14 == 0) goto L_0x01d5
            goto L_0x01f0
        L_0x01d5:
            r45 = r1
            r44 = r12
            r50 = r29
            r51 = r31
            r48 = r35
            r14 = r41
            r19 = 1120403456(0x42c80000, float:100.0)
            r12 = r3
            r29 = r9
            r9 = r54
            r52 = r33
            r33 = r28
            r28 = r52
            goto L_0x0347
        L_0x01f0:
            float r2 = r3.b()
            float r43 = r3.A0()
            float r44 = r3.C()
            r19 = 1120403456(0x42c80000, float:100.0)
            float r44 = r44 / r19
            r45 = r1
            com.github.mikephil.charting.charts.PieChart r1 = r6.f1704f
            boolean r1 = r1.r()
            if (r1 == 0) goto L_0x0213
            float r1 = r9 * r16
            float r46 = r9 - r1
            float r46 = r46 * r44
            float r46 = r46 + r1
            goto L_0x0215
        L_0x0213:
            float r46 = r9 * r44
        L_0x0215:
            boolean r1 = r3.o0()
            if (r1 == 0) goto L_0x0229
            float r43 = r43 * r15
            double r4 = java.lang.Math.sin(r4)
            double r4 = java.lang.Math.abs(r4)
            float r1 = (float) r4
            float r43 = r43 * r1
            goto L_0x022b
        L_0x0229:
            float r43 = r43 * r15
        L_0x022b:
            float r1 = r46 * r11
            r5 = r29
            float r4 = r5.f1727g
            float r1 = r1 + r4
            float r46 = r46 * r12
            r29 = r9
            float r9 = r5.f1728h
            float r44 = r46 + r9
            r46 = 1065353216(0x3f800000, float:1.0)
            float r2 = r2 + r46
            float r2 = r2 * r15
            float r46 = r2 * r11
            float r46 = r46 + r4
            float r2 = r2 * r12
            float r9 = r9 + r2
            r47 = r5
            double r4 = (double) r0
            r48 = 4645040803167600640(0x4076800000000000, double:360.0)
            java.lang.Double.isNaN(r4)
            double r4 = r4 % r48
            r48 = 4636033603912859648(0x4056800000000000, double:90.0)
            int r0 = (r4 > r48 ? 1 : (r4 == r48 ? 0 : -1))
            if (r0 < 0) goto L_0x027e
            r48 = 4643457506423603200(0x4070e00000000000, double:270.0)
            int r0 = (r4 > r48 ? 1 : (r4 == r48 ? 0 : -1))
            if (r0 > 0) goto L_0x027e
            float r0 = r46 - r43
            android.graphics.Paint r2 = r6.e
            android.graphics.Paint$Align r4 = android.graphics.Paint.Align.RIGHT
            r2.setTextAlign(r4)
            if (r13 == 0) goto L_0x0278
            android.graphics.Paint r2 = r6.k
            android.graphics.Paint$Align r4 = android.graphics.Paint.Align.RIGHT
            r2.setTextAlign(r4)
        L_0x0278:
            float r2 = r0 - r22
            r43 = r0
            r5 = r2
            goto L_0x0293
        L_0x027e:
            float r43 = r46 + r43
            android.graphics.Paint r0 = r6.e
            android.graphics.Paint$Align r2 = android.graphics.Paint.Align.LEFT
            r0.setTextAlign(r2)
            if (r13 == 0) goto L_0x0290
            android.graphics.Paint r0 = r6.k
            android.graphics.Paint$Align r2 = android.graphics.Paint.Align.LEFT
            r0.setTextAlign(r2)
        L_0x0290:
            float r0 = r43 + r22
            r5 = r0
        L_0x0293:
            int r0 = r3.x0()
            r2 = 1122867(0x112233, float:1.573472E-39)
            if (r0 == r2) goto L_0x02d7
            boolean r0 = r3.d()
            if (r0 == 0) goto L_0x02ab
            android.graphics.Paint r0 = r6.f1707i
            int r2 = r3.e(r8)
            r0.setColor(r2)
        L_0x02ab:
            android.graphics.Paint r4 = r6.f1707i
            r0 = r54
            r2 = r44
            r44 = r12
            r12 = r3
            r3 = r46
            r48 = r35
            r35 = r4
            r4 = r9
            r51 = r31
            r50 = r47
            r31 = r5
            r52 = r33
            r33 = r28
            r28 = r52
            r5 = r35
            r0.drawLine(r1, r2, r3, r4, r5)
            android.graphics.Paint r5 = r6.f1707i
            r1 = r46
            r2 = r9
            r3 = r43
            r0.drawLine(r1, r2, r3, r4, r5)
            goto L_0x02e8
        L_0x02d7:
            r44 = r12
            r51 = r31
            r48 = r35
            r50 = r47
            r12 = r3
            r31 = r5
            r52 = r33
            r33 = r28
            r28 = r52
        L_0x02e8:
            if (r13 == 0) goto L_0x0314
            if (r14 == 0) goto L_0x0314
            int r5 = r12.b((int) r8)
            r0 = r53
            r1 = r54
            r2 = r7
            r3 = r31
            r4 = r9
            r0.a(r1, r2, r3, r4, r5)
            int r0 = r17.d()
            if (r8 >= r0) goto L_0x030f
            if (r41 == 0) goto L_0x030f
            float r9 = r9 + r27
            r5 = r54
            r3 = r31
            r4 = r41
            r6.a(r5, r4, r3, r9)
            goto L_0x0345
        L_0x030f:
            r9 = r54
            r14 = r41
            goto L_0x0347
        L_0x0314:
            r5 = r54
            r3 = r31
            r4 = r41
            if (r13 == 0) goto L_0x032d
            int r0 = r17.d()
            if (r8 >= r0) goto L_0x0345
            if (r4 == 0) goto L_0x0345
            r0 = 1073741824(0x40000000, float:2.0)
            float r1 = r27 / r0
            float r9 = r9 + r1
            r6.a(r5, r4, r3, r9)
            goto L_0x0345
        L_0x032d:
            r0 = 1073741824(0x40000000, float:2.0)
            if (r14 == 0) goto L_0x0345
            float r1 = r27 / r0
            float r9 = r9 + r1
            int r13 = r12.b((int) r8)
            r0 = r53
            r1 = r54
            r2 = r7
            r14 = r4
            r4 = r9
            r9 = r5
            r5 = r13
            r0.a(r1, r2, r3, r4, r5)
            goto L_0x0347
        L_0x0345:
            r14 = r4
            r9 = r5
        L_0x0347:
            if (r10 != 0) goto L_0x0352
            if (r39 == 0) goto L_0x034c
            goto L_0x0352
        L_0x034c:
            r13 = r50
        L_0x034e:
            r10 = 1073741824(0x40000000, float:2.0)
            goto L_0x03b3
        L_0x0352:
            float r0 = r15 * r11
            r13 = r50
            float r1 = r13.f1727g
            float r5 = r0 + r1
            float r0 = r15 * r44
            float r1 = r13.f1728h
            float r31 = r0 + r1
            android.graphics.Paint r0 = r6.e
            android.graphics.Paint$Align r1 = android.graphics.Paint.Align.CENTER
            r0.setTextAlign(r1)
            if (r10 == 0) goto L_0x038a
            if (r39 == 0) goto L_0x038a
            int r10 = r12.b((int) r8)
            r0 = r53
            r1 = r54
            r2 = r7
            r3 = r5
            r4 = r31
            r7 = r5
            r5 = r10
            r0.a(r1, r2, r3, r4, r5)
            int r0 = r17.d()
            if (r8 >= r0) goto L_0x034e
            if (r14 == 0) goto L_0x034e
            float r0 = r31 + r27
            r6.a(r9, r14, r7, r0)
            goto L_0x034e
        L_0x038a:
            r3 = r5
            if (r10 == 0) goto L_0x039f
            int r0 = r17.d()
            if (r8 >= r0) goto L_0x034e
            if (r14 == 0) goto L_0x034e
            r10 = 1073741824(0x40000000, float:2.0)
            float r0 = r27 / r10
            float r0 = r31 + r0
            r6.a(r9, r14, r3, r0)
            goto L_0x03b3
        L_0x039f:
            r10 = 1073741824(0x40000000, float:2.0)
            if (r39 == 0) goto L_0x03b3
            float r0 = r27 / r10
            float r4 = r31 + r0
            int r5 = r12.b((int) r8)
            r0 = r53
            r1 = r54
            r2 = r7
            r0.a(r1, r2, r3, r4, r5)
        L_0x03b3:
            android.graphics.drawable.Drawable r0 = r28.b()
            if (r0 == 0) goto L_0x03e7
            boolean r0 = r12.G0()
            if (r0 == 0) goto L_0x03e7
            android.graphics.drawable.Drawable r1 = r28.b()
            r7 = r51
            float r0 = r7.f1728h
            float r2 = r15 + r0
            float r2 = r2 * r11
            float r3 = r13.f1727g
            float r2 = r2 + r3
            float r0 = r0 + r15
            float r0 = r0 * r44
            float r3 = r13.f1728h
            float r0 = r0 + r3
            float r3 = r7.f1727g
            float r0 = r0 + r3
            int r2 = (int) r2
            int r3 = (int) r0
            int r4 = r1.getIntrinsicWidth()
            int r5 = r1.getIntrinsicHeight()
            r0 = r54
            h.a.a.a.i.i.a(r0, r1, r2, r3, r4, r5)
            goto L_0x03e9
        L_0x03e7:
            r7 = r51
        L_0x03e9:
            int r25 = r25 + 1
            int r8 = r8 + 1
            r5 = r7
            r3 = r12
            r9 = r29
            r7 = r30
            r0 = r32
            r28 = r33
            r10 = r34
            r11 = r36
            r12 = r37
            r14 = r40
            r2 = r42
            r1 = r45
            r4 = r48
            r29 = r13
            r13 = r38
            goto L_0x013a
        L_0x040b:
            r7 = r5
            r34 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r40 = r14
            r33 = r28
            r13 = r29
            r10 = 1073741824(0x40000000, float:2.0)
            r19 = 1120403456(0x42c80000, float:100.0)
            r29 = r9
            r9 = r54
            h.a.a.a.i.e.b(r7)
            r0 = r25
        L_0x0427:
            int r4 = r26 + 1
            r7 = r9
            r8 = r13
            r9 = r29
            r5 = r33
            r10 = r34
            r11 = r36
            r12 = r37
            r13 = r38
            r14 = r40
            goto L_0x00ae
        L_0x043b:
            r9 = r7
            r13 = r8
            h.a.a.a.i.e.b(r13)
            r54.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.m.c(android.graphics.Canvas):void");
    }

    /* access modifiers changed from: protected */
    public void d(Canvas canvas) {
        float f2;
        e eVar;
        Canvas canvas2 = canvas;
        CharSequence centerText = this.f1704f.getCenterText();
        if (this.f1704f.p() && centerText != null) {
            e centerCircleBox = this.f1704f.getCenterCircleBox();
            e centerTextOffset = this.f1704f.getCenterTextOffset();
            float f3 = centerCircleBox.f1727g + centerTextOffset.f1727g;
            float f4 = centerCircleBox.f1728h + centerTextOffset.f1728h;
            if (!this.f1704f.r() || this.f1704f.t()) {
                f2 = this.f1704f.getRadius();
            } else {
                f2 = this.f1704f.getRadius() * (this.f1704f.getHoleRadius() / 100.0f);
            }
            RectF[] rectFArr = this.o;
            RectF rectF = rectFArr[0];
            rectF.left = f3 - f2;
            rectF.top = f4 - f2;
            rectF.right = f3 + f2;
            rectF.bottom = f4 + f2;
            RectF rectF2 = rectFArr[1];
            rectF2.set(rectF);
            float centerTextRadiusPercent = this.f1704f.getCenterTextRadiusPercent() / 100.0f;
            if (((double) centerTextRadiusPercent) > 0.0d) {
                rectF2.inset((rectF2.width() - (rectF2.width() * centerTextRadiusPercent)) / 2.0f, (rectF2.height() - (rectF2.height() * centerTextRadiusPercent)) / 2.0f);
            }
            if (!centerText.equals(this.m) || !rectF2.equals(this.n)) {
                this.n.set(rectF2);
                this.m = centerText;
                eVar = centerTextOffset;
                StaticLayout staticLayout = r3;
                StaticLayout staticLayout2 = new StaticLayout(centerText, 0, centerText.length(), this.f1708j, (int) Math.max(Math.ceil((double) this.n.width()), 1.0d), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
                this.l = staticLayout;
            } else {
                eVar = centerTextOffset;
            }
            float height = (float) this.l.getHeight();
            canvas.save();
            if (Build.VERSION.SDK_INT >= 18) {
                Path path = this.u;
                path.reset();
                path.addOval(rectF, Path.Direction.CW);
                canvas2.clipPath(path);
            }
            canvas2.translate(rectF2.left, rectF2.top + ((rectF2.height() - height) / 2.0f));
            this.l.draw(canvas2);
            canvas.restore();
            e.b(centerCircleBox);
            e.b(eVar);
        }
    }

    /* access modifiers changed from: protected */
    public void e(Canvas canvas) {
        if (this.f1704f.r() && this.q != null) {
            float radius = this.f1704f.getRadius();
            float holeRadius = (this.f1704f.getHoleRadius() / 100.0f) * radius;
            e centerCircleBox = this.f1704f.getCenterCircleBox();
            if (Color.alpha(this.f1705g.getColor()) > 0) {
                this.q.drawCircle(centerCircleBox.f1727g, centerCircleBox.f1728h, holeRadius, this.f1705g);
            }
            if (Color.alpha(this.f1706h.getColor()) > 0 && this.f1704f.getTransparentCircleRadius() > this.f1704f.getHoleRadius()) {
                int alpha = this.f1706h.getAlpha();
                float transparentCircleRadius = radius * (this.f1704f.getTransparentCircleRadius() / 100.0f);
                this.f1706h.setAlpha((int) (((float) alpha) * this.b.a() * this.b.b()));
                this.t.reset();
                this.t.addCircle(centerCircleBox.f1727g, centerCircleBox.f1728h, transparentCircleRadius, Path.Direction.CW);
                this.t.addCircle(centerCircleBox.f1727g, centerCircleBox.f1728h, holeRadius, Path.Direction.CCW);
                this.q.drawPath(this.t, this.f1706h);
                this.f1706h.setAlpha(alpha);
            }
            e.b(centerCircleBox);
        }
    }

    /* access modifiers changed from: protected */
    public float a(e eVar, float f2, float f3, float f4, float f5, float f6, float f7) {
        e eVar2 = eVar;
        double d = (double) ((f6 + f7) * 0.017453292f);
        float cos = eVar2.f1727g + (((float) Math.cos(d)) * f2);
        float sin = eVar2.f1728h + (((float) Math.sin(d)) * f2);
        double d2 = (double) ((f6 + (f7 / 2.0f)) * 0.017453292f);
        float cos2 = eVar2.f1727g + (((float) Math.cos(d2)) * f2);
        float sin2 = eVar2.f1728h + (((float) Math.sin(d2)) * f2);
        double d3 = (double) f3;
        Double.isNaN(d3);
        double sqrt = (double) (f2 - ((float) ((Math.sqrt(Math.pow((double) (cos - f4), 2.0d) + Math.pow((double) (sin - f5), 2.0d)) / 2.0d) * Math.tan(((180.0d - d3) / 2.0d) * 0.017453292519943295d))));
        double sqrt2 = Math.sqrt(Math.pow((double) (cos2 - ((cos + f4) / 2.0f)), 2.0d) + Math.pow((double) (sin2 - ((sin + f5) / 2.0f)), 2.0d));
        Double.isNaN(sqrt);
        return (float) (sqrt - sqrt2);
    }

    /* access modifiers changed from: protected */
    public float a(h.a.a.a.e.b.i iVar) {
        if (!iVar.Z()) {
            return iVar.N();
        }
        if (iVar.N() / this.a.r() > (iVar.A() / ((com.github.mikephil.charting.data.m) this.f1704f.getData()).l()) * 2.0f) {
            return 0.0f;
        }
        return iVar.N();
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, h.a.a.a.e.b.i iVar) {
        float f2;
        float f3;
        float[] fArr;
        int i2;
        RectF rectF;
        int i3;
        float f4;
        float f5;
        int i4;
        e eVar;
        float f6;
        RectF rectF2;
        int i5;
        RectF rectF3;
        float f7;
        float f8;
        float f9;
        float f10;
        int i6;
        RectF rectF4;
        RectF rectF5;
        RectF rectF6;
        float f11;
        int i7;
        e eVar2;
        h.a.a.a.e.b.i iVar2 = iVar;
        float rotationAngle = this.f1704f.getRotationAngle();
        float a = this.b.a();
        float b = this.b.b();
        RectF circleBox = this.f1704f.getCircleBox();
        int X = iVar.X();
        float[] drawAngles = this.f1704f.getDrawAngles();
        e centerCircleBox = this.f1704f.getCenterCircleBox();
        float radius = this.f1704f.getRadius();
        boolean z = this.f1704f.r() && !this.f1704f.t();
        float holeRadius = z ? (this.f1704f.getHoleRadius() / 100.0f) * radius : 0.0f;
        float holeRadius2 = (radius - ((this.f1704f.getHoleRadius() * radius) / 100.0f)) / 2.0f;
        RectF rectF7 = new RectF();
        boolean z2 = z && this.f1704f.s();
        int i8 = 0;
        for (int i9 = 0; i9 < X; i9++) {
            if (Math.abs(((PieEntry) iVar2.c(i9)).c()) > i.d) {
                i8++;
            }
        }
        if (i8 <= 1) {
            f2 = 0.0f;
        } else {
            f2 = a(iVar2);
        }
        int i10 = 0;
        float f12 = 0.0f;
        while (i10 < X) {
            float f13 = drawAngles[i10];
            if (Math.abs(iVar2.c(i10).c()) > i.d && (!this.f1704f.a(i10) || z2)) {
                boolean z3 = f2 > 0.0f && f13 <= 180.0f;
                this.c.setColor(iVar2.e(i10));
                float f14 = i8 == 1 ? 0.0f : f2 / (radius * 0.017453292f);
                float f15 = rotationAngle + ((f12 + (f14 / 2.0f)) * b);
                float f16 = (f13 - f14) * b;
                if (f16 < 0.0f) {
                    f16 = 0.0f;
                }
                this.r.reset();
                if (z2) {
                    float f17 = radius - holeRadius2;
                    i3 = i10;
                    i5 = i8;
                    double d = (double) (f15 * 0.017453292f);
                    i2 = X;
                    fArr = drawAngles;
                    float cos = centerCircleBox.f1727g + (((float) Math.cos(d)) * f17);
                    float sin = centerCircleBox.f1728h + (f17 * ((float) Math.sin(d)));
                    rectF7.set(cos - holeRadius2, sin - holeRadius2, cos + holeRadius2, sin + holeRadius2);
                } else {
                    i3 = i10;
                    i5 = i8;
                    i2 = X;
                    fArr = drawAngles;
                }
                double d2 = (double) (f15 * 0.017453292f);
                f4 = rotationAngle;
                f3 = a;
                float cos2 = centerCircleBox.f1727g + (((float) Math.cos(d2)) * radius);
                float sin2 = centerCircleBox.f1728h + (((float) Math.sin(d2)) * radius);
                if (f16 < 360.0f || f16 % 360.0f > i.d) {
                    if (z2) {
                        this.r.arcTo(rectF7, f15 + 180.0f, -180.0f);
                    }
                    this.r.arcTo(circleBox, f15, f16);
                } else {
                    this.r.addCircle(centerCircleBox.f1727g, centerCircleBox.f1728h, radius, Path.Direction.CW);
                }
                RectF rectF8 = this.s;
                float f18 = centerCircleBox.f1727g;
                float f19 = centerCircleBox.f1728h;
                rectF8.set(f18 - holeRadius, f19 - holeRadius, f18 + holeRadius, f19 + holeRadius);
                if (!z) {
                    f7 = f16;
                    f9 = holeRadius;
                    f10 = radius;
                    i6 = i5;
                    rectF4 = circleBox;
                    eVar = centerCircleBox;
                    rectF5 = rectF7;
                    f8 = 360.0f;
                } else if (holeRadius > 0.0f || z3) {
                    if (z3) {
                        f11 = f16;
                        i4 = i5;
                        rectF = circleBox;
                        f6 = holeRadius;
                        rectF6 = rectF7;
                        i7 = 1;
                        f5 = radius;
                        float f20 = f15;
                        eVar2 = centerCircleBox;
                        float a2 = a(centerCircleBox, radius, f13 * b, cos2, sin2, f20, f11);
                        if (a2 < 0.0f) {
                            a2 = -a2;
                        }
                        holeRadius = Math.max(f6, a2);
                    } else {
                        f11 = f16;
                        rectF6 = rectF7;
                        f6 = holeRadius;
                        f5 = radius;
                        eVar2 = centerCircleBox;
                        i4 = i5;
                        rectF = circleBox;
                        i7 = 1;
                    }
                    float f21 = (i4 == i7 || holeRadius == 0.0f) ? 0.0f : f2 / (holeRadius * 0.017453292f);
                    float f22 = f4 + ((f12 + (f21 / 2.0f)) * b);
                    float f23 = (f13 - f21) * b;
                    if (f23 < 0.0f) {
                        f23 = 0.0f;
                    }
                    float f24 = f22 + f23;
                    if (f11 < 360.0f || f11 % 360.0f > i.d) {
                        if (z2) {
                            float f25 = f5 - holeRadius2;
                            double d3 = (double) (f24 * 0.017453292f);
                            float cos3 = eVar2.f1727g + (((float) Math.cos(d3)) * f25);
                            float sin3 = eVar2.f1728h + (f25 * ((float) Math.sin(d3)));
                            rectF3 = rectF6;
                            rectF3.set(cos3 - holeRadius2, sin3 - holeRadius2, cos3 + holeRadius2, sin3 + holeRadius2);
                            this.r.arcTo(rectF3, f24, 180.0f);
                        } else {
                            rectF3 = rectF6;
                            double d4 = (double) (f24 * 0.017453292f);
                            this.r.lineTo(eVar2.f1727g + (((float) Math.cos(d4)) * holeRadius), eVar2.f1728h + (holeRadius * ((float) Math.sin(d4))));
                        }
                        this.r.arcTo(this.s, f24, -f23);
                    } else {
                        this.r.addCircle(eVar2.f1727g, eVar2.f1728h, holeRadius, Path.Direction.CCW);
                        rectF3 = rectF6;
                    }
                    eVar = eVar2;
                    rectF2 = rectF3;
                    this.r.close();
                    this.q.drawPath(this.r, this.c);
                    f12 += f13 * f3;
                } else {
                    f7 = f16;
                    f9 = holeRadius;
                    f10 = radius;
                    i6 = i5;
                    f8 = 360.0f;
                    rectF4 = circleBox;
                    eVar = centerCircleBox;
                    rectF5 = rectF7;
                }
                if (f7 % f8 > i.d) {
                    if (z3) {
                        float f26 = f15 + (f7 / 2.0f);
                        float f27 = f15;
                        rectF2 = rectF3;
                        float a3 = a(eVar, f5, f13 * b, cos2, sin2, f27, f7);
                        double d5 = (double) (f26 * 0.017453292f);
                        this.r.lineTo(eVar.f1727g + (((float) Math.cos(d5)) * a3), eVar.f1728h + (a3 * ((float) Math.sin(d5))));
                    } else {
                        rectF2 = rectF3;
                        this.r.lineTo(eVar.f1727g, eVar.f1728h);
                    }
                    this.r.close();
                    this.q.drawPath(this.r, this.c);
                    f12 += f13 * f3;
                }
                rectF2 = rectF3;
                this.r.close();
                this.q.drawPath(this.r, this.c);
                f12 += f13 * f3;
            } else {
                f12 += f13 * a;
                i3 = i10;
                rectF2 = rectF7;
                f5 = radius;
                f4 = rotationAngle;
                f3 = a;
                rectF = circleBox;
                i2 = X;
                fArr = drawAngles;
                i4 = i8;
                f6 = holeRadius;
                eVar = centerCircleBox;
            }
            i10 = i3 + 1;
            rectF7 = rectF2;
            holeRadius = f6;
            centerCircleBox = eVar;
            i8 = i4;
            radius = f5;
            rotationAngle = f4;
            circleBox = rectF;
            X = i2;
            drawAngles = fArr;
            a = f3;
            iVar2 = iVar;
        }
        e.b(centerCircleBox);
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, String str, float f2, float f3) {
        canvas.drawText(str, f2, f3, this.k);
    }

    public void a(Canvas canvas, d[] dVarArr) {
        boolean z;
        float[] fArr;
        float f2;
        e eVar;
        float f3;
        int i2;
        RectF rectF;
        float f4;
        h.a.a.a.e.b.i a;
        float f5;
        int i3;
        float f6;
        int i4;
        float f7;
        float[] fArr2;
        float f8;
        float f9;
        d[] dVarArr2 = dVarArr;
        boolean z2 = this.f1704f.r() && !this.f1704f.t();
        if (!z2 || !this.f1704f.s()) {
            float a2 = this.b.a();
            float b = this.b.b();
            float rotationAngle = this.f1704f.getRotationAngle();
            float[] drawAngles = this.f1704f.getDrawAngles();
            float[] absoluteAngles = this.f1704f.getAbsoluteAngles();
            e centerCircleBox = this.f1704f.getCenterCircleBox();
            float radius = this.f1704f.getRadius();
            float holeRadius = z2 ? (this.f1704f.getHoleRadius() / 100.0f) * radius : 0.0f;
            RectF rectF2 = this.v;
            rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
            int i5 = 0;
            while (i5 < dVarArr2.length) {
                int g2 = (int) dVarArr2[i5].g();
                if (g2 < drawAngles.length && (a = ((com.github.mikephil.charting.data.m) this.f1704f.getData()).a(dVarArr2[i5].c())) != null && a.e0()) {
                    int X = a.X();
                    int i6 = 0;
                    for (int i7 = 0; i7 < X; i7++) {
                        if (Math.abs(((PieEntry) a.c(i7)).c()) > i.d) {
                            i6++;
                        }
                    }
                    if (g2 == 0) {
                        i3 = 1;
                        f5 = 0.0f;
                    } else {
                        f5 = absoluteAngles[g2 - 1] * a2;
                        i3 = 1;
                    }
                    if (i6 <= i3) {
                        f6 = 0.0f;
                    } else {
                        f6 = a.N();
                    }
                    float f10 = drawAngles[g2];
                    float s2 = a.s();
                    int i8 = i5;
                    float f11 = radius + s2;
                    float f12 = holeRadius;
                    rectF2.set(this.f1704f.getCircleBox());
                    float f13 = -s2;
                    rectF2.inset(f13, f13);
                    boolean z3 = f6 > 0.0f && f10 <= 180.0f;
                    this.c.setColor(a.e(g2));
                    float f14 = i6 == 1 ? 0.0f : f6 / (radius * 0.017453292f);
                    float f15 = i6 == 1 ? 0.0f : f6 / (f11 * 0.017453292f);
                    float f16 = rotationAngle + (((f14 / 2.0f) + f5) * b);
                    float f17 = (f10 - f14) * b;
                    float f18 = f17 < 0.0f ? 0.0f : f17;
                    float f19 = (((f15 / 2.0f) + f5) * b) + rotationAngle;
                    float f20 = (f10 - f15) * b;
                    if (f20 < 0.0f) {
                        f20 = 0.0f;
                    }
                    this.r.reset();
                    if (f18 < 360.0f || f18 % 360.0f > i.d) {
                        fArr2 = drawAngles;
                        f7 = f5;
                        double d = (double) (f19 * 0.017453292f);
                        i4 = i6;
                        z = z2;
                        this.r.moveTo(centerCircleBox.f1727g + (((float) Math.cos(d)) * f11), centerCircleBox.f1728h + (f11 * ((float) Math.sin(d))));
                        this.r.arcTo(rectF2, f19, f20);
                    } else {
                        this.r.addCircle(centerCircleBox.f1727g, centerCircleBox.f1728h, f11, Path.Direction.CW);
                        fArr2 = drawAngles;
                        f7 = f5;
                        i4 = i6;
                        z = z2;
                    }
                    if (z3) {
                        double d2 = (double) (f16 * 0.017453292f);
                        i2 = i8;
                        rectF = rectF2;
                        f3 = f12;
                        eVar = centerCircleBox;
                        fArr = fArr2;
                        f8 = a(centerCircleBox, radius, f10 * b, (((float) Math.cos(d2)) * radius) + centerCircleBox.f1727g, centerCircleBox.f1728h + (((float) Math.sin(d2)) * radius), f16, f18);
                    } else {
                        rectF = rectF2;
                        eVar = centerCircleBox;
                        i2 = i8;
                        f3 = f12;
                        fArr = fArr2;
                        f8 = 0.0f;
                    }
                    RectF rectF3 = this.s;
                    float f21 = eVar.f1727g;
                    float f22 = eVar.f1728h;
                    rectF3.set(f21 - f3, f22 - f3, f21 + f3, f22 + f3);
                    if (!z || (f3 <= 0.0f && !z3)) {
                        f4 = a2;
                        f2 = b;
                        if (f18 % 360.0f > i.d) {
                            if (z3) {
                                double d3 = (double) ((f16 + (f18 / 2.0f)) * 0.017453292f);
                                this.r.lineTo(eVar.f1727g + (((float) Math.cos(d3)) * f8), eVar.f1728h + (f8 * ((float) Math.sin(d3))));
                            } else {
                                this.r.lineTo(eVar.f1727g, eVar.f1728h);
                            }
                        }
                    } else {
                        if (z3) {
                            if (f8 < 0.0f) {
                                f8 = -f8;
                            }
                            f9 = Math.max(f3, f8);
                        } else {
                            f9 = f3;
                        }
                        float f23 = (i4 == 1 || f9 == 0.0f) ? 0.0f : f6 / (f9 * 0.017453292f);
                        float f24 = ((f7 + (f23 / 2.0f)) * b) + rotationAngle;
                        float f25 = (f10 - f23) * b;
                        if (f25 < 0.0f) {
                            f25 = 0.0f;
                        }
                        float f26 = f24 + f25;
                        if (f18 < 360.0f || f18 % 360.0f > i.d) {
                            double d4 = (double) (f26 * 0.017453292f);
                            f4 = a2;
                            f2 = b;
                            this.r.lineTo(eVar.f1727g + (((float) Math.cos(d4)) * f9), eVar.f1728h + (f9 * ((float) Math.sin(d4))));
                            this.r.arcTo(this.s, f26, -f25);
                        } else {
                            this.r.addCircle(eVar.f1727g, eVar.f1728h, f9, Path.Direction.CCW);
                            f4 = a2;
                            f2 = b;
                        }
                    }
                    this.r.close();
                    this.q.drawPath(this.r, this.c);
                } else {
                    i2 = i5;
                    rectF = rectF2;
                    f3 = holeRadius;
                    fArr = drawAngles;
                    z = z2;
                    f4 = a2;
                    f2 = b;
                    eVar = centerCircleBox;
                }
                i5 = i2 + 1;
                a2 = f4;
                rectF2 = rectF;
                holeRadius = f3;
                centerCircleBox = eVar;
                b = f2;
                drawAngles = fArr;
                z2 = z;
                dVarArr2 = dVarArr;
            }
            e.b(centerCircleBox);
        }
    }
}
