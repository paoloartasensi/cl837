package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: LinearSmoothScroller */
public class n extends RecyclerView.y {

    /* renamed from: i  reason: collision with root package name */
    protected final LinearInterpolator f853i = new LinearInterpolator();

    /* renamed from: j  reason: collision with root package name */
    protected final DecelerateInterpolator f854j = new DecelerateInterpolator();
    protected PointF k;
    private final DisplayMetrics l;
    private boolean m = false;
    private float n;
    protected int o = 0;
    protected int p = 0;

    public n(Context context) {
        this.l = context.getResources().getDisplayMetrics();
    }

    private int b(int i2, int i3) {
        int i4 = i2 - i3;
        if (i2 * i4 <= 0) {
            return 0;
        }
        return i4;
    }

    private float k() {
        if (!this.m) {
            this.n = a(this.l);
            this.m = true;
        }
        return this.n;
    }

    /* access modifiers changed from: protected */
    public void a(View view, RecyclerView.z zVar, RecyclerView.y.a aVar) {
        int a = a(view, i());
        int b = b(view, j());
        int d = d((int) Math.sqrt((double) ((a * a) + (b * b))));
        if (d > 0) {
            aVar.a(-a, -b, d, this.f854j);
        }
    }

    public int b(View view, int i2) {
        RecyclerView.o b = b();
        if (b == null || !b.b()) {
            return 0;
        }
        RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
        return a(b.j(view) - pVar.topMargin, b.e(view) + pVar.bottomMargin, b.q(), b.h() - b.n(), i2);
    }

    /* access modifiers changed from: protected */
    public int d(int i2) {
        double e = (double) e(i2);
        Double.isNaN(e);
        return (int) Math.ceil(e / 0.3356d);
    }

    /* access modifiers changed from: protected */
    public int e(int i2) {
        return (int) Math.ceil((double) (((float) Math.abs(i2)) * k()));
    }

    /* access modifiers changed from: protected */
    public void f() {
    }

    /* access modifiers changed from: protected */
    public void g() {
        this.p = 0;
        this.o = 0;
        this.k = null;
    }

    /* access modifiers changed from: protected */
    public int i() {
        PointF pointF = this.k;
        if (pointF != null) {
            float f2 = pointF.x;
            if (f2 != 0.0f) {
                return f2 > 0.0f ? 1 : -1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int j() {
        PointF pointF = this.k;
        if (pointF != null) {
            float f2 = pointF.y;
            if (f2 != 0.0f) {
                return f2 > 0.0f ? 1 : -1;
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void a(int i2, int i3, RecyclerView.z zVar, RecyclerView.y.a aVar) {
        if (a() == 0) {
            h();
            return;
        }
        this.o = b(this.o, i2);
        int b = b(this.p, i3);
        this.p = b;
        if (this.o == 0 && b == 0) {
            a(aVar);
        }
    }

    /* access modifiers changed from: protected */
    public float a(DisplayMetrics displayMetrics) {
        return 25.0f / ((float) displayMetrics.densityDpi);
    }

    /* access modifiers changed from: protected */
    public void a(RecyclerView.y.a aVar) {
        PointF a = a(c());
        if (a == null || (a.x == 0.0f && a.y == 0.0f)) {
            aVar.a(c());
            h();
            return;
        }
        a(a);
        this.k = a;
        this.o = (int) (a.x * 10000.0f);
        this.p = (int) (a.y * 10000.0f);
        aVar.a((int) (((float) this.o) * 1.2f), (int) (((float) this.p) * 1.2f), (int) (((float) e(10000)) * 1.2f), this.f853i);
    }

    public int a(int i2, int i3, int i4, int i5, int i6) {
        if (i6 == -1) {
            return i4 - i2;
        }
        if (i6 == 0) {
            int i7 = i4 - i2;
            if (i7 > 0) {
                return i7;
            }
            int i8 = i5 - i3;
            if (i8 < 0) {
                return i8;
            }
            return 0;
        } else if (i6 == 1) {
            return i5 - i3;
        } else {
            throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    public int a(View view, int i2) {
        RecyclerView.o b = b();
        if (b == null || !b.a()) {
            return 0;
        }
        RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
        return a(b.f(view) - pVar.leftMargin, b.i(view) + pVar.rightMargin, b.o(), b.r() - b.p(), i2);
    }
}
