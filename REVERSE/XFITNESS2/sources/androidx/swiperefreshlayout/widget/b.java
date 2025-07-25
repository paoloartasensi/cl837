package androidx.swiperefreshlayout.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.core.g.h;

/* compiled from: CircularProgressDrawable */
public class b extends Drawable implements Animatable {
    private static final Interpolator k = new LinearInterpolator();
    private static final Interpolator l = new g.c.a.a.b();
    private static final int[] m = {-16777216};
    private final c e;

    /* renamed from: f  reason: collision with root package name */
    private float f873f;

    /* renamed from: g  reason: collision with root package name */
    private Resources f874g;

    /* renamed from: h  reason: collision with root package name */
    private Animator f875h;

    /* renamed from: i  reason: collision with root package name */
    float f876i;

    /* renamed from: j  reason: collision with root package name */
    boolean f877j;

    /* compiled from: CircularProgressDrawable */
    class a implements ValueAnimator.AnimatorUpdateListener {
        final /* synthetic */ c a;

        a(c cVar) {
            this.a = cVar;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            b.this.a(floatValue, this.a);
            b.this.a(floatValue, this.a, false);
            b.this.invalidateSelf();
        }
    }

    /* renamed from: androidx.swiperefreshlayout.widget.b$b  reason: collision with other inner class name */
    /* compiled from: CircularProgressDrawable */
    class C0047b implements Animator.AnimatorListener {
        final /* synthetic */ c a;

        C0047b(c cVar) {
            this.a = cVar;
        }

        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
            b.this.a(1.0f, this.a, true);
            this.a.l();
            this.a.j();
            b bVar = b.this;
            if (bVar.f877j) {
                bVar.f877j = false;
                animator.cancel();
                animator.setDuration(1332);
                animator.start();
                this.a.a(false);
                return;
            }
            bVar.f876i += 1.0f;
        }

        public void onAnimationStart(Animator animator) {
            b.this.f876i = 0.0f;
        }
    }

    /* compiled from: CircularProgressDrawable */
    private static class c {
        final RectF a = new RectF();
        final Paint b = new Paint();
        final Paint c = new Paint();
        final Paint d = new Paint();
        float e = 0.0f;

        /* renamed from: f  reason: collision with root package name */
        float f878f = 0.0f;

        /* renamed from: g  reason: collision with root package name */
        float f879g = 0.0f;

        /* renamed from: h  reason: collision with root package name */
        float f880h = 5.0f;

        /* renamed from: i  reason: collision with root package name */
        int[] f881i;

        /* renamed from: j  reason: collision with root package name */
        int f882j;
        float k;
        float l;
        float m;
        boolean n;
        Path o;
        float p = 1.0f;
        float q;
        int r;
        int s;
        int t = 255;
        int u;

        c() {
            this.b.setStrokeCap(Paint.Cap.SQUARE);
            this.b.setAntiAlias(true);
            this.b.setStyle(Paint.Style.STROKE);
            this.c.setStyle(Paint.Style.FILL);
            this.c.setAntiAlias(true);
            this.d.setColor(0);
        }

        /* access modifiers changed from: package-private */
        public void a(float f2, float f3) {
            this.r = (int) f2;
            this.s = (int) f3;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            this.u = i2;
        }

        /* access modifiers changed from: package-private */
        public void c(int i2) {
            this.f882j = i2;
            this.u = this.f881i[i2];
        }

        /* access modifiers changed from: package-private */
        public int d() {
            return (this.f882j + 1) % this.f881i.length;
        }

        /* access modifiers changed from: package-private */
        public void e(float f2) {
            this.e = f2;
        }

        /* access modifiers changed from: package-private */
        public void f(float f2) {
            this.f880h = f2;
            this.b.setStrokeWidth(f2);
        }

        /* access modifiers changed from: package-private */
        public float g() {
            return this.l;
        }

        /* access modifiers changed from: package-private */
        public float h() {
            return this.m;
        }

        /* access modifiers changed from: package-private */
        public float i() {
            return this.k;
        }

        /* access modifiers changed from: package-private */
        public void j() {
            c(d());
        }

        /* access modifiers changed from: package-private */
        public void k() {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            e(0.0f);
            c(0.0f);
            d(0.0f);
        }

        /* access modifiers changed from: package-private */
        public void l() {
            this.k = this.e;
            this.l = this.f878f;
            this.m = this.f879g;
        }

        /* access modifiers changed from: package-private */
        public float b() {
            return this.f878f;
        }

        /* access modifiers changed from: package-private */
        public void d(float f2) {
            this.f879g = f2;
        }

        /* access modifiers changed from: package-private */
        public float e() {
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public void a(Canvas canvas, Rect rect) {
            RectF rectF = this.a;
            float f2 = this.q;
            float f3 = (this.f880h / 2.0f) + f2;
            if (f2 <= 0.0f) {
                f3 = (((float) Math.min(rect.width(), rect.height())) / 2.0f) - Math.max((((float) this.r) * this.p) / 2.0f, this.f880h / 2.0f);
            }
            rectF.set(((float) rect.centerX()) - f3, ((float) rect.centerY()) - f3, ((float) rect.centerX()) + f3, ((float) rect.centerY()) + f3);
            float f4 = this.e;
            float f5 = this.f879g;
            float f6 = (f4 + f5) * 360.0f;
            float f7 = ((this.f878f + f5) * 360.0f) - f6;
            this.b.setColor(this.u);
            this.b.setAlpha(this.t);
            float f8 = this.f880h / 2.0f;
            rectF.inset(f8, f8);
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2.0f, this.d);
            float f9 = -f8;
            rectF.inset(f9, f9);
            canvas.drawArc(rectF, f6, f7, false, this.b);
            a(canvas, f6, f7, rectF);
        }

        /* access modifiers changed from: package-private */
        public void b(float f2) {
            this.q = f2;
        }

        /* access modifiers changed from: package-private */
        public int c() {
            return this.f881i[d()];
        }

        /* access modifiers changed from: package-private */
        public int f() {
            return this.f881i[this.f882j];
        }

        /* access modifiers changed from: package-private */
        public void c(float f2) {
            this.f878f = f2;
        }

        /* access modifiers changed from: package-private */
        public void a(Canvas canvas, float f2, float f3, RectF rectF) {
            if (this.n) {
                Path path = this.o;
                if (path == null) {
                    Path path2 = new Path();
                    this.o = path2;
                    path2.setFillType(Path.FillType.EVEN_ODD);
                } else {
                    path.reset();
                }
                this.o.moveTo(0.0f, 0.0f);
                this.o.lineTo(((float) this.r) * this.p, 0.0f);
                Path path3 = this.o;
                float f4 = this.p;
                path3.lineTo((((float) this.r) * f4) / 2.0f, ((float) this.s) * f4);
                this.o.offset(((Math.min(rectF.width(), rectF.height()) / 2.0f) + rectF.centerX()) - ((((float) this.r) * this.p) / 2.0f), rectF.centerY() + (this.f880h / 2.0f));
                this.o.close();
                this.c.setColor(this.u);
                this.c.setAlpha(this.t);
                canvas.save();
                canvas.rotate(f2 + f3, rectF.centerX(), rectF.centerY());
                canvas.drawPath(this.o, this.c);
                canvas.restore();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int[] iArr) {
            this.f881i = iArr;
            c(0);
        }

        /* access modifiers changed from: package-private */
        public void a(ColorFilter colorFilter) {
            this.b.setColorFilter(colorFilter);
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            this.t = i2;
        }

        /* access modifiers changed from: package-private */
        public int a() {
            return this.t;
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            if (this.n != z) {
                this.n = z;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(float f2) {
            if (f2 != this.p) {
                this.p = f2;
            }
        }
    }

    public b(Context context) {
        h.a(context);
        this.f874g = context.getResources();
        c cVar = new c();
        this.e = cVar;
        cVar.a(m);
        c(2.5f);
        a();
    }

    private int a(float f2, int i2, int i3) {
        int i4 = (i2 >> 24) & 255;
        int i5 = (i2 >> 16) & 255;
        int i6 = (i2 >> 8) & 255;
        int i7 = i2 & 255;
        return ((i4 + ((int) (((float) (((i3 >> 24) & 255) - i4)) * f2))) << 24) | ((i5 + ((int) (((float) (((i3 >> 16) & 255) - i5)) * f2))) << 16) | ((i6 + ((int) (((float) (((i3 >> 8) & 255) - i6)) * f2))) << 8) | (i7 + ((int) (f2 * ((float) ((i3 & 255) - i7)))));
    }

    private void a(float f2, float f3, float f4, float f5) {
        c cVar = this.e;
        float f6 = this.f874g.getDisplayMetrics().density;
        cVar.f(f3 * f6);
        cVar.b(f2 * f6);
        cVar.c(0);
        cVar.a(f4 * f6, f5 * f6);
    }

    private void d(float f2) {
        this.f873f = f2;
    }

    public void b(float f2) {
        this.e.d(f2);
        invalidateSelf();
    }

    public void c(float f2) {
        this.e.f(f2);
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.rotate(this.f873f, bounds.exactCenterX(), bounds.exactCenterY());
        this.e.a(canvas, bounds);
        canvas.restore();
    }

    public int getAlpha() {
        return this.e.a();
    }

    public int getOpacity() {
        return -3;
    }

    public boolean isRunning() {
        return this.f875h.isRunning();
    }

    public void setAlpha(int i2) {
        this.e.a(i2);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.e.a(colorFilter);
        invalidateSelf();
    }

    public void start() {
        this.f875h.cancel();
        this.e.l();
        if (this.e.b() != this.e.e()) {
            this.f877j = true;
            this.f875h.setDuration(666);
            this.f875h.start();
            return;
        }
        this.e.c(0);
        this.e.k();
        this.f875h.setDuration(1332);
        this.f875h.start();
    }

    public void stop() {
        this.f875h.cancel();
        d(0.0f);
        this.e.a(false);
        this.e.c(0);
        this.e.k();
        invalidateSelf();
    }

    private void b(float f2, c cVar) {
        a(f2, cVar);
        cVar.e(cVar.i() + (((cVar.g() - 0.01f) - cVar.i()) * f2));
        cVar.c(cVar.g());
        cVar.d(cVar.h() + ((((float) (Math.floor((double) (cVar.h() / 0.8f)) + 1.0d)) - cVar.h()) * f2));
    }

    public void a(int i2) {
        if (i2 == 0) {
            a(11.0f, 3.0f, 12.0f, 6.0f);
        } else {
            a(7.5f, 2.5f, 10.0f, 5.0f);
        }
        invalidateSelf();
    }

    public void a(boolean z) {
        this.e.a(z);
        invalidateSelf();
    }

    public void a(float f2) {
        this.e.a(f2);
        invalidateSelf();
    }

    public void a(float f2, float f3) {
        this.e.e(f2);
        this.e.c(f3);
        invalidateSelf();
    }

    public void a(int... iArr) {
        this.e.a(iArr);
        this.e.c(0);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, c cVar) {
        if (f2 > 0.75f) {
            cVar.b(a((f2 - 0.75f) / 0.25f, cVar.f(), cVar.c()));
        } else {
            cVar.b(cVar.f());
        }
    }

    /* access modifiers changed from: package-private */
    public void a(float f2, c cVar, boolean z) {
        float f3;
        float f4;
        if (this.f877j) {
            b(f2, cVar);
        } else if (f2 != 1.0f || z) {
            float h2 = cVar.h();
            if (f2 < 0.5f) {
                f3 = cVar.i();
                f4 = (l.getInterpolation(f2 / 0.5f) * 0.79f) + 0.01f + f3;
            } else {
                float i2 = cVar.i() + 0.79f;
                float f5 = i2;
                f3 = i2 - (((1.0f - l.getInterpolation((f2 - 0.5f) / 0.5f)) * 0.79f) + 0.01f);
                f4 = f5;
            }
            cVar.e(f3);
            cVar.c(f4);
            cVar.d(h2 + (0.20999998f * f2));
            d((f2 + this.f876i) * 216.0f);
        }
    }

    private void a() {
        c cVar = this.e;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new a(cVar));
        ofFloat.setRepeatCount(-1);
        ofFloat.setRepeatMode(1);
        ofFloat.setInterpolator(k);
        ofFloat.addListener(new C0047b(cVar));
        this.f875h = ofFloat;
    }
}
