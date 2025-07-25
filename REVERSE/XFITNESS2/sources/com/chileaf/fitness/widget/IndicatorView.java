package com.chileaf.fitness.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import com.chileaf.fitness.R$styleable;

public class IndicatorView extends View {
    private int e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public int f1299f;

    /* renamed from: g  reason: collision with root package name */
    private float f1300g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f1301h;

    /* renamed from: i  reason: collision with root package name */
    private int[] f1302i;

    /* renamed from: j  reason: collision with root package name */
    private Paint f1303j;
    /* access modifiers changed from: private */
    public Handler k;
    /* access modifiers changed from: private */
    public Runnable l;

    class a implements Runnable {
        a() {
        }

        public void run() {
            IndicatorView indicatorView = IndicatorView.this;
            int unused = indicatorView.f1299f = indicatorView.f1299f + 30;
            IndicatorView.this.invalidate();
            IndicatorView.this.k.postDelayed(IndicatorView.this.l, 60);
        }
    }

    public IndicatorView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f1301h) {
            a();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.k != null) {
            b();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        float min = ((float) Math.min((getWidth() - getPaddingLeft()) - getPaddingRight(), (getHeight() - getPaddingTop()) - getPaddingBottom())) * 0.5f;
        if (this.f1300g == 0.0f) {
            this.f1300g = a(15, min / 2.0f) / 2.0f;
        }
        this.f1303j.setStrokeWidth(this.f1300g);
        int i2 = 0;
        while (true) {
            int[] iArr = this.f1302i;
            if (i2 < iArr.length) {
                this.f1303j.setColor(iArr[i2]);
                float f2 = (float) width;
                int i3 = i2 * -30;
                float f3 = min / 2.0f;
                float f4 = (float) height;
                Canvas canvas2 = canvas;
                canvas2.drawLine(f2 + a(this.f1299f + i3, f3), f4 + b(this.f1299f + i3, f3), f2 + a(this.f1299f + i3, min - (this.f1300g / 2.0f)), f4 + b(i3 + this.f1299f, min - (this.f1300g / 2.0f)), this.f1303j);
                i2++;
            } else {
                return;
            }
        }
    }

    public void setColor(int i2) {
        this.e = i2;
    }

    public void setStartAngle(int i2) {
        this.f1299f = i2;
    }

    public void setStrokeWidth(float f2) {
        this.f1300g = f2;
    }

    public IndicatorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private float b(int i2, float f2) {
        double d = (double) f2;
        double d2 = (double) i2;
        Double.isNaN(d2);
        double sin = Math.sin((d2 * 3.141592653589793d) / 180.0d);
        Double.isNaN(d);
        return (float) (d * sin);
    }

    private void c() {
        Paint paint = new Paint(1);
        this.f1303j = paint;
        paint.setDither(true);
        int alpha = Color.alpha(this.e);
        int red = Color.red(this.e);
        int green = Color.green(this.e);
        int blue = Color.blue(this.e);
        int abs = Math.abs(alpha + 0) / 12;
        int i2 = 0;
        while (true) {
            int[] iArr = this.f1302i;
            if (i2 < iArr.length) {
                iArr[i2] = Color.argb(alpha - (abs * i2), red, green, blue);
                i2++;
            } else {
                this.f1303j.setStrokeCap(Paint.Cap.ROUND);
                return;
            }
        }
    }

    public IndicatorView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = Color.argb(255, 255, 255, 255);
        this.f1299f = 0;
        this.f1300g = 0.0f;
        this.f1302i = new int[12];
        this.k = new Handler(Looper.getMainLooper());
        this.l = new a();
        a(context, attributeSet, i2, 0);
    }

    private void a(Context context, AttributeSet attributeSet, int i2, int i3) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.IndicatorView, i2, i3);
        this.e = obtainStyledAttributes.getColor(0, this.e);
        this.f1299f = obtainStyledAttributes.getInt(2, this.f1299f);
        this.f1300g = obtainStyledAttributes.getDimension(3, this.f1300g);
        this.f1301h = obtainStyledAttributes.getBoolean(1, true);
        obtainStyledAttributes.recycle();
        c();
    }

    public void b() {
        this.k.removeCallbacks(this.l);
    }

    private float a(int i2, float f2) {
        double d = (double) f2;
        double d2 = (double) i2;
        Double.isNaN(d2);
        double cos = Math.cos((d2 * 3.141592653589793d) / 180.0d);
        Double.isNaN(d);
        return (float) (d * cos);
    }

    public void a() {
        this.k.post(this.l);
    }
}
