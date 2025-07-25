package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

/* compiled from: ScrollEventAdapter */
final class e extends RecyclerView.t {
    private ViewPager2.i a;
    private final ViewPager2 b;
    private final RecyclerView c;
    private final LinearLayoutManager d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f973f;

    /* renamed from: g  reason: collision with root package name */
    private a f974g = new a();

    /* renamed from: h  reason: collision with root package name */
    private int f975h;

    /* renamed from: i  reason: collision with root package name */
    private int f976i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f977j;
    private boolean k;
    private boolean l;
    private boolean m;

    /* compiled from: ScrollEventAdapter */
    private static final class a {
        int a;
        float b;
        int c;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = -1;
            this.b = 0.0f;
            this.c = 0;
        }
    }

    e(ViewPager2 viewPager2) {
        this.b = viewPager2;
        RecyclerView recyclerView = viewPager2.n;
        this.c = recyclerView;
        this.d = (LinearLayoutManager) recyclerView.getLayoutManager();
        h();
    }

    private int f() {
        return this.d.H();
    }

    private boolean g() {
        int i2 = this.e;
        return i2 == 1 || i2 == 4;
    }

    private void h() {
        this.e = 0;
        this.f973f = 0;
        this.f974g.a();
        this.f975h = -1;
        this.f976i = -1;
        this.f977j = false;
        this.k = false;
        this.m = false;
        this.l = false;
    }

    private void i() {
        int i2;
        a aVar = this.f974g;
        int H = this.d.H();
        aVar.a = H;
        if (H == -1) {
            aVar.a();
            return;
        }
        View c2 = this.d.c(H);
        if (c2 == null) {
            aVar.a();
            return;
        }
        int k2 = this.d.k(c2);
        int m2 = this.d.m(c2);
        int n = this.d.n(c2);
        int d2 = this.d.d(c2);
        ViewGroup.LayoutParams layoutParams = c2.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            k2 += marginLayoutParams.leftMargin;
            m2 += marginLayoutParams.rightMargin;
            n += marginLayoutParams.topMargin;
            d2 += marginLayoutParams.bottomMargin;
        }
        int height = c2.getHeight() + n + d2;
        int width = c2.getWidth() + k2 + m2;
        if (this.d.K() == 0) {
            i2 = (c2.getLeft() - k2) - this.c.getPaddingLeft();
            if (this.b.b()) {
                i2 = -i2;
            }
            height = width;
        } else {
            i2 = (c2.getTop() - n) - this.c.getPaddingTop();
        }
        int i3 = -i2;
        aVar.c = i3;
        if (i3 >= 0) {
            aVar.b = height == 0 ? 0.0f : ((float) i3) / ((float) height);
        } else if (new a(this.d).a()) {
            throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
        } else {
            throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", new Object[]{Integer.valueOf(aVar.c)}));
        }
    }

    public void a(RecyclerView recyclerView, int i2) {
        boolean z = true;
        if (!(this.e == 1 && this.f973f == 1) && i2 == 1) {
            a(false);
        } else if (!g() || i2 != 2) {
            if (g() && i2 == 0) {
                i();
                if (!this.k) {
                    int i3 = this.f974g.a;
                    if (i3 != -1) {
                        a(i3, 0.0f, 0);
                    }
                } else {
                    a aVar = this.f974g;
                    if (aVar.c == 0) {
                        int i4 = this.f975h;
                        int i5 = aVar.a;
                        if (i4 != i5) {
                            a(i5);
                        }
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    b(0);
                    h();
                }
            }
            if (this.e == 2 && i2 == 0 && this.l) {
                i();
                a aVar2 = this.f974g;
                if (aVar2.c == 0) {
                    int i6 = this.f976i;
                    int i7 = aVar2.a;
                    if (i6 != i7) {
                        if (i7 == -1) {
                            i7 = 0;
                        }
                        a(i7);
                    }
                    b(0);
                    h();
                }
            }
        } else if (this.k) {
            b(2);
            this.f977j = true;
        }
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.f973f;
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return this.f973f == 0;
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.l = true;
    }

    private void b(int i2) {
        if ((this.e != 3 || this.f973f != 0) && this.f973f != i2) {
            this.f973f = i2;
            ViewPager2.i iVar = this.a;
            if (iVar != null) {
                iVar.a(i2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        if ((r5 < 0) == r3.b.b()) goto L_0x0022;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.recyclerview.widget.RecyclerView r4, int r5, int r6) {
        /*
            r3 = this;
            r4 = 1
            r3.k = r4
            r3.i()
            boolean r0 = r3.f977j
            r1 = -1
            r2 = 0
            if (r0 == 0) goto L_0x003d
            r3.f977j = r2
            if (r6 > 0) goto L_0x0022
            if (r6 != 0) goto L_0x0020
            if (r5 >= 0) goto L_0x0016
            r5 = 1
            goto L_0x0017
        L_0x0016:
            r5 = 0
        L_0x0017:
            androidx.viewpager2.widget.ViewPager2 r6 = r3.b
            boolean r6 = r6.b()
            if (r5 != r6) goto L_0x0020
            goto L_0x0022
        L_0x0020:
            r5 = 0
            goto L_0x0023
        L_0x0022:
            r5 = 1
        L_0x0023:
            if (r5 == 0) goto L_0x002f
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r6 = r5.c
            if (r6 == 0) goto L_0x002f
            int r5 = r5.a
            int r5 = r5 + r4
            goto L_0x0033
        L_0x002f:
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r5 = r5.a
        L_0x0033:
            r3.f976i = r5
            int r6 = r3.f975h
            if (r6 == r5) goto L_0x004b
            r3.a((int) r5)
            goto L_0x004b
        L_0x003d:
            int r5 = r3.e
            if (r5 != 0) goto L_0x004b
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r5 = r5.a
            if (r5 != r1) goto L_0x0048
            r5 = 0
        L_0x0048:
            r3.a((int) r5)
        L_0x004b:
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r5 = r5.a
            if (r5 != r1) goto L_0x0052
            r5 = 0
        L_0x0052:
            androidx.viewpager2.widget.e$a r6 = r3.f974g
            float r0 = r6.b
            int r6 = r6.c
            r3.a((int) r5, (float) r0, (int) r6)
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r5 = r5.a
            int r6 = r3.f976i
            if (r5 == r6) goto L_0x0065
            if (r6 != r1) goto L_0x0075
        L_0x0065:
            androidx.viewpager2.widget.e$a r5 = r3.f974g
            int r5 = r5.c
            if (r5 != 0) goto L_0x0075
            int r5 = r3.f973f
            if (r5 == r4) goto L_0x0075
            r3.b(r2)
            r3.h()
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.viewpager2.widget.e.a(androidx.recyclerview.widget.RecyclerView, int, int):void");
    }

    private void a(boolean z) {
        this.m = z;
        this.e = z ? 4 : 1;
        int i2 = this.f976i;
        if (i2 != -1) {
            this.f975h = i2;
            this.f976i = -1;
        } else if (this.f975h == -1) {
            this.f975h = f();
        }
        b(1);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z) {
        this.e = z ? 2 : 3;
        boolean z2 = false;
        this.m = false;
        if (this.f976i != i2) {
            z2 = true;
        }
        this.f976i = i2;
        b(2);
        if (z2) {
            a(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ViewPager2.i iVar) {
        this.a = iVar;
    }

    /* access modifiers changed from: package-private */
    public double a() {
        i();
        a aVar = this.f974g;
        double d2 = (double) aVar.a;
        double d3 = (double) aVar.b;
        Double.isNaN(d2);
        Double.isNaN(d3);
        return d2 + d3;
    }

    private void a(int i2) {
        ViewPager2.i iVar = this.a;
        if (iVar != null) {
            iVar.b(i2);
        }
    }

    private void a(int i2, float f2, int i3) {
        ViewPager2.i iVar = this.a;
        if (iVar != null) {
            iVar.a(i2, f2, i3);
        }
    }
}
