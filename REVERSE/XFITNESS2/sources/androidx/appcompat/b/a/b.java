package androidx.appcompat.b.a;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;

/* compiled from: DrawableContainer */
class b extends Drawable implements Drawable.Callback {
    private c e;

    /* renamed from: f  reason: collision with root package name */
    private Rect f83f;

    /* renamed from: g  reason: collision with root package name */
    private Drawable f84g;

    /* renamed from: h  reason: collision with root package name */
    private Drawable f85h;

    /* renamed from: i  reason: collision with root package name */
    private int f86i = 255;

    /* renamed from: j  reason: collision with root package name */
    private boolean f87j;
    private int k = -1;
    private boolean l;
    private Runnable m;
    private long n;
    private long o;
    private C0004b p;

    /* compiled from: DrawableContainer */
    class a implements Runnable {
        a() {
        }

        public void run() {
            b.this.a(true);
            b.this.invalidateSelf();
        }
    }

    /* renamed from: androidx.appcompat.b.a.b$b  reason: collision with other inner class name */
    /* compiled from: DrawableContainer */
    static class C0004b implements Drawable.Callback {
        private Drawable.Callback e;

        C0004b() {
        }

        public C0004b a(Drawable.Callback callback) {
            this.e = callback;
            return this;
        }

        public void invalidateDrawable(Drawable drawable) {
        }

        public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
            Drawable.Callback callback = this.e;
            if (callback != null) {
                callback.scheduleDrawable(drawable, runnable, j2);
            }
        }

        public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
            Drawable.Callback callback = this.e;
            if (callback != null) {
                callback.unscheduleDrawable(drawable, runnable);
            }
        }

        public Drawable.Callback a() {
            Drawable.Callback callback = this.e;
            this.e = null;
            return callback;
        }
    }

    /* compiled from: DrawableContainer */
    static abstract class c extends Drawable.ConstantState {
        int A;
        int B;
        boolean C;
        ColorFilter D;
        boolean E;
        ColorStateList F;
        PorterDuff.Mode G;
        boolean H;
        boolean I;
        final b a;
        Resources b;
        int c = 160;
        int d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        SparseArray<Drawable.ConstantState> f88f;

        /* renamed from: g  reason: collision with root package name */
        Drawable[] f89g;

        /* renamed from: h  reason: collision with root package name */
        int f90h;

        /* renamed from: i  reason: collision with root package name */
        boolean f91i;

        /* renamed from: j  reason: collision with root package name */
        boolean f92j;
        Rect k;
        boolean l;
        boolean m;
        int n;
        int o;
        int p;
        int q;
        boolean r;
        int s;
        boolean t;
        boolean u;
        boolean v;
        boolean w;
        boolean x;
        boolean y;
        int z;

        c(c cVar, b bVar, Resources resources) {
            Resources resources2;
            this.f91i = false;
            this.l = false;
            this.x = true;
            this.A = 0;
            this.B = 0;
            this.a = bVar;
            if (resources != null) {
                resources2 = resources;
            } else {
                resources2 = cVar != null ? cVar.b : null;
            }
            this.b = resources2;
            int a2 = b.a(resources, cVar != null ? cVar.c : 0);
            this.c = a2;
            if (cVar != null) {
                this.d = cVar.d;
                this.e = cVar.e;
                this.v = true;
                this.w = true;
                this.f91i = cVar.f91i;
                this.l = cVar.l;
                this.x = cVar.x;
                this.y = cVar.y;
                this.z = cVar.z;
                this.A = cVar.A;
                this.B = cVar.B;
                this.C = cVar.C;
                this.D = cVar.D;
                this.E = cVar.E;
                this.F = cVar.F;
                this.G = cVar.G;
                this.H = cVar.H;
                this.I = cVar.I;
                if (cVar.c == a2) {
                    if (cVar.f92j) {
                        this.k = new Rect(cVar.k);
                        this.f92j = true;
                    }
                    if (cVar.m) {
                        this.n = cVar.n;
                        this.o = cVar.o;
                        this.p = cVar.p;
                        this.q = cVar.q;
                        this.m = true;
                    }
                }
                if (cVar.r) {
                    this.s = cVar.s;
                    this.r = true;
                }
                if (cVar.t) {
                    this.u = cVar.u;
                    this.t = true;
                }
                Drawable[] drawableArr = cVar.f89g;
                this.f89g = new Drawable[drawableArr.length];
                this.f90h = cVar.f90h;
                SparseArray<Drawable.ConstantState> sparseArray = cVar.f88f;
                if (sparseArray != null) {
                    this.f88f = sparseArray.clone();
                } else {
                    this.f88f = new SparseArray<>(this.f90h);
                }
                int i2 = this.f90h;
                for (int i3 = 0; i3 < i2; i3++) {
                    if (drawableArr[i3] != null) {
                        Drawable.ConstantState constantState = drawableArr[i3].getConstantState();
                        if (constantState != null) {
                            this.f88f.put(i3, constantState);
                        } else {
                            this.f89g[i3] = drawableArr[i3];
                        }
                    }
                }
                return;
            }
            this.f89g = new Drawable[10];
            this.f90h = 0;
        }

        private Drawable b(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(this.z);
            }
            Drawable mutate = drawable.mutate();
            mutate.setCallback(this.a);
            return mutate;
        }

        private void n() {
            SparseArray<Drawable.ConstantState> sparseArray = this.f88f;
            if (sparseArray != null) {
                int size = sparseArray.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.f89g[this.f88f.keyAt(i2)] = b(this.f88f.valueAt(i2).newDrawable(this.b));
                }
                this.f88f = null;
            }
        }

        public final int a(Drawable drawable) {
            int i2 = this.f90h;
            if (i2 >= this.f89g.length) {
                a(i2, i2 + 10);
            }
            drawable.mutate();
            drawable.setVisible(false, true);
            drawable.setCallback(this.a);
            this.f89g[i2] = drawable;
            this.f90h++;
            this.e = drawable.getChangingConfigurations() | this.e;
            k();
            this.k = null;
            this.f92j = false;
            this.m = false;
            this.v = false;
            return i2;
        }

        /* access modifiers changed from: package-private */
        public final int c() {
            return this.f89g.length;
        }

        public boolean canApplyTheme() {
            int i2 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                if (drawable == null) {
                    Drawable.ConstantState constantState = this.f88f.get(i3);
                    if (constantState != null && constantState.canApplyTheme()) {
                        return true;
                    }
                } else if (drawable.canApplyTheme()) {
                    return true;
                }
            }
            return false;
        }

        public final int d() {
            return this.f90h;
        }

        public final int e() {
            if (!this.m) {
                b();
            }
            return this.o;
        }

        public final int f() {
            if (!this.m) {
                b();
            }
            return this.q;
        }

        public final int g() {
            if (!this.m) {
                b();
            }
            return this.p;
        }

        public int getChangingConfigurations() {
            return this.d | this.e;
        }

        public final Rect h() {
            Rect rect = null;
            if (this.f91i) {
                return null;
            }
            if (this.k != null || this.f92j) {
                return this.k;
            }
            n();
            Rect rect2 = new Rect();
            int i2 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getPadding(rect2)) {
                    if (rect == null) {
                        rect = new Rect(0, 0, 0, 0);
                    }
                    int i4 = rect2.left;
                    if (i4 > rect.left) {
                        rect.left = i4;
                    }
                    int i5 = rect2.top;
                    if (i5 > rect.top) {
                        rect.top = i5;
                    }
                    int i6 = rect2.right;
                    if (i6 > rect.right) {
                        rect.right = i6;
                    }
                    int i7 = rect2.bottom;
                    if (i7 > rect.bottom) {
                        rect.bottom = i7;
                    }
                }
            }
            this.f92j = true;
            this.k = rect;
            return rect;
        }

        public final int i() {
            if (!this.m) {
                b();
            }
            return this.n;
        }

        public final int j() {
            if (this.r) {
                return this.s;
            }
            n();
            int i2 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            int opacity = i2 > 0 ? drawableArr[0].getOpacity() : -2;
            for (int i3 = 1; i3 < i2; i3++) {
                opacity = Drawable.resolveOpacity(opacity, drawableArr[i3].getOpacity());
            }
            this.s = opacity;
            this.r = true;
            return opacity;
        }

        /* access modifiers changed from: package-private */
        public void k() {
            this.r = false;
            this.t = false;
        }

        public final boolean l() {
            return this.l;
        }

        /* access modifiers changed from: package-private */
        public abstract void m();

        public final void c(int i2) {
            this.B = i2;
        }

        /* access modifiers changed from: package-private */
        public final boolean b(int i2, int i3) {
            int i4 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            boolean z2 = false;
            for (int i5 = 0; i5 < i4; i5++) {
                if (drawableArr[i5] != null) {
                    boolean layoutDirection = Build.VERSION.SDK_INT >= 23 ? drawableArr[i5].setLayoutDirection(i2) : false;
                    if (i5 == i3) {
                        z2 = layoutDirection;
                    }
                }
            }
            this.z = i2;
            return z2;
        }

        public final void b(boolean z2) {
            this.f91i = z2;
        }

        /* access modifiers changed from: protected */
        public void b() {
            this.m = true;
            n();
            int i2 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            this.o = -1;
            this.n = -1;
            this.q = 0;
            this.p = 0;
            for (int i3 = 0; i3 < i2; i3++) {
                Drawable drawable = drawableArr[i3];
                int intrinsicWidth = drawable.getIntrinsicWidth();
                if (intrinsicWidth > this.n) {
                    this.n = intrinsicWidth;
                }
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicHeight > this.o) {
                    this.o = intrinsicHeight;
                }
                int minimumWidth = drawable.getMinimumWidth();
                if (minimumWidth > this.p) {
                    this.p = minimumWidth;
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (minimumHeight > this.q) {
                    this.q = minimumHeight;
                }
            }
        }

        public final Drawable a(int i2) {
            int indexOfKey;
            Drawable drawable = this.f89g[i2];
            if (drawable != null) {
                return drawable;
            }
            SparseArray<Drawable.ConstantState> sparseArray = this.f88f;
            if (sparseArray == null || (indexOfKey = sparseArray.indexOfKey(i2)) < 0) {
                return null;
            }
            Drawable b2 = b(this.f88f.valueAt(indexOfKey).newDrawable(this.b));
            this.f89g[i2] = b2;
            this.f88f.removeAt(indexOfKey);
            if (this.f88f.size() == 0) {
                this.f88f = null;
            }
            return b2;
        }

        /* access modifiers changed from: package-private */
        public final void a(Resources resources) {
            if (resources != null) {
                this.b = resources;
                int a2 = b.a(resources, this.c);
                int i2 = this.c;
                this.c = a2;
                if (i2 != a2) {
                    this.m = false;
                    this.f92j = false;
                }
            }
        }

        public final void b(int i2) {
            this.A = i2;
        }

        /* access modifiers changed from: package-private */
        public final void a(Resources.Theme theme) {
            if (theme != null) {
                n();
                int i2 = this.f90h;
                Drawable[] drawableArr = this.f89g;
                for (int i3 = 0; i3 < i2; i3++) {
                    if (drawableArr[i3] != null && drawableArr[i3].canApplyTheme()) {
                        drawableArr[i3].applyTheme(theme);
                        this.e |= drawableArr[i3].getChangingConfigurations();
                    }
                }
                a(theme.getResources());
            }
        }

        public final void a(boolean z2) {
            this.l = z2;
        }

        public void a(int i2, int i3) {
            Drawable[] drawableArr = new Drawable[i3];
            System.arraycopy(this.f89g, 0, drawableArr, 0, i2);
            this.f89g = drawableArr;
        }

        public synchronized boolean a() {
            if (this.v) {
                return this.w;
            }
            n();
            this.v = true;
            int i2 = this.f90h;
            Drawable[] drawableArr = this.f89g;
            for (int i3 = 0; i3 < i2; i3++) {
                if (drawableArr[i3].getConstantState() == null) {
                    this.w = false;
                    return false;
                }
            }
            this.w = true;
            return true;
        }
    }

    b() {
    }

    private boolean c() {
        if (!isAutoMirrored() || androidx.core.graphics.drawable.a.e(this) != 1) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public c a() {
        throw null;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(int r10) {
        /*
            r9 = this;
            int r0 = r9.k
            r1 = 0
            if (r10 != r0) goto L_0x0006
            return r1
        L_0x0006:
            long r2 = android.os.SystemClock.uptimeMillis()
            androidx.appcompat.b.a.b$c r0 = r9.e
            int r0 = r0.B
            r4 = 0
            r5 = 0
            if (r0 <= 0) goto L_0x002e
            android.graphics.drawable.Drawable r0 = r9.f85h
            if (r0 == 0) goto L_0x001a
            r0.setVisible(r1, r1)
        L_0x001a:
            android.graphics.drawable.Drawable r0 = r9.f84g
            if (r0 == 0) goto L_0x0029
            r9.f85h = r0
            androidx.appcompat.b.a.b$c r0 = r9.e
            int r0 = r0.B
            long r0 = (long) r0
            long r0 = r0 + r2
            r9.o = r0
            goto L_0x0035
        L_0x0029:
            r9.f85h = r4
            r9.o = r5
            goto L_0x0035
        L_0x002e:
            android.graphics.drawable.Drawable r0 = r9.f84g
            if (r0 == 0) goto L_0x0035
            r0.setVisible(r1, r1)
        L_0x0035:
            if (r10 < 0) goto L_0x0055
            androidx.appcompat.b.a.b$c r0 = r9.e
            int r1 = r0.f90h
            if (r10 >= r1) goto L_0x0055
            android.graphics.drawable.Drawable r0 = r0.a((int) r10)
            r9.f84g = r0
            r9.k = r10
            if (r0 == 0) goto L_0x005a
            androidx.appcompat.b.a.b$c r10 = r9.e
            int r10 = r10.A
            if (r10 <= 0) goto L_0x0051
            long r7 = (long) r10
            long r2 = r2 + r7
            r9.n = r2
        L_0x0051:
            r9.a((android.graphics.drawable.Drawable) r0)
            goto L_0x005a
        L_0x0055:
            r9.f84g = r4
            r10 = -1
            r9.k = r10
        L_0x005a:
            long r0 = r9.n
            r10 = 1
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 != 0) goto L_0x0067
            long r0 = r9.o
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x0079
        L_0x0067:
            java.lang.Runnable r0 = r9.m
            if (r0 != 0) goto L_0x0073
            androidx.appcompat.b.a.b$a r0 = new androidx.appcompat.b.a.b$a
            r0.<init>()
            r9.m = r0
            goto L_0x0076
        L_0x0073:
            r9.unscheduleSelf(r0)
        L_0x0076:
            r9.a((boolean) r10)
        L_0x0079:
            r9.invalidateSelf()
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.b.a.b.a(int):boolean");
    }

    public void applyTheme(Resources.Theme theme) {
        this.e.a(theme);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.k;
    }

    public boolean canApplyTheme() {
        return this.e.canApplyTheme();
    }

    public void draw(Canvas canvas) {
        Drawable drawable = this.f84g;
        if (drawable != null) {
            drawable.draw(canvas);
        }
        Drawable drawable2 = this.f85h;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    public int getAlpha() {
        return this.f86i;
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.e.getChangingConfigurations();
    }

    public final Drawable.ConstantState getConstantState() {
        if (!this.e.a()) {
            return null;
        }
        this.e.d = getChangingConfigurations();
        return this.e;
    }

    public Drawable getCurrent() {
        return this.f84g;
    }

    public void getHotspotBounds(Rect rect) {
        Rect rect2 = this.f83f;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    public int getIntrinsicHeight() {
        if (this.e.l()) {
            return this.e.e();
        }
        Drawable drawable = this.f84g;
        if (drawable != null) {
            return drawable.getIntrinsicHeight();
        }
        return -1;
    }

    public int getIntrinsicWidth() {
        if (this.e.l()) {
            return this.e.i();
        }
        Drawable drawable = this.f84g;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return -1;
    }

    public int getMinimumHeight() {
        if (this.e.l()) {
            return this.e.f();
        }
        Drawable drawable = this.f84g;
        if (drawable != null) {
            return drawable.getMinimumHeight();
        }
        return 0;
    }

    public int getMinimumWidth() {
        if (this.e.l()) {
            return this.e.g();
        }
        Drawable drawable = this.f84g;
        if (drawable != null) {
            return drawable.getMinimumWidth();
        }
        return 0;
    }

    public int getOpacity() {
        Drawable drawable = this.f84g;
        if (drawable == null || !drawable.isVisible()) {
            return -2;
        }
        return this.e.j();
    }

    public void getOutline(Outline outline) {
        Drawable drawable = this.f84g;
        if (drawable != null) {
            drawable.getOutline(outline);
        }
    }

    public boolean getPadding(Rect rect) {
        boolean z;
        Rect h2 = this.e.h();
        if (h2 != null) {
            rect.set(h2);
            z = (h2.right | ((h2.left | h2.top) | h2.bottom)) != 0;
        } else {
            Drawable drawable = this.f84g;
            if (drawable != null) {
                z = drawable.getPadding(rect);
            } else {
                z = super.getPadding(rect);
            }
        }
        if (c()) {
            int i2 = rect.left;
            rect.left = rect.right;
            rect.right = i2;
        }
        return z;
    }

    public void invalidateDrawable(Drawable drawable) {
        c cVar = this.e;
        if (cVar != null) {
            cVar.k();
        }
        if (drawable == this.f84g && getCallback() != null) {
            getCallback().invalidateDrawable(this);
        }
    }

    public boolean isAutoMirrored() {
        return this.e.C;
    }

    public void jumpToCurrentState() {
        boolean z;
        Drawable drawable = this.f85h;
        boolean z2 = true;
        if (drawable != null) {
            drawable.jumpToCurrentState();
            this.f85h = null;
            z = true;
        } else {
            z = false;
        }
        Drawable drawable2 = this.f84g;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            if (this.f87j) {
                this.f84g.setAlpha(this.f86i);
            }
        }
        if (this.o != 0) {
            this.o = 0;
            z = true;
        }
        if (this.n != 0) {
            this.n = 0;
        } else {
            z2 = z;
        }
        if (z2) {
            invalidateSelf();
        }
    }

    public Drawable mutate() {
        if (!this.l && super.mutate() == this) {
            c a2 = a();
            a2.m();
            a(a2);
            this.l = true;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        Drawable drawable = this.f85h;
        if (drawable != null) {
            drawable.setBounds(rect);
        }
        Drawable drawable2 = this.f84g;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
    }

    public boolean onLayoutDirectionChanged(int i2) {
        return this.e.b(i2, b());
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i2) {
        Drawable drawable = this.f85h;
        if (drawable != null) {
            return drawable.setLevel(i2);
        }
        Drawable drawable2 = this.f84g;
        if (drawable2 != null) {
            return drawable2.setLevel(i2);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        Drawable drawable = this.f85h;
        if (drawable != null) {
            return drawable.setState(iArr);
        }
        Drawable drawable2 = this.f84g;
        if (drawable2 != null) {
            return drawable2.setState(iArr);
        }
        return false;
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
        if (drawable == this.f84g && getCallback() != null) {
            getCallback().scheduleDrawable(this, runnable, j2);
        }
    }

    public void setAlpha(int i2) {
        if (!this.f87j || this.f86i != i2) {
            this.f87j = true;
            this.f86i = i2;
            Drawable drawable = this.f84g;
            if (drawable == null) {
                return;
            }
            if (this.n == 0) {
                drawable.setAlpha(i2);
            } else {
                a(false);
            }
        }
    }

    public void setAutoMirrored(boolean z) {
        c cVar = this.e;
        if (cVar.C != z) {
            cVar.C = z;
            Drawable drawable = this.f84g;
            if (drawable != null) {
                androidx.core.graphics.drawable.a.a(drawable, z);
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        c cVar = this.e;
        cVar.E = true;
        if (cVar.D != colorFilter) {
            cVar.D = colorFilter;
            Drawable drawable = this.f84g;
            if (drawable != null) {
                drawable.setColorFilter(colorFilter);
            }
        }
    }

    public void setDither(boolean z) {
        c cVar = this.e;
        if (cVar.x != z) {
            cVar.x = z;
            Drawable drawable = this.f84g;
            if (drawable != null) {
                drawable.setDither(z);
            }
        }
    }

    public void setHotspot(float f2, float f3) {
        Drawable drawable = this.f84g;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, f2, f3);
        }
    }

    public void setHotspotBounds(int i2, int i3, int i4, int i5) {
        Rect rect = this.f83f;
        if (rect == null) {
            this.f83f = new Rect(i2, i3, i4, i5);
        } else {
            rect.set(i2, i3, i4, i5);
        }
        Drawable drawable = this.f84g;
        if (drawable != null) {
            androidx.core.graphics.drawable.a.a(drawable, i2, i3, i4, i5);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        c cVar = this.e;
        cVar.H = true;
        if (cVar.F != colorStateList) {
            cVar.F = colorStateList;
            androidx.core.graphics.drawable.a.a(this.f84g, colorStateList);
        }
    }

    public void setTintMode(PorterDuff.Mode mode) {
        c cVar = this.e;
        cVar.I = true;
        if (cVar.G != mode) {
            cVar.G = mode;
            androidx.core.graphics.drawable.a.a(this.f84g, mode);
        }
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        Drawable drawable = this.f85h;
        if (drawable != null) {
            drawable.setVisible(z, z2);
        }
        Drawable drawable2 = this.f84g;
        if (drawable2 != null) {
            drawable2.setVisible(z, z2);
        }
        return visible;
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (drawable == this.f84g && getCallback() != null) {
            getCallback().unscheduleDrawable(this, runnable);
        }
    }

    private void a(Drawable drawable) {
        if (this.p == null) {
            this.p = new C0004b();
        }
        C0004b bVar = this.p;
        bVar.a(drawable.getCallback());
        drawable.setCallback(bVar);
        try {
            if (this.e.A <= 0 && this.f87j) {
                drawable.setAlpha(this.f86i);
            }
            if (this.e.E) {
                drawable.setColorFilter(this.e.D);
            } else {
                if (this.e.H) {
                    androidx.core.graphics.drawable.a.a(drawable, this.e.F);
                }
                if (this.e.I) {
                    androidx.core.graphics.drawable.a.a(drawable, this.e.G);
                }
            }
            drawable.setVisible(isVisible(), true);
            drawable.setDither(this.e.x);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            if (Build.VERSION.SDK_INT >= 23) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
            if (Build.VERSION.SDK_INT >= 19) {
                drawable.setAutoMirrored(this.e.C);
            }
            Rect rect = this.f83f;
            if (Build.VERSION.SDK_INT >= 21 && rect != null) {
                drawable.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom);
            }
        } finally {
            drawable.setCallback(this.p.a());
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(boolean r14) {
        /*
            r13 = this;
            r0 = 1
            r13.f87j = r0
            long r1 = android.os.SystemClock.uptimeMillis()
            android.graphics.drawable.Drawable r3 = r13.f84g
            r4 = 255(0xff, double:1.26E-321)
            r6 = 0
            r7 = 0
            if (r3 == 0) goto L_0x0038
            long r9 = r13.n
            int r11 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r11 == 0) goto L_0x003a
            int r11 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r11 > 0) goto L_0x0022
            int r9 = r13.f86i
            r3.setAlpha(r9)
            r13.n = r7
            goto L_0x003a
        L_0x0022:
            long r9 = r9 - r1
            long r9 = r9 * r4
            int r10 = (int) r9
            androidx.appcompat.b.a.b$c r9 = r13.e
            int r9 = r9.A
            int r10 = r10 / r9
            int r9 = 255 - r10
            int r10 = r13.f86i
            int r9 = r9 * r10
            int r9 = r9 / 255
            r3.setAlpha(r9)
            r3 = 1
            goto L_0x003b
        L_0x0038:
            r13.n = r7
        L_0x003a:
            r3 = 0
        L_0x003b:
            android.graphics.drawable.Drawable r9 = r13.f85h
            if (r9 == 0) goto L_0x0065
            long r10 = r13.o
            int r12 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r12 == 0) goto L_0x0067
            int r12 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r12 > 0) goto L_0x0052
            r9.setVisible(r6, r6)
            r0 = 0
            r13.f85h = r0
            r13.o = r7
            goto L_0x0067
        L_0x0052:
            long r10 = r10 - r1
            long r10 = r10 * r4
            int r3 = (int) r10
            androidx.appcompat.b.a.b$c r4 = r13.e
            int r4 = r4.B
            int r3 = r3 / r4
            int r4 = r13.f86i
            int r3 = r3 * r4
            int r3 = r3 / 255
            r9.setAlpha(r3)
            goto L_0x0068
        L_0x0065:
            r13.o = r7
        L_0x0067:
            r0 = r3
        L_0x0068:
            if (r14 == 0) goto L_0x0074
            if (r0 == 0) goto L_0x0074
            java.lang.Runnable r14 = r13.m
            r3 = 16
            long r1 = r1 + r3
            r13.scheduleSelf(r14, r1)
        L_0x0074:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.b.a.b.a(boolean):void");
    }

    /* access modifiers changed from: package-private */
    public final void a(Resources resources) {
        this.e.a(resources);
    }

    /* access modifiers changed from: package-private */
    public void a(c cVar) {
        this.e = cVar;
        int i2 = this.k;
        if (i2 >= 0) {
            Drawable a2 = cVar.a(i2);
            this.f84g = a2;
            if (a2 != null) {
                a(a2);
            }
        }
        this.f85h = null;
    }

    static int a(Resources resources, int i2) {
        if (resources != null) {
            i2 = resources.getDisplayMetrics().densityDpi;
        }
        if (i2 == 0) {
            return 160;
        }
        return i2;
    }
}
