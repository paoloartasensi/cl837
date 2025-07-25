package com.chileaf.fitness.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;
import com.chileaf.fitness.R$styleable;
import java.lang.reflect.Field;

public class CircleProgressBar extends ProgressBar {
    private int A;
    private int B;
    private Paint.Cap C;
    private final RectF e;

    /* renamed from: f  reason: collision with root package name */
    private final Rect f1294f;

    /* renamed from: g  reason: collision with root package name */
    private final Paint f1295g;

    /* renamed from: h  reason: collision with root package name */
    private final Paint f1296h;

    /* renamed from: i  reason: collision with root package name */
    private final Paint f1297i;

    /* renamed from: j  reason: collision with root package name */
    private final Paint f1298j;
    private float k;
    private float l;
    private float m;
    private int n;
    private float o;
    private float p;
    private int q;
    private float r;
    private float s;
    private float t;
    private int u;
    private int v;
    private int w;
    private int x;
    private boolean y;
    private String z;

    public CircleProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CircleProgressBar);
        this.n = obtainStyledAttributes.getColor(0, 0);
        this.o = obtainStyledAttributes.getFloat(15, 360.0f);
        this.p = obtainStyledAttributes.getFloat(13, -90.0f);
        this.y = obtainStyledAttributes.getBoolean(6, true);
        this.q = obtainStyledAttributes.getInt(1, 45);
        this.z = obtainStyledAttributes.hasValue(11) ? obtainStyledAttributes.getString(11) : "%d%%";
        this.A = obtainStyledAttributes.getInt(14, 0);
        this.B = obtainStyledAttributes.getInt(5, 0);
        this.C = obtainStyledAttributes.hasValue(8) ? Paint.Cap.values()[obtainStyledAttributes.getInt(8, 0)] : Paint.Cap.BUTT;
        this.r = (float) obtainStyledAttributes.getDimensionPixelSize(2, a(4.0f));
        this.t = (float) obtainStyledAttributes.getDimensionPixelSize(12, a(11.0f));
        this.s = (float) obtainStyledAttributes.getDimensionPixelSize(9, a(1.0f));
        this.u = obtainStyledAttributes.getColor(7, Color.parseColor("#FFF2A670"));
        this.v = obtainStyledAttributes.getColor(4, Color.parseColor("#FFF2A670"));
        this.w = obtainStyledAttributes.getColor(10, Color.parseColor("#FFF2A670"));
        this.x = obtainStyledAttributes.getColor(3, Color.parseColor("#FFE3E3E5"));
        obtainStyledAttributes.recycle();
    }

    private void b() {
        this.f1298j.setTextAlign(Paint.Align.CENTER);
        this.f1298j.setTextSize(this.t);
        this.f1295g.setStyle(this.A == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.f1295g.setStrokeWidth(this.s);
        this.f1295g.setColor(this.u);
        this.f1295g.setStrokeCap(this.C);
        this.f1296h.setStyle(this.A == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.f1296h.setStrokeWidth(this.s);
        this.f1296h.setColor(this.x);
        this.f1296h.setStrokeCap(this.C);
        this.f1297i.setStyle(Paint.Style.FILL);
        this.f1297i.setColor(this.n);
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [android.graphics.Shader] */
    /* JADX WARNING: type inference failed for: r13v1, types: [android.graphics.LinearGradient] */
    /* JADX WARNING: type inference failed for: r6v2, types: [android.graphics.RadialGradient] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c() {
        /*
            r21 = this;
            r0 = r21
            int r1 = r0.u
            int r2 = r0.v
            r3 = 0
            if (r1 == r2) goto L_0x00a6
            int r1 = r0.B
            if (r1 == 0) goto L_0x0083
            r2 = 1
            if (r1 == r2) goto L_0x0070
            r4 = 2
            if (r1 == r4) goto L_0x0015
            goto L_0x00a0
        L_0x0015:
            float r1 = r0.s
            double r5 = (double) r1
            r7 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            java.lang.Double.isNaN(r5)
            double r5 = r5 / r7
            r7 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r5 = r5 * r7
            float r1 = r0.k
            double r7 = (double) r1
            java.lang.Double.isNaN(r7)
            double r5 = r5 / r7
            float r1 = (float) r5
            float r3 = r0.p
            double r5 = (double) r3
            android.graphics.Paint$Cap r3 = r0.C
            android.graphics.Paint$Cap r7 = android.graphics.Paint.Cap.BUTT
            if (r3 != r7) goto L_0x003d
            int r3 = r0.A
            if (r3 != r4) goto L_0x003d
            r7 = 0
            goto L_0x0042
        L_0x003d:
            double r7 = (double) r1
            double r7 = java.lang.Math.toDegrees(r7)
        L_0x0042:
            java.lang.Double.isNaN(r5)
            double r5 = r5 - r7
            float r1 = (float) r5
            android.graphics.SweepGradient r3 = new android.graphics.SweepGradient
            float r5 = r0.l
            float r6 = r0.m
            int[] r7 = new int[r4]
            r8 = 0
            int r9 = r0.u
            r7[r8] = r9
            int r8 = r0.v
            r7[r2] = r8
            float[] r2 = new float[r4]
            r2 = {0, 1065353216} // fill-array
            r3.<init>(r5, r6, r7, r2)
            android.graphics.Matrix r2 = new android.graphics.Matrix
            r2.<init>()
            float r4 = r0.l
            float r5 = r0.m
            r2.postRotate(r1, r4, r5)
            r3.setLocalMatrix(r2)
            goto L_0x00a0
        L_0x0070:
            android.graphics.RadialGradient r3 = new android.graphics.RadialGradient
            float r7 = r0.l
            float r8 = r0.m
            float r9 = r0.k
            int r10 = r0.u
            int r11 = r0.v
            android.graphics.Shader$TileMode r12 = android.graphics.Shader.TileMode.CLAMP
            r6 = r3
            r6.<init>(r7, r8, r9, r10, r11, r12)
            goto L_0x00a0
        L_0x0083:
            android.graphics.LinearGradient r3 = new android.graphics.LinearGradient
            android.graphics.RectF r1 = r0.e
            float r2 = r1.left
            float r15 = r1.top
            float r1 = r1.bottom
            int r4 = r0.u
            int r5 = r0.v
            android.graphics.Shader$TileMode r20 = android.graphics.Shader.TileMode.CLAMP
            r13 = r3
            r14 = r2
            r16 = r2
            r17 = r1
            r18 = r4
            r19 = r5
            r13.<init>(r14, r15, r16, r17, r18, r19, r20)
        L_0x00a0:
            android.graphics.Paint r1 = r0.f1295g
            r1.setShader(r3)
            goto L_0x00b2
        L_0x00a6:
            android.graphics.Paint r1 = r0.f1295g
            r1.setShader(r3)
            android.graphics.Paint r1 = r0.f1295g
            int r2 = r0.u
            r1.setColor(r2)
        L_0x00b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.widget.CircleProgressBar.c():void");
    }

    private void d(Canvas canvas) {
        if (this.y) {
            String format = String.format(this.z, new Object[]{Integer.valueOf(getProgress())});
            this.f1298j.setTextSize(this.t);
            this.f1298j.setColor(this.w);
            this.f1298j.getTextBounds(format, 0, format.length(), this.f1294f);
            canvas.drawText(format, this.l, this.m + ((float) (this.f1294f.height() / 2)), this.f1298j);
        }
    }

    private void e(Canvas canvas) {
        canvas.drawArc(this.e, this.p, this.o, false, this.f1296h);
        canvas.drawArc(this.e, this.p, (this.o * ((float) getProgress())) / ((float) getMax()), false, this.f1295g);
    }

    private void f(Canvas canvas) {
        canvas.drawArc(this.e, this.p, this.o, false, this.f1296h);
        canvas.drawArc(this.e, this.p, (this.o * ((float) getProgress())) / ((float) getMax()), true, this.f1295g);
    }

    public int getBackgroundColor() {
        return this.n;
    }

    public Paint.Cap getCap() {
        return this.C;
    }

    public int getLineCount() {
        return this.q;
    }

    public float getLineWidth() {
        return this.r;
    }

    public int getProgressBackgroundColor() {
        return this.x;
    }

    public int getProgressEndColor() {
        return this.v;
    }

    public int getProgressStartColor() {
        return this.u;
    }

    public float getProgressStrokeWidth() {
        return this.s;
    }

    public int getProgressTextColor() {
        return this.w;
    }

    public String getProgressTextFormatPattern() {
        return this.z;
    }

    public float getProgressTextSize() {
        return this.t;
    }

    public int getShader() {
        return this.B;
    }

    public int getStyle() {
        return this.A;
    }

    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        a(canvas);
        c(canvas);
        d(canvas);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        float f2 = (float) (i2 / 2);
        this.l = f2;
        float f3 = (float) (i3 / 2);
        this.m = f3;
        float min = Math.min(f2, f3);
        this.k = min;
        RectF rectF = this.e;
        float f4 = this.m;
        rectF.top = f4 - min;
        rectF.bottom = f4 + min;
        float f5 = this.l;
        rectF.left = f5 - min;
        rectF.right = f5 + min;
        c();
        RectF rectF2 = this.e;
        float f6 = this.s;
        rectF2.inset(f6 / 2.0f, f6 / 2.0f);
    }

    public void setBackgroundColor(int i2) {
        this.n = i2;
        this.f1297i.setColor(i2);
        invalidate();
    }

    public void setCap(Paint.Cap cap) {
        this.C = cap;
        this.f1295g.setStrokeCap(cap);
        this.f1296h.setStrokeCap(cap);
        invalidate();
    }

    public void setLineCount(int i2) {
        this.q = i2;
        invalidate();
    }

    public void setLineWidth(float f2) {
        this.r = f2;
        invalidate();
    }

    public void setProgressBackgroundColor(int i2) {
        this.x = i2;
        this.f1296h.setColor(i2);
        invalidate();
    }

    public void setProgressEndColor(int i2) {
        this.v = i2;
        c();
        invalidate();
    }

    public void setProgressStartColor(int i2) {
        this.u = i2;
        c();
        invalidate();
    }

    public void setProgressStrokeWidth(float f2) {
        this.s = f2;
        this.e.inset(f2 / 2.0f, f2 / 2.0f);
        invalidate();
    }

    public void setProgressTextColor(int i2) {
        this.w = i2;
        invalidate();
    }

    public void setProgressTextFormatPattern(String str) {
        this.z = str;
        invalidate();
    }

    public void setProgressTextSize(float f2) {
        this.t = f2;
        invalidate();
    }

    public void setShader(int i2) {
        this.B = i2;
        c();
        invalidate();
    }

    public void setStyle(int i2) {
        this.A = i2;
        this.f1295g.setStyle(i2 == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.f1296h.setStyle(this.A == 1 ? Paint.Style.FILL : Paint.Style.STROKE);
        invalidate();
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = new RectF();
        this.f1294f = new Rect();
        this.f1295g = new Paint(1);
        this.f1296h = new Paint(1);
        this.f1297i = new Paint(1);
        this.f1298j = new Paint(1);
        setBackgroundColor(0);
        a();
        a(context, attributeSet);
        b();
    }

    private void b(Canvas canvas) {
        double d = (double) (this.o / 360.0f);
        Double.isNaN(d);
        double d2 = (double) this.q;
        Double.isNaN(d2);
        float f2 = (float) ((d * 6.283185307179586d) / d2);
        float f3 = this.k;
        float f4 = f3 - 10.0f;
        float f5 = (f3 - this.r) - 10.0f;
        canvas.rotate(this.p, this.l, this.m);
        int progress = (int) ((((float) getProgress()) / ((float) getMax())) * ((float) this.q));
        for (int i2 = 0; i2 < this.q; i2++) {
            double d3 = (double) (((float) i2) * f2);
            float sin = (((float) Math.sin(d3)) * f5) + this.l;
            float cos = this.l - (((float) Math.cos(d3)) * f5);
            float sin2 = (((float) Math.sin(d3)) * f4) + this.l;
            float cos2 = this.l - (((float) Math.cos(d3)) * f4);
            if (i2 < progress) {
                canvas.drawLine(sin, cos, sin2, cos2, this.f1295g);
            } else {
                canvas.drawLine(sin, cos, sin2, cos2, this.f1296h);
            }
        }
    }

    private void c(Canvas canvas) {
        int i2 = this.A;
        if (i2 == 1) {
            f(canvas);
        } else if (i2 != 2) {
            b(canvas);
        } else {
            e(canvas);
        }
    }

    private void a() {
        try {
            Field declaredField = ProgressBar.class.getDeclaredField("mOnlyIndeterminate");
            declaredField.setAccessible(true);
            declaredField.set(this, false);
            Field declaredField2 = ProgressBar.class.getDeclaredField("mIndeterminate");
            declaredField2.setAccessible(true);
            declaredField2.set(this, false);
            Field declaredField3 = ProgressBar.class.getDeclaredField("mCurrentDrawable");
            declaredField3.setAccessible(true);
            declaredField3.set(this, (Object) null);
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }

    private void a(Canvas canvas) {
        if (this.n != 0) {
            float f2 = this.l;
            canvas.drawCircle(f2, f2, this.k, this.f1297i);
        }
    }

    private int a(float f2) {
        return Math.round(TypedValue.applyDimension(1, f2, getContext().getResources().getDisplayMetrics()));
    }
}
