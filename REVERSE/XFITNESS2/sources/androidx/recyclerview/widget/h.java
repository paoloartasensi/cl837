package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import androidx.core.h.v;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: FastScroller */
class h extends RecyclerView.n implements RecyclerView.s {
    private static final int[] D = {16842919};
    private static final int[] E = new int[0];
    int A = 0;
    private final Runnable B = new a();
    private final RecyclerView.t C = new b();
    private final int a;
    private final int b;
    final StateListDrawable c;
    final Drawable d;
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private final int f834f;

    /* renamed from: g  reason: collision with root package name */
    private final StateListDrawable f835g;

    /* renamed from: h  reason: collision with root package name */
    private final Drawable f836h;

    /* renamed from: i  reason: collision with root package name */
    private final int f837i;

    /* renamed from: j  reason: collision with root package name */
    private final int f838j;
    int k;
    int l;
    float m;
    int n;
    int o;
    float p;
    private int q = 0;
    private int r = 0;
    private RecyclerView s;
    private boolean t = false;
    private boolean u = false;
    private int v = 0;
    private int w = 0;
    private final int[] x = new int[2];
    private final int[] y = new int[2];
    final ValueAnimator z = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});

    /* compiled from: FastScroller */
    class a implements Runnable {
        a() {
        }

        public void run() {
            h.this.a(500);
        }
    }

    /* compiled from: FastScroller */
    class b extends RecyclerView.t {
        b() {
        }

        public void a(RecyclerView recyclerView, int i2, int i3) {
            h.this.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    }

    /* compiled from: FastScroller */
    private class c extends AnimatorListenerAdapter {
        private boolean a = false;

        c() {
        }

        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        public void onAnimationEnd(Animator animator) {
            if (this.a) {
                this.a = false;
            } else if (((Float) h.this.z.getAnimatedValue()).floatValue() == 0.0f) {
                h hVar = h.this;
                hVar.A = 0;
                hVar.b(0);
            } else {
                h hVar2 = h.this;
                hVar2.A = 2;
                hVar2.a();
            }
        }
    }

    /* compiled from: FastScroller */
    private class d implements ValueAnimator.AnimatorUpdateListener {
        d() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            h.this.c.setAlpha(floatValue);
            h.this.d.setAlpha(floatValue);
            h.this.a();
        }
    }

    h(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i2, int i3, int i4) {
        this.c = stateListDrawable;
        this.d = drawable;
        this.f835g = stateListDrawable2;
        this.f836h = drawable2;
        this.e = Math.max(i2, stateListDrawable.getIntrinsicWidth());
        this.f834f = Math.max(i2, drawable.getIntrinsicWidth());
        this.f837i = Math.max(i2, stateListDrawable2.getIntrinsicWidth());
        this.f838j = Math.max(i2, drawable2.getIntrinsicWidth());
        this.a = i3;
        this.b = i4;
        this.c.setAlpha(255);
        this.d.setAlpha(255);
        this.z.addListener(new c());
        this.z.addUpdateListener(new d());
        a(recyclerView);
    }

    private void c() {
        this.s.removeCallbacks(this.B);
    }

    private void d() {
        this.s.b((RecyclerView.n) this);
        this.s.removeOnItemTouchListener(this);
        this.s.removeOnScrollListener(this.C);
        c();
    }

    private int[] e() {
        int[] iArr = this.y;
        int i2 = this.b;
        iArr[0] = i2;
        iArr[1] = this.q - i2;
        return iArr;
    }

    private int[] f() {
        int[] iArr = this.x;
        int i2 = this.b;
        iArr[0] = i2;
        iArr[1] = this.r - i2;
        return iArr;
    }

    private boolean g() {
        return v.o(this.s) == 1;
    }

    private void h() {
        this.s.a((RecyclerView.n) this);
        this.s.addOnItemTouchListener(this);
        this.s.addOnScrollListener(this.C);
    }

    public void a(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.s;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                d();
            }
            this.s = recyclerView;
            if (recyclerView != null) {
                h();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        if (i2 == 2 && this.v != 2) {
            this.c.setState(D);
            c();
        }
        if (i2 == 0) {
            a();
        } else {
            b();
        }
        if (this.v == 2 && i2 != 2) {
            this.c.setState(E);
            c(1200);
        } else if (i2 == 1) {
            c(1500);
        }
        this.v = i2;
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int i2 = this.v;
        if (i2 == 1) {
            boolean b2 = b(motionEvent.getX(), motionEvent.getY());
            boolean a2 = a(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!b2 && !a2) {
                return false;
            }
            if (a2) {
                this.w = 1;
                this.p = (float) ((int) motionEvent.getX());
            } else if (b2) {
                this.w = 2;
                this.m = (float) ((int) motionEvent.getY());
            }
            b(2);
        } else if (i2 == 2) {
            return true;
        } else {
            return false;
        }
        return true;
    }

    public void onRequestDisallowInterceptTouchEvent(boolean z2) {
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.v != 0) {
            if (motionEvent.getAction() == 0) {
                boolean b2 = b(motionEvent.getX(), motionEvent.getY());
                boolean a2 = a(motionEvent.getX(), motionEvent.getY());
                if (b2 || a2) {
                    if (a2) {
                        this.w = 1;
                        this.p = (float) ((int) motionEvent.getX());
                    } else if (b2) {
                        this.w = 2;
                        this.m = (float) ((int) motionEvent.getY());
                    }
                    b(2);
                }
            } else if (motionEvent.getAction() == 1 && this.v == 2) {
                this.m = 0.0f;
                this.p = 0.0f;
                b(1);
                this.w = 0;
            } else if (motionEvent.getAction() == 2 && this.v == 2) {
                b();
                if (this.w == 1) {
                    a(motionEvent.getX());
                }
                if (this.w == 2) {
                    b(motionEvent.getY());
                }
            }
        }
    }

    private void c(int i2) {
        c();
        this.s.postDelayed(this.B, (long) i2);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.s.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        int i3 = this.A;
        if (i3 == 1) {
            this.z.cancel();
        } else if (i3 != 2) {
            return;
        }
        this.A = 3;
        ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(new float[]{((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f});
        this.z.setDuration((long) i2);
        this.z.start();
    }

    public void b() {
        int i2 = this.A;
        if (i2 != 0) {
            if (i2 == 3) {
                this.z.cancel();
            } else {
                return;
            }
        }
        this.A = 1;
        ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(new float[]{((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f});
        this.z.setDuration(500);
        this.z.setStartDelay(0);
        this.z.start();
    }

    private void a(Canvas canvas) {
        int i2 = this.r;
        int i3 = this.f837i;
        int i4 = i2 - i3;
        int i5 = this.o;
        int i6 = this.n;
        int i7 = i5 - (i6 / 2);
        this.f835g.setBounds(0, 0, i6, i3);
        this.f836h.setBounds(0, 0, this.q, this.f838j);
        canvas.translate(0.0f, (float) i4);
        this.f836h.draw(canvas);
        canvas.translate((float) i7, 0.0f);
        this.f835g.draw(canvas);
        canvas.translate((float) (-i7), (float) (-i4));
    }

    public void b(Canvas canvas, RecyclerView recyclerView, RecyclerView.z zVar) {
        if (this.q != this.s.getWidth() || this.r != this.s.getHeight()) {
            this.q = this.s.getWidth();
            this.r = this.s.getHeight();
            b(0);
        } else if (this.A != 0) {
            if (this.t) {
                b(canvas);
            }
            if (this.u) {
                a(canvas);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        int computeVerticalScrollRange = this.s.computeVerticalScrollRange();
        int i4 = this.r;
        this.t = computeVerticalScrollRange - i4 > 0 && i4 >= this.a;
        int computeHorizontalScrollRange = this.s.computeHorizontalScrollRange();
        int i5 = this.q;
        boolean z2 = computeHorizontalScrollRange - i5 > 0 && i5 >= this.a;
        this.u = z2;
        if (this.t || z2) {
            if (this.t) {
                float f2 = (float) i4;
                this.l = (int) ((f2 * (((float) i3) + (f2 / 2.0f))) / ((float) computeVerticalScrollRange));
                this.k = Math.min(i4, (i4 * i4) / computeVerticalScrollRange);
            }
            if (this.u) {
                float f3 = (float) i5;
                this.o = (int) ((f3 * (((float) i2) + (f3 / 2.0f))) / ((float) computeHorizontalScrollRange));
                this.n = Math.min(i5, (i5 * i5) / computeHorizontalScrollRange);
            }
            int i6 = this.v;
            if (i6 == 0 || i6 == 1) {
                b(1);
            }
        } else if (this.v != 0) {
            b(0);
        }
    }

    private void b(Canvas canvas) {
        int i2 = this.q;
        int i3 = this.e;
        int i4 = i2 - i3;
        int i5 = this.l;
        int i6 = this.k;
        int i7 = i5 - (i6 / 2);
        this.c.setBounds(0, 0, i3, i6);
        this.d.setBounds(0, 0, this.f834f, this.r);
        if (g()) {
            this.d.draw(canvas);
            canvas.translate((float) this.e, (float) i7);
            canvas.scale(-1.0f, 1.0f);
            this.c.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            canvas.translate((float) (-this.e), (float) (-i7));
            return;
        }
        canvas.translate((float) i4, 0.0f);
        this.d.draw(canvas);
        canvas.translate(0.0f, (float) i7);
        this.c.draw(canvas);
        canvas.translate((float) (-i4), (float) (-i7));
    }

    private void a(float f2) {
        int[] e2 = e();
        float max = Math.max((float) e2[0], Math.min((float) e2[1], f2));
        if (Math.abs(((float) this.o) - max) >= 2.0f) {
            int a2 = a(this.p, max, e2, this.s.computeHorizontalScrollRange(), this.s.computeHorizontalScrollOffset(), this.q);
            if (a2 != 0) {
                this.s.scrollBy(a2, 0);
            }
            this.p = max;
        }
    }

    private void b(float f2) {
        int[] f3 = f();
        float max = Math.max((float) f3[0], Math.min((float) f3[1], f2));
        if (Math.abs(((float) this.l) - max) >= 2.0f) {
            int a2 = a(this.m, max, f3, this.s.computeVerticalScrollRange(), this.s.computeVerticalScrollOffset(), this.r);
            if (a2 != 0) {
                this.s.scrollBy(0, a2);
            }
            this.m = max;
        }
    }

    private int a(float f2, float f3, int[] iArr, int i2, int i3, int i4) {
        int i5 = iArr[1] - iArr[0];
        if (i5 == 0) {
            return 0;
        }
        int i6 = i2 - i4;
        int i7 = (int) (((f3 - f2) / ((float) i5)) * ((float) i6));
        int i8 = i3 + i7;
        if (i8 >= i6 || i8 < 0) {
            return 0;
        }
        return i7;
    }

    /* access modifiers changed from: package-private */
    public boolean a(float f2, float f3) {
        if (f3 >= ((float) (this.r - this.f837i))) {
            int i2 = this.o;
            int i3 = this.n;
            return f2 >= ((float) (i2 - (i3 / 2))) && f2 <= ((float) (i2 + (i3 / 2)));
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(float f2, float f3) {
        if (!g() ? f2 >= ((float) (this.q - this.e)) : f2 <= ((float) (this.e / 2))) {
            int i2 = this.l;
            int i3 = this.k;
            return f3 >= ((float) (i2 - (i3 / 2))) && f3 <= ((float) (i2 + (i3 / 2)));
        }
    }
}
