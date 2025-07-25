package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.h.v;

/* compiled from: AutoScrollHelper */
public abstract class a implements View.OnTouchListener {
    private static final int v = ViewConfiguration.getTapTimeout();
    final C0027a e = new C0027a();

    /* renamed from: f  reason: collision with root package name */
    private final Interpolator f533f = new AccelerateInterpolator();

    /* renamed from: g  reason: collision with root package name */
    final View f534g;

    /* renamed from: h  reason: collision with root package name */
    private Runnable f535h;

    /* renamed from: i  reason: collision with root package name */
    private float[] f536i = {0.0f, 0.0f};

    /* renamed from: j  reason: collision with root package name */
    private float[] f537j = {Float.MAX_VALUE, Float.MAX_VALUE};
    private int k;
    private int l;
    private float[] m = {0.0f, 0.0f};
    private float[] n = {0.0f, 0.0f};
    private float[] o = {Float.MAX_VALUE, Float.MAX_VALUE};
    private boolean p;
    boolean q;
    boolean r;
    boolean s;
    private boolean t;
    private boolean u;

    /* renamed from: androidx.core.widget.a$a  reason: collision with other inner class name */
    /* compiled from: AutoScrollHelper */
    private static class C0027a {
        private int a;
        private int b;
        private float c;
        private float d;
        private long e = Long.MIN_VALUE;

        /* renamed from: f  reason: collision with root package name */
        private long f538f = 0;

        /* renamed from: g  reason: collision with root package name */
        private int f539g = 0;

        /* renamed from: h  reason: collision with root package name */
        private int f540h = 0;

        /* renamed from: i  reason: collision with root package name */
        private long f541i = -1;

        /* renamed from: j  reason: collision with root package name */
        private float f542j;
        private int k;

        C0027a() {
        }

        private float a(float f2) {
            return (-4.0f * f2 * f2) + (f2 * 4.0f);
        }

        public void a(int i2) {
            this.b = i2;
        }

        public void b(int i2) {
            this.a = i2;
        }

        public int c() {
            return this.f540h;
        }

        public int d() {
            float f2 = this.c;
            return (int) (f2 / Math.abs(f2));
        }

        public int e() {
            float f2 = this.d;
            return (int) (f2 / Math.abs(f2));
        }

        public boolean f() {
            return this.f541i > 0 && AnimationUtils.currentAnimationTimeMillis() > this.f541i + ((long) this.k);
        }

        public void g() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.k = a.a((int) (currentAnimationTimeMillis - this.e), 0, this.b);
            this.f542j = a(currentAnimationTimeMillis);
            this.f541i = currentAnimationTimeMillis;
        }

        public void h() {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.e = currentAnimationTimeMillis;
            this.f541i = -1;
            this.f538f = currentAnimationTimeMillis;
            this.f542j = 0.5f;
            this.f539g = 0;
            this.f540h = 0;
        }

        private float a(long j2) {
            if (j2 < this.e) {
                return 0.0f;
            }
            long j3 = this.f541i;
            if (j3 < 0 || j2 < j3) {
                return a.a(((float) (j2 - this.e)) / ((float) this.a), 0.0f, 1.0f) * 0.5f;
            }
            long j4 = j2 - j3;
            float f2 = this.f542j;
            return (1.0f - f2) + (f2 * a.a(((float) j4) / ((float) this.k), 0.0f, 1.0f));
        }

        public int b() {
            return this.f539g;
        }

        public void a() {
            if (this.f538f != 0) {
                long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                float a2 = a(a(currentAnimationTimeMillis));
                this.f538f = currentAnimationTimeMillis;
                float f2 = ((float) (currentAnimationTimeMillis - this.f538f)) * a2;
                this.f539g = (int) (this.c * f2);
                this.f540h = (int) (f2 * this.d);
                return;
            }
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }

        public void a(float f2, float f3) {
            this.c = f2;
            this.d = f3;
        }
    }

    /* compiled from: AutoScrollHelper */
    private class b implements Runnable {
        b() {
        }

        public void run() {
            a aVar = a.this;
            if (aVar.s) {
                if (aVar.q) {
                    aVar.q = false;
                    aVar.e.h();
                }
                C0027a aVar2 = a.this.e;
                if (aVar2.f() || !a.this.b()) {
                    a.this.s = false;
                    return;
                }
                a aVar3 = a.this;
                if (aVar3.r) {
                    aVar3.r = false;
                    aVar3.a();
                }
                aVar2.a();
                a.this.a(aVar2.b(), aVar2.c());
                v.a(a.this.f534g, (Runnable) this);
            }
        }
    }

    public a(View view) {
        this.f534g = view;
        float f2 = Resources.getSystem().getDisplayMetrics().density;
        float f3 = (float) ((int) ((1575.0f * f2) + 0.5f));
        b(f3, f3);
        float f4 = (float) ((int) ((f2 * 315.0f) + 0.5f));
        c(f4, f4);
        d(1);
        a(Float.MAX_VALUE, Float.MAX_VALUE);
        d(0.2f, 0.2f);
        e(1.0f, 1.0f);
        c(v);
        f(500);
        e(500);
    }

    static float a(float f2, float f3, float f4) {
        return f2 > f4 ? f4 : f2 < f3 ? f3 : f2;
    }

    static int a(int i2, int i3, int i4) {
        return i2 > i4 ? i4 : i2 < i3 ? i3 : i2;
    }

    public a a(boolean z) {
        if (this.t && !z) {
            c();
        }
        this.t = z;
        return this;
    }

    public abstract void a(int i2, int i3);

    public abstract boolean a(int i2);

    public a b(float f2, float f3) {
        float[] fArr = this.o;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public abstract boolean b(int i2);

    public a c(float f2, float f3) {
        float[] fArr = this.n;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public a d(int i2) {
        this.k = i2;
        return this;
    }

    public a e(float f2, float f3) {
        float[] fArr = this.m;
        fArr[0] = f2 / 1000.0f;
        fArr[1] = f3 / 1000.0f;
        return this;
    }

    public a f(int i2) {
        this.e.b(i2);
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        if (r0 != 3) goto L_0x0058;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0060 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            boolean r0 = r5.t
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r7.getActionMasked()
            r2 = 1
            if (r0 == 0) goto L_0x001a
            if (r0 == r2) goto L_0x0016
            r3 = 2
            if (r0 == r3) goto L_0x001e
            r6 = 3
            if (r0 == r6) goto L_0x0016
            goto L_0x0058
        L_0x0016:
            r5.c()
            goto L_0x0058
        L_0x001a:
            r5.r = r2
            r5.p = r1
        L_0x001e:
            float r0 = r7.getX()
            int r3 = r6.getWidth()
            float r3 = (float) r3
            android.view.View r4 = r5.f534g
            int r4 = r4.getWidth()
            float r4 = (float) r4
            float r0 = r5.a((int) r1, (float) r0, (float) r3, (float) r4)
            float r7 = r7.getY()
            int r6 = r6.getHeight()
            float r6 = (float) r6
            android.view.View r3 = r5.f534g
            int r3 = r3.getHeight()
            float r3 = (float) r3
            float r6 = r5.a((int) r2, (float) r7, (float) r6, (float) r3)
            androidx.core.widget.a$a r7 = r5.e
            r7.a(r0, r6)
            boolean r6 = r5.s
            if (r6 != 0) goto L_0x0058
            boolean r6 = r5.b()
            if (r6 == 0) goto L_0x0058
            r5.d()
        L_0x0058:
            boolean r6 = r5.u
            if (r6 == 0) goto L_0x0061
            boolean r6 = r5.s
            if (r6 == 0) goto L_0x0061
            r1 = 1
        L_0x0061:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.widget.a.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    private float f(float f2, float f3) {
        if (f3 == 0.0f) {
            return 0.0f;
        }
        int i2 = this.k;
        if (i2 == 0 || i2 == 1) {
            if (f2 < f3) {
                if (f2 >= 0.0f) {
                    return 1.0f - (f2 / f3);
                }
                return (!this.s || this.k != 1) ? 0.0f : 1.0f;
            }
        } else if (i2 == 2 && f2 < 0.0f) {
            return f2 / (-f3);
        }
    }

    public a d(float f2, float f3) {
        float[] fArr = this.f536i;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        C0027a aVar = this.e;
        int e2 = aVar.e();
        int d = aVar.d();
        return (e2 != 0 && b(e2)) || (d != 0 && a(d));
    }

    public a c(int i2) {
        this.l = i2;
        return this;
    }

    public a e(int i2) {
        this.e.a(i2);
        return this;
    }

    private void c() {
        if (this.q) {
            this.s = false;
        } else {
            this.e.g();
        }
    }

    private void d() {
        int i2;
        if (this.f535h == null) {
            this.f535h = new b();
        }
        this.s = true;
        this.q = true;
        if (this.p || (i2 = this.l) <= 0) {
            this.f535h.run();
        } else {
            v.a(this.f534g, this.f535h, (long) i2);
        }
        this.p = true;
    }

    public a a(float f2, float f3) {
        float[] fArr = this.f537j;
        fArr[0] = f2;
        fArr[1] = f3;
        return this;
    }

    private float a(int i2, float f2, float f3, float f4) {
        float a = a(this.f536i[i2], f3, this.f537j[i2], f2);
        if (a == 0.0f) {
            return 0.0f;
        }
        float f5 = this.m[i2];
        float f6 = this.n[i2];
        float f7 = this.o[i2];
        float f8 = f5 * f4;
        if (a > 0.0f) {
            return a(a * f8, f6, f7);
        }
        return -a((-a) * f8, f6, f7);
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6;
        float a = a(f2 * f3, 0.0f, f4);
        float f7 = f(f3 - f5, a) - f(f5, a);
        if (f7 < 0.0f) {
            f6 = -this.f533f.getInterpolation(-f7);
        } else if (f7 <= 0.0f) {
            return 0.0f;
        } else {
            f6 = this.f533f.getInterpolation(f7);
        }
        return a(f6, -1.0f, 1.0f);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.f534g.onTouchEvent(obtain);
        obtain.recycle();
    }
}
