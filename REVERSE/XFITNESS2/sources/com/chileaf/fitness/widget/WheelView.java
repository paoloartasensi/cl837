package com.chileaf.fitness.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import com.chileaf.fitness.R$styleable;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Arrays;
import java.util.List;

public class WheelView extends View implements Runnable {
    private static final String s0 = WheelView.class.getSimpleName();
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int O;
    private int P;
    private int Q;
    private int R;
    private int S;
    private int T;
    private int U;
    private int V;
    private int W;
    private int a0;
    private int b0;
    private int c0;
    private int d0;
    private final Handler e;
    private boolean e0;

    /* renamed from: f  reason: collision with root package name */
    private Paint f1315f;
    private boolean f0;

    /* renamed from: g  reason: collision with root package name */
    private Paint f1316g;
    private boolean g0;

    /* renamed from: h  reason: collision with root package name */
    private Scroller f1317h;
    private boolean h0;

    /* renamed from: i  reason: collision with root package name */
    private VelocityTracker f1318i;
    private boolean i0;

    /* renamed from: j  reason: collision with root package name */
    private boolean f1319j;
    private boolean j0;
    private a k;
    private boolean k0;
    private b l;
    private boolean l0;
    private Rect m;
    private String m0;
    private Rect n;
    private int n0;
    private Rect o;
    private int o0;
    private Rect p;
    private int p0;
    private Camera q;
    private String q0;
    private Matrix r;
    private boolean r0;
    private Matrix s;
    private List<String> t;
    private String u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    public interface a {
        void a(WheelView wheelView, String str, int i2);
    }

    public interface b {
        void a(int i2);

        void b(int i2);

        void c(int i2);
    }

    public WheelView(Context context) {
        this(context, (AttributeSet) null);
    }

    private int a(int i2, int i3, int i4) {
        if (i2 == 1073741824) {
            return i3;
        }
        return i2 == Integer.MIN_VALUE ? Math.min(i4, i3) : i4;
    }

    private void b() {
        int i2 = this.H;
        if (i2 == 1) {
            int i3 = this.m.left;
            int i4 = this.I;
            this.U = (i4 / 2) + i3;
            this.p0 = i3 + i4 + this.n0 + (this.o0 / 2);
        } else if (i2 != 2) {
            int i5 = this.S;
            this.U = i5;
            this.p0 = i5 + (this.I / 2) + this.n0 + (this.o0 / 2);
        } else {
            int i6 = this.m.right;
            this.U = (i6 - this.y) + (this.I / 2);
            this.p0 = i6 - (this.o0 / 2);
        }
        this.V = (int) (((float) this.T) - ((this.f1315f.ascent() + this.f1315f.descent()) / 2.0f));
    }

    private void c() {
        int i2 = this.M;
        int i3 = this.J;
        int i4 = i2 * i3;
        this.O = this.i0 ? Integer.MIN_VALUE : ((-i3) * (this.t.size() - 1)) + i4;
        if (this.i0) {
            i4 = Integer.MAX_VALUE;
        }
        this.P = i4;
    }

    private void d() {
        if (this.f0) {
            int i2 = this.G / 4;
            int i3 = this.T;
            int i4 = this.K;
            int i5 = i3 + i4 + i2;
            int i6 = (i3 - i4) - i2;
            this.n.set(this.m.left - getPaddingLeft(), i5, this.m.right + getPaddingRight() + this.n0 + this.o0, this.D + i5);
            this.o.set(this.m.left - getPaddingLeft(), i6, this.m.right + getPaddingRight() + this.n0 + this.o0, this.D + i6);
        }
    }

    private void e() {
        this.z = 0;
        this.y = 0;
        if (this.e0) {
            this.y = (int) this.f1315f.measureText(this.t.get(0));
        } else if (e(this.a0)) {
            this.y = (int) this.f1315f.measureText(this.t.get(this.a0));
        } else if (!TextUtils.isEmpty(this.u)) {
            this.y = (int) this.f1315f.measureText(this.u);
        } else {
            for (String measureText : this.t) {
                Paint paint = this.f1315f;
                this.y = Math.max(this.y, (int) paint.measureText(measureText));
            }
        }
        this.I = this.y;
        if (f()) {
            int round = Math.round(this.f1316g.measureText(this.m0));
            this.o0 = round;
            this.y = this.y + round + this.n0;
        }
        Paint.FontMetrics fontMetrics = this.f1315f.getFontMetrics();
        this.z = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    private boolean f() {
        return !TextUtils.isEmpty(this.m0);
    }

    private void g() {
        this.f1315f.setTextAlign(Paint.Align.CENTER);
    }

    private void h() {
        int i2 = this.v;
        if (i2 >= 2) {
            if (i2 % 2 == 0) {
                this.v = i2 + 1;
            }
            int i3 = this.v + 2;
            this.w = i3;
            this.x = i3 / 2;
            return;
        }
        throw new ArithmeticException("Wheel's visible item count can not be less than 2!");
    }

    public int getCurrentItemPosition() {
        return this.N;
    }

    public int getCurtainColor() {
        return this.F;
    }

    public List<String> getData() {
        return this.t;
    }

    public int getIndicatorColor() {
        return this.E;
    }

    public int getIndicatorSize() {
        return this.D;
    }

    public int getItemAlign() {
        return this.H;
    }

    public int getItemSpace() {
        return this.G;
    }

    public int getItemTextColor() {
        return this.A;
    }

    public int getItemTextSize() {
        return this.C;
    }

    public String getMaximumWidthText() {
        return this.u;
    }

    public int getMaximumWidthTextPosition() {
        return this.a0;
    }

    public int getSelectedItemPosition() {
        return this.M;
    }

    public int getSelectedItemTextColor() {
        return this.B;
    }

    public String getSelectedItemValue() {
        return this.t.get(this.N);
    }

    public Typeface getTypeface() {
        Paint paint = this.f1315f;
        if (paint != null) {
            return paint.getTypeface();
        }
        return null;
    }

    public int getVisibleItemCount() {
        return this.v;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        String str;
        int i2;
        Canvas canvas2 = canvas;
        b bVar = this.l;
        if (bVar != null) {
            bVar.b(this.W);
        }
        if (f()) {
            this.f1316g.setColor(this.A);
            this.f1316g.setTextSize((float) this.C);
            this.f1316g.setStyle(Paint.Style.FILL);
            this.f1316g.setTextAlign(Paint.Align.CENTER);
            canvas2.drawText(this.m0, (float) this.p0, (float) this.V, this.f1316g);
        }
        int i3 = (-this.W) / this.J;
        int i4 = this.x;
        int i5 = i3 - i4;
        int i6 = this.M + i5;
        int i7 = -i4;
        while (i6 < this.M + i5 + this.w) {
            if (this.i0) {
                int size = i6 % this.t.size();
                if (size < 0) {
                    size += this.t.size();
                }
                str = this.t.get(size);
            } else {
                str = e(i6) ? this.t.get(i6) : BuildConfig.FLAVOR;
            }
            this.f1315f.setColor(this.A);
            this.f1315f.setStyle(Paint.Style.FILL);
            int i8 = this.V;
            int i9 = this.J;
            int i10 = (i7 * i9) + i8 + (this.W % i9);
            if (this.j0) {
                int abs = i8 - Math.abs(i8 - i10);
                int i11 = this.m.top;
                int i12 = this.V;
                float f2 = (((float) (abs - i11)) * 1.0f) / ((float) (i12 - i11));
                int i13 = i10 > i12 ? 1 : i10 < i12 ? -1 : 0;
                float f3 = 90.0f;
                float f4 = (-(1.0f - f2)) * 90.0f * ((float) i13);
                if (f4 < -90.0f) {
                    f4 = -90.0f;
                }
                if (f4 <= 90.0f) {
                    f3 = f4;
                }
                int i14 = (int) f3;
                i2 = d(i14);
                int i15 = this.U;
                int i16 = this.T - i2;
                this.q.save();
                this.q.rotateX(f3);
                this.q.getMatrix(this.r);
                this.q.restore();
                float f5 = (float) (-i15);
                float f6 = (float) (-i16);
                this.r.preTranslate(f5, f6);
                float f7 = (float) i15;
                float f8 = (float) i16;
                this.r.postTranslate(f7, f8);
                this.q.save();
                this.q.translate(0.0f, 0.0f, (float) b(i14));
                this.q.getMatrix(this.s);
                this.q.restore();
                this.s.preTranslate(f5, f6);
                this.s.postTranslate(f7, f8);
                this.r.postConcat(this.s);
            } else {
                i2 = 0;
            }
            if (this.h0) {
                int i17 = this.V;
                int abs2 = (int) (((((float) (i17 - Math.abs(i17 - i10))) * 1.0f) / ((float) this.V)) * 255.0f);
                this.f1315f.setAlpha(abs2 < 0 ? 0 : abs2);
            }
            if (this.j0) {
                i10 = this.V - i2;
            }
            if (this.B != -1) {
                canvas.save();
                if (this.j0) {
                    canvas2.concat(this.r);
                }
                canvas2.clipRect(this.p, Region.Op.DIFFERENCE);
                float f9 = (float) i10;
                canvas2.drawText(str, (float) this.U, f9, this.f1315f);
                canvas.restore();
                this.f1315f.setColor(this.B);
                canvas.save();
                if (this.j0) {
                    canvas2.concat(this.r);
                }
                canvas2.clipRect(this.p);
                canvas2.drawText(str, (float) this.U, f9, this.f1315f);
                canvas.restore();
            } else {
                canvas.save();
                canvas2.clipRect(this.m);
                if (this.j0) {
                    canvas2.concat(this.r);
                }
                canvas2.drawText(str, (float) this.U, (float) i10, this.f1315f);
                canvas.restore();
            }
            if (this.r0) {
                canvas.save();
                canvas2.clipRect(this.m);
                this.f1315f.setColor(-16711681);
                int i18 = this.T + (this.J * i7);
                Rect rect = this.m;
                float f10 = (float) i18;
                canvas.drawLine((float) rect.left, f10, (float) rect.right, f10, this.f1315f);
                this.f1315f.setColor(-16711936);
                this.f1315f.setStyle(Paint.Style.STROKE);
                int i19 = i18 - this.K;
                Rect rect2 = this.m;
                canvas.drawRect((float) rect2.left, (float) i19, (float) rect2.right, (float) (i19 + this.J), this.f1315f);
                canvas.restore();
            }
            i6++;
            i7++;
        }
        if (this.g0) {
            this.f1315f.setColor(this.F);
            this.f1315f.setStyle(Paint.Style.FILL);
            canvas2.drawRect(this.p, this.f1315f);
        }
        if (this.f0) {
            this.f1315f.setColor(this.E);
            this.f1315f.setStyle(Paint.Style.FILL);
            canvas2.drawRect(this.n, this.f1315f);
            canvas2.drawRect(this.o, this.f1315f);
        }
        if (this.r0) {
            this.f1315f.setColor(-65536);
            this.f1315f.setStyle(Paint.Style.FILL);
            canvas.drawRect(0.0f, 0.0f, (float) getPaddingLeft(), (float) getHeight(), this.f1315f);
            canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getPaddingTop(), this.f1315f);
            canvas.drawRect((float) (getWidth() - getPaddingRight()), 0.0f, (float) getWidth(), (float) getHeight(), this.f1315f);
            canvas.drawRect(0.0f, (float) (getHeight() - getPaddingBottom()), (float) getWidth(), (float) getHeight(), this.f1315f);
        }
        if (this.r0) {
            this.f1315f.setColor(-65536);
            this.f1315f.setStyle(Paint.Style.FILL);
            canvas2.drawCircle((float) this.S, (float) this.T, 5.0f, this.f1315f);
        }
        if (this.r0) {
            this.f1315f.setColor(-16776961);
            this.f1315f.setStyle(Paint.Style.FILL);
            Rect rect3 = this.m;
            canvas.drawLine((float) rect3.left, (float) rect3.top, (float) this.y, (float) rect3.bottom, this.f1315f);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int i4 = this.y;
        int i5 = this.z;
        int i6 = this.v;
        int i7 = (i5 * i6) + (this.G * (i6 - 1));
        if (this.j0) {
            double d = (double) (i7 * 2);
            Double.isNaN(d);
            i7 = (int) (d / 3.141592653589793d);
        }
        if (this.r0) {
            String str = s0;
            Log.i(str, "Wheel's content size is (" + i4 + ":" + i7 + ")");
        }
        int paddingLeft = i4 + getPaddingLeft() + getPaddingRight();
        int paddingTop = i7 + getPaddingTop() + getPaddingBottom();
        if (this.r0) {
            String str2 = s0;
            Log.i(str2, "Wheel's size is (" + paddingLeft + ":" + paddingTop + ")");
        }
        setMeasuredDimension(a(mode, size, paddingLeft), a(mode2, size2, paddingTop));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        this.m.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        if (this.r0) {
            String str = s0;
            Log.i(str, "Wheel's drawn rect size is (" + this.m.width() + ":" + this.m.height() + ") and location is (" + this.m.left + ":" + this.m.top + ")");
        }
        this.S = this.m.centerX() - ((this.o0 + this.n0) / 2);
        this.T = this.m.centerY();
        b();
        this.L = this.m.height() / 2;
        int height = this.m.height() / this.v;
        this.J = height;
        this.K = height / 2;
        c();
        d();
        a();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.f1319j = true;
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            VelocityTracker velocityTracker = this.f1318i;
            if (velocityTracker == null) {
                this.f1318i = VelocityTracker.obtain();
            } else {
                velocityTracker.clear();
            }
            this.f1318i.addMovement(motionEvent);
            if (!this.f1317h.isFinished()) {
                this.f1317h.abortAnimation();
                this.l0 = true;
            }
            int y2 = (int) motionEvent.getY();
            this.b0 = y2;
            this.c0 = y2;
        } else if (action == 1) {
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
            if (!this.k0 || this.l0) {
                this.f1318i.addMovement(motionEvent);
                if (Build.VERSION.SDK_INT >= 4) {
                    this.f1318i.computeCurrentVelocity(1000, (float) this.R);
                } else {
                    this.f1318i.computeCurrentVelocity(1000);
                }
                this.l0 = false;
                int yVelocity = (int) this.f1318i.getYVelocity();
                if (Math.abs(yVelocity) > this.Q) {
                    this.f1317h.fling(0, this.W, 0, yVelocity, 0, 0, this.O, this.P);
                    Scroller scroller = this.f1317h;
                    scroller.setFinalY(scroller.getFinalY() + c(this.f1317h.getFinalY() % this.J));
                } else {
                    Scroller scroller2 = this.f1317h;
                    int i2 = this.W;
                    scroller2.startScroll(0, i2, 0, c(i2 % this.J));
                }
                if (!this.i0) {
                    int finalY = this.f1317h.getFinalY();
                    int i3 = this.P;
                    if (finalY > i3) {
                        this.f1317h.setFinalY(i3);
                    } else {
                        int finalY2 = this.f1317h.getFinalY();
                        int i4 = this.O;
                        if (finalY2 < i4) {
                            this.f1317h.setFinalY(i4);
                        }
                    }
                }
                this.e.post(this);
                VelocityTracker velocityTracker2 = this.f1318i;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.f1318i = null;
                }
            }
        } else if (action != 2) {
            if (action == 3) {
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                VelocityTracker velocityTracker3 = this.f1318i;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.f1318i = null;
                }
            }
        } else if (Math.abs(((float) this.c0) - motionEvent.getY()) < ((float) this.d0)) {
            this.k0 = true;
        } else {
            this.k0 = false;
            this.f1318i.addMovement(motionEvent);
            b bVar = this.l;
            if (bVar != null) {
                bVar.c(1);
            }
            float y3 = motionEvent.getY() - ((float) this.b0);
            if (Math.abs(y3) >= 1.0f) {
                this.W = (int) (((float) this.W) + y3);
                this.b0 = (int) motionEvent.getY();
                invalidate();
            }
        }
        return true;
    }

    public void run() {
        List<String> list = this.t;
        if (list != null && list.size() != 0) {
            if (this.f1317h.isFinished() && !this.l0) {
                int i2 = this.J;
                if (i2 != 0) {
                    int size = (((-this.W) / i2) + this.M) % this.t.size();
                    if (size < 0) {
                        size += this.t.size();
                    }
                    if (this.r0) {
                        String str = s0;
                        Log.i(str, size + ":" + this.t.get(size) + ":" + this.W);
                    }
                    this.N = size;
                    a aVar = this.k;
                    if (aVar != null && this.f1319j) {
                        aVar.a(this, this.t.get(size), size);
                    }
                    b bVar = this.l;
                    if (bVar != null && this.f1319j) {
                        bVar.a(size);
                        this.l.c(0);
                    }
                } else {
                    return;
                }
            }
            if (this.f1317h.computeScrollOffset()) {
                b bVar2 = this.l;
                if (bVar2 != null) {
                    bVar2.c(2);
                }
                this.W = this.f1317h.getCurrY();
                postInvalidate();
                this.e.postDelayed(this, 10);
            }
        }
    }

    public void setAtmospheric(boolean z2) {
        this.h0 = z2;
        invalidate();
    }

    public void setCurtain(boolean z2) {
        this.g0 = z2;
        a();
        invalidate();
    }

    public void setCurtainColor(int i2) {
        this.F = i2;
        invalidate();
    }

    public void setCurved(boolean z2) {
        this.j0 = z2;
        requestLayout();
        invalidate();
    }

    public void setCyclic(boolean z2) {
        this.i0 = z2;
        c();
        invalidate();
    }

    public void setData(List<String> list) {
        if (list != null) {
            this.t = list;
            if (this.M > list.size() - 1 || this.N > list.size() - 1) {
                int size = list.size() - 1;
                this.N = size;
                this.M = size;
            } else {
                this.M = this.N;
            }
            this.W = 0;
            e();
            c();
            requestLayout();
            invalidate();
            return;
        }
        throw new NullPointerException("WheelPicker's data can not be null!");
    }

    public void setIndicator(boolean z2) {
        this.f0 = z2;
        d();
        invalidate();
    }

    public void setIndicatorColor(int i2) {
        this.E = i2;
        invalidate();
    }

    public void setIndicatorSize(int i2) {
        this.D = i2;
        d();
        invalidate();
    }

    public void setItemAlign(int i2) {
        this.H = i2;
        g();
        b();
        invalidate();
    }

    public void setItemSpace(int i2) {
        this.G = i2;
        requestLayout();
        invalidate();
    }

    public void setItemTextColor(int i2) {
        this.A = i2;
        invalidate();
    }

    public void setItemTextSize(int i2) {
        this.C = i2;
        this.f1315f.setTextSize((float) i2);
        e();
        requestLayout();
        invalidate();
    }

    public void setMaximumWidthText(String str) {
        if (str != null) {
            this.u = str;
            e();
            requestLayout();
            invalidate();
            return;
        }
        throw new NullPointerException("Maximum width text can not be null!");
    }

    public void setMaximumWidthTextPosition(int i2) {
        if (e(i2)) {
            this.a0 = i2;
            e();
            requestLayout();
            invalidate();
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Maximum width text Position must in [0, " + this.t.size() + "), but current is " + i2);
    }

    public void setOnItemSelectedListener(a aVar) {
        this.k = aVar;
    }

    public void setOnWheelChangeListener(b bVar) {
        this.l = bVar;
    }

    public void setSameWidth(boolean z2) {
        this.e0 = z2;
        e();
        requestLayout();
        invalidate();
    }

    public void setSelectedItemPosition(int i2) {
        a(i2, true);
    }

    public void setSelectedItemTextColor(int i2) {
        this.B = i2;
        a();
        invalidate();
    }

    public void setSelectedItemValue(String str) {
        if (str != null && this.t.size() > 0) {
            for (int i2 = 0; i2 < this.t.size(); i2++) {
                if (str.equals(this.t.get(i2))) {
                    postDelayed(new a(this, i2), 10);
                }
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        Paint paint = this.f1315f;
        if (paint != null) {
            paint.setTypeface(typeface);
        }
        e();
        requestLayout();
        invalidate();
    }

    public void setVisibleItemCount(int i2) {
        this.v = i2;
        h();
        requestLayout();
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = new Handler();
        this.Q = 50;
        this.R = 8000;
        this.d0 = 5;
        this.r0 = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.WheelView);
        int resourceId = obtainStyledAttributes.getResourceId(11, 0);
        if (resourceId == 0) {
            this.t = Arrays.asList(new String[]{"1", "2", "3", "5", "6", "7", "8", "9", "10"});
        } else {
            this.t = Arrays.asList(getResources().getStringArray(resourceId));
        }
        this.j0 = obtainStyledAttributes.getBoolean(3, false);
        this.i0 = obtainStyledAttributes.getBoolean(4, false);
        this.g0 = obtainStyledAttributes.getBoolean(1, false);
        this.F = obtainStyledAttributes.getColor(2, -1996488705);
        this.e0 = obtainStyledAttributes.getBoolean(21, false);
        this.h0 = obtainStyledAttributes.getBoolean(0, false);
        this.f0 = obtainStyledAttributes.getBoolean(6, false);
        this.E = obtainStyledAttributes.getColor(7, -1166541);
        this.D = obtainStyledAttributes.getDimensionPixelSize(8, a(2.0f));
        this.u = obtainStyledAttributes.getString(19);
        this.a0 = obtainStyledAttributes.getInt(20, -1);
        this.m0 = obtainStyledAttributes.getString(12);
        this.n0 = obtainStyledAttributes.getDimensionPixelSize(13, 0);
        this.G = obtainStyledAttributes.getDimensionPixelSize(16, a(12.0f));
        this.A = obtainStyledAttributes.getColor(17, -7829368);
        this.C = obtainStyledAttributes.getDimensionPixelSize(18, b(24.0f));
        this.v = obtainStyledAttributes.getInt(10, 5);
        this.M = obtainStyledAttributes.getInt(14, 0);
        this.B = obtainStyledAttributes.getColor(15, -1);
        this.H = obtainStyledAttributes.getInt(9, 0);
        this.q0 = obtainStyledAttributes.getString(5);
        obtainStyledAttributes.recycle();
        h();
        this.f1315f = new Paint(69);
        this.f1316g = new Paint(69);
        this.f1315f.setTextSize((float) this.C);
        this.f1316g.setTextSize((float) this.C);
        if (this.q0 != null) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), this.q0));
        }
        g();
        e();
        this.f1317h = new Scroller(getContext());
        if (Build.VERSION.SDK_INT >= 4) {
            ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
            this.Q = viewConfiguration.getScaledMinimumFlingVelocity();
            this.R = viewConfiguration.getScaledMaximumFlingVelocity();
            this.d0 = viewConfiguration.getScaledTouchSlop();
        }
        this.q = new Camera();
        this.m = new Rect();
        this.s = new Matrix();
        this.r = new Matrix();
        this.p = new Rect();
        this.n = new Rect();
        this.o = new Rect();
    }

    private void a() {
        if (this.g0 || this.B != -1) {
            Rect rect = this.p;
            Rect rect2 = this.m;
            int i2 = rect2.left;
            int i3 = this.T;
            int i4 = this.K;
            rect.set(i2, i3 - i4, (rect2.right - this.o0) - this.n0, i3 + i4);
        }
    }

    private int c(int i2) {
        int i3;
        if (Math.abs(i2) <= this.K) {
            return -i2;
        }
        if (this.W < 0) {
            i3 = -this.J;
        } else {
            i3 = this.J;
        }
        return i3 - i2;
    }

    public /* synthetic */ void a(int i2) {
        a(i2, true);
    }

    public void a(int i2, boolean z2) {
        this.f1319j = false;
        if (!z2 || !this.f1317h.isFinished()) {
            if (!this.f1317h.isFinished()) {
                this.f1317h.abortAnimation();
            }
            int max = Math.max(Math.min(i2, this.t.size() - 1), 0);
            this.M = max;
            this.N = max;
            this.W = 0;
            c();
            requestLayout();
            invalidate();
            return;
        }
        int size = getData().size();
        int i3 = i2 - this.N;
        if (i3 != 0) {
            if (this.i0 && Math.abs(i3) > size / 2) {
                if (i3 > 0) {
                    size = -size;
                }
                i3 += size;
            }
            Scroller scroller = this.f1317h;
            scroller.startScroll(0, scroller.getCurrY(), 0, (-i3) * this.J);
            this.e.post(this);
        }
    }

    private int b(int i2) {
        double d = (double) this.L;
        double cos = Math.cos(Math.toRadians((double) i2));
        double d2 = (double) this.L;
        Double.isNaN(d2);
        Double.isNaN(d);
        return (int) (d - (cos * d2));
    }

    private int b(float f2) {
        return Math.round(TypedValue.applyDimension(2, f2, Resources.getSystem().getDisplayMetrics()));
    }

    private int d(int i2) {
        double sin = Math.sin(Math.toRadians((double) i2));
        double d = (double) this.L;
        Double.isNaN(d);
        return (int) (sin * d);
    }

    private boolean e(int i2) {
        return i2 >= 0 && i2 < this.t.size();
    }

    private int a(float f2) {
        return Math.round(TypedValue.applyDimension(1, f2, Resources.getSystem().getDisplayMetrics()));
    }
}
