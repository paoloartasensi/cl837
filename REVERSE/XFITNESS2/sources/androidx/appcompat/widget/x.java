package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.appcompat.resources.R$drawable;
import androidx.vectordrawable.a.a.i;
import g.a.h;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: ResourceManagerInternal */
public final class x {

    /* renamed from: h  reason: collision with root package name */
    private static final PorterDuff.Mode f332h = PorterDuff.Mode.SRC_IN;

    /* renamed from: i  reason: collision with root package name */
    private static x f333i;

    /* renamed from: j  reason: collision with root package name */
    private static final c f334j = new c(6);
    private WeakHashMap<Context, h<ColorStateList>> a;
    private g.a.a<String, d> b;
    private h<String> c;
    private final WeakHashMap<Context, g.a.d<WeakReference<Drawable.ConstantState>>> d = new WeakHashMap<>(0);
    private TypedValue e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f335f;

    /* renamed from: g  reason: collision with root package name */
    private e f336g;

    /* compiled from: ResourceManagerInternal */
    static class a implements d {
        a() {
        }

        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return androidx.appcompat.b.a.a.b(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AsldcInflateDelegate", "Exception while inflating <animated-selector>", e);
                return null;
            }
        }
    }

    /* compiled from: ResourceManagerInternal */
    private static class b implements d {
        b() {
        }

        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return androidx.vectordrawable.a.a.c.a(context, context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", e);
                return null;
            }
        }
    }

    /* compiled from: ResourceManagerInternal */
    private static class c extends g.a.e<Integer, PorterDuffColorFilter> {
        public c(int i2) {
            super(i2);
        }

        private static int b(int i2, PorterDuff.Mode mode) {
            return ((i2 + 31) * 31) + mode.hashCode();
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter a(int i2, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter) b(Integer.valueOf(b(i2, mode)));
        }

        /* access modifiers changed from: package-private */
        public PorterDuffColorFilter a(int i2, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return (PorterDuffColorFilter) a(Integer.valueOf(b(i2, mode)), porterDuffColorFilter);
        }
    }

    /* compiled from: ResourceManagerInternal */
    private interface d {
        Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme);
    }

    /* compiled from: ResourceManagerInternal */
    interface e {
        ColorStateList a(Context context, int i2);

        PorterDuff.Mode a(int i2);

        Drawable a(x xVar, Context context, int i2);

        boolean a(Context context, int i2, Drawable drawable);

        boolean b(Context context, int i2, Drawable drawable);
    }

    /* compiled from: ResourceManagerInternal */
    private static class f implements d {
        f() {
        }

        public Drawable a(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                return i.createFromXmlInner(context.getResources(), xmlPullParser, attributeSet, theme);
            } catch (Exception e) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", e);
                return null;
            }
        }
    }

    public static synchronized x a() {
        x xVar;
        synchronized (x.class) {
            if (f333i == null) {
                x xVar2 = new x();
                f333i = xVar2;
                a(xVar2);
            }
            xVar = f333i;
        }
        return xVar;
    }

    private Drawable c(Context context, int i2) {
        Drawable drawable;
        if (this.e == null) {
            this.e = new TypedValue();
        }
        TypedValue typedValue = this.e;
        context.getResources().getValue(i2, typedValue, true);
        long a2 = a(typedValue);
        Drawable a3 = a(context, a2);
        if (a3 != null) {
            return a3;
        }
        e eVar = this.f336g;
        if (eVar == null) {
            drawable = null;
        } else {
            drawable = eVar.a(this, context, i2);
        }
        if (drawable != null) {
            drawable.setChangingConfigurations(typedValue.changingConfigurations);
            a(context, a2, drawable);
        }
        return drawable;
    }

    private ColorStateList d(Context context, int i2) {
        h hVar;
        WeakHashMap<Context, h<ColorStateList>> weakHashMap = this.a;
        if (weakHashMap == null || (hVar = weakHashMap.get(context)) == null) {
            return null;
        }
        return (ColorStateList) hVar.a(i2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0073 A[Catch:{ Exception -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009a A[Catch:{ Exception -> 0x00a2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable e(android.content.Context r11, int r12) {
        /*
            r10 = this;
            g.a.a<java.lang.String, androidx.appcompat.widget.x$d> r0 = r10.b
            r1 = 0
            if (r0 == 0) goto L_0x00b2
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x00b2
            g.a.h<java.lang.String> r0 = r10.c
            java.lang.String r2 = "appcompat_skip_skip"
            if (r0 == 0) goto L_0x0028
            java.lang.Object r0 = r0.a((int) r12)
            java.lang.String r0 = (java.lang.String) r0
            boolean r3 = r2.equals(r0)
            if (r3 != 0) goto L_0x0027
            if (r0 == 0) goto L_0x002f
            g.a.a<java.lang.String, androidx.appcompat.widget.x$d> r3 = r10.b
            java.lang.Object r0 = r3.get(r0)
            if (r0 != 0) goto L_0x002f
        L_0x0027:
            return r1
        L_0x0028:
            g.a.h r0 = new g.a.h
            r0.<init>()
            r10.c = r0
        L_0x002f:
            android.util.TypedValue r0 = r10.e
            if (r0 != 0) goto L_0x003a
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            r10.e = r0
        L_0x003a:
            android.util.TypedValue r0 = r10.e
            android.content.res.Resources r1 = r11.getResources()
            r3 = 1
            r1.getValue(r12, r0, r3)
            long r4 = a((android.util.TypedValue) r0)
            android.graphics.drawable.Drawable r6 = r10.a((android.content.Context) r11, (long) r4)
            if (r6 == 0) goto L_0x004f
            return r6
        L_0x004f:
            java.lang.CharSequence r7 = r0.string
            if (r7 == 0) goto L_0x00aa
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = ".xml"
            boolean r7 = r7.endsWith(r8)
            if (r7 == 0) goto L_0x00aa
            android.content.res.XmlResourceParser r1 = r1.getXml(r12)     // Catch:{ Exception -> 0x00a2 }
            android.util.AttributeSet r7 = android.util.Xml.asAttributeSet(r1)     // Catch:{ Exception -> 0x00a2 }
        L_0x0067:
            int r8 = r1.next()     // Catch:{ Exception -> 0x00a2 }
            r9 = 2
            if (r8 == r9) goto L_0x0071
            if (r8 == r3) goto L_0x0071
            goto L_0x0067
        L_0x0071:
            if (r8 != r9) goto L_0x009a
            java.lang.String r3 = r1.getName()     // Catch:{ Exception -> 0x00a2 }
            g.a.h<java.lang.String> r8 = r10.c     // Catch:{ Exception -> 0x00a2 }
            r8.a(r12, r3)     // Catch:{ Exception -> 0x00a2 }
            g.a.a<java.lang.String, androidx.appcompat.widget.x$d> r8 = r10.b     // Catch:{ Exception -> 0x00a2 }
            java.lang.Object r3 = r8.get(r3)     // Catch:{ Exception -> 0x00a2 }
            androidx.appcompat.widget.x$d r3 = (androidx.appcompat.widget.x.d) r3     // Catch:{ Exception -> 0x00a2 }
            if (r3 == 0) goto L_0x008f
            android.content.res.Resources$Theme r8 = r11.getTheme()     // Catch:{ Exception -> 0x00a2 }
            android.graphics.drawable.Drawable r1 = r3.a(r11, r1, r7, r8)     // Catch:{ Exception -> 0x00a2 }
            r6 = r1
        L_0x008f:
            if (r6 == 0) goto L_0x00aa
            int r0 = r0.changingConfigurations     // Catch:{ Exception -> 0x00a2 }
            r6.setChangingConfigurations(r0)     // Catch:{ Exception -> 0x00a2 }
            r10.a((android.content.Context) r11, (long) r4, (android.graphics.drawable.Drawable) r6)     // Catch:{ Exception -> 0x00a2 }
            goto L_0x00aa
        L_0x009a:
            org.xmlpull.v1.XmlPullParserException r11 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r0 = "No start tag found"
            r11.<init>(r0)     // Catch:{ Exception -> 0x00a2 }
            throw r11     // Catch:{ Exception -> 0x00a2 }
        L_0x00a2:
            r11 = move-exception
            java.lang.String r0 = "ResourceManagerInternal"
            java.lang.String r1 = "Exception while inflating drawable"
            android.util.Log.e(r0, r1, r11)
        L_0x00aa:
            if (r6 != 0) goto L_0x00b1
            g.a.h<java.lang.String> r11 = r10.c
            r11.a(r12, r2)
        L_0x00b1:
            return r6
        L_0x00b2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.x.e(android.content.Context, int):android.graphics.drawable.Drawable");
    }

    /* access modifiers changed from: package-private */
    public synchronized ColorStateList b(Context context, int i2) {
        ColorStateList d2;
        d2 = d(context, i2);
        if (d2 == null) {
            d2 = this.f336g == null ? null : this.f336g.a(context, i2);
            if (d2 != null) {
                a(context, i2, d2);
            }
        }
        return d2;
    }

    private static void a(x xVar) {
        if (Build.VERSION.SDK_INT < 24) {
            xVar.a("vector", (d) new f());
            xVar.a("animated-vector", (d) new b());
            xVar.a("animated-selector", (d) new a());
        }
    }

    private void b(Context context) {
        if (!this.f335f) {
            this.f335f = true;
            Drawable a2 = a(context, R$drawable.abc_vector_test);
            if (a2 == null || !a(a2)) {
                this.f335f = false;
                throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
            }
        }
    }

    public synchronized void a(e eVar) {
        this.f336g = eVar;
    }

    public synchronized Drawable a(Context context, int i2) {
        return a(context, i2, false);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable a(Context context, int i2, boolean z) {
        Drawable e2;
        b(context);
        e2 = e(context, i2);
        if (e2 == null) {
            e2 = c(context, i2);
        }
        if (e2 == null) {
            e2 = androidx.core.content.a.c(context, i2);
        }
        if (e2 != null) {
            e2 = a(context, i2, z, e2);
        }
        if (e2 != null) {
            q.b(e2);
        }
        return e2;
    }

    public synchronized void a(Context context) {
        g.a.d dVar = this.d.get(context);
        if (dVar != null) {
            dVar.b();
        }
    }

    private static long a(TypedValue typedValue) {
        return (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
    }

    private Drawable a(Context context, int i2, boolean z, Drawable drawable) {
        ColorStateList b2 = b(context, i2);
        if (b2 != null) {
            if (q.a(drawable)) {
                drawable = drawable.mutate();
            }
            Drawable i3 = androidx.core.graphics.drawable.a.i(drawable);
            androidx.core.graphics.drawable.a.a(i3, b2);
            PorterDuff.Mode a2 = a(i2);
            if (a2 == null) {
                return i3;
            }
            androidx.core.graphics.drawable.a.a(i3, a2);
            return i3;
        }
        e eVar = this.f336g;
        if ((eVar == null || !eVar.b(context, i2, drawable)) && !a(context, i2, drawable) && z) {
            return null;
        }
        return drawable;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002c, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized android.graphics.drawable.Drawable a(android.content.Context r4, long r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.WeakHashMap<android.content.Context, g.a.d<java.lang.ref.WeakReference<android.graphics.drawable.Drawable$ConstantState>>> r0 = r3.d     // Catch:{ all -> 0x002d }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x002d }
            g.a.d r0 = (g.a.d) r0     // Catch:{ all -> 0x002d }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r3)
            return r1
        L_0x000e:
            java.lang.Object r2 = r0.c((long) r5)     // Catch:{ all -> 0x002d }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x002b
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x002d }
            android.graphics.drawable.Drawable$ConstantState r2 = (android.graphics.drawable.Drawable.ConstantState) r2     // Catch:{ all -> 0x002d }
            if (r2 == 0) goto L_0x0028
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ all -> 0x002d }
            android.graphics.drawable.Drawable r4 = r2.newDrawable(r4)     // Catch:{ all -> 0x002d }
            monitor-exit(r3)
            return r4
        L_0x0028:
            r0.b((long) r5)     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r3)
            return r1
        L_0x002d:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.x.a(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    private synchronized boolean a(Context context, long j2, Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        g.a.d dVar = this.d.get(context);
        if (dVar == null) {
            dVar = new g.a.d();
            this.d.put(context, dVar);
        }
        dVar.c(j2, new WeakReference(constantState));
        return true;
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable a(Context context, l0 l0Var, int i2) {
        Drawable e2 = e(context, i2);
        if (e2 == null) {
            e2 = l0Var.a(i2);
        }
        if (e2 == null) {
            return null;
        }
        return a(context, i2, false, e2);
    }

    /* access modifiers changed from: package-private */
    public boolean a(Context context, int i2, Drawable drawable) {
        e eVar = this.f336g;
        return eVar != null && eVar.a(context, i2, drawable);
    }

    private void a(String str, d dVar) {
        if (this.b == null) {
            this.b = new g.a.a<>();
        }
        this.b.put(str, dVar);
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode a(int i2) {
        e eVar = this.f336g;
        if (eVar == null) {
            return null;
        }
        return eVar.a(i2);
    }

    private void a(Context context, int i2, ColorStateList colorStateList) {
        if (this.a == null) {
            this.a = new WeakHashMap<>();
        }
        h hVar = this.a.get(context);
        if (hVar == null) {
            hVar = new h();
            this.a.put(context, hVar);
        }
        hVar.a(i2, colorStateList);
    }

    static void a(Drawable drawable, e0 e0Var, int[] iArr) {
        if (!q.a(drawable) || drawable.mutate() == drawable) {
            if (e0Var.d || e0Var.c) {
                drawable.setColorFilter(a(e0Var.d ? e0Var.a : null, e0Var.c ? e0Var.b : f332h, iArr));
            } else {
                drawable.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT <= 23) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d("ResourceManagerInternal", "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter a(ColorStateList colorStateList, PorterDuff.Mode mode, int[] iArr) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return a(colorStateList.getColorForState(iArr, 0), mode);
    }

    public static synchronized PorterDuffColorFilter a(int i2, PorterDuff.Mode mode) {
        PorterDuffColorFilter a2;
        synchronized (x.class) {
            a2 = f334j.a(i2, mode);
            if (a2 == null) {
                a2 = new PorterDuffColorFilter(i2, mode);
                f334j.a(i2, mode, a2);
            }
        }
        return a2;
    }

    private static boolean a(Drawable drawable) {
        return (drawable instanceof i) || "android.graphics.drawable.VectorDrawable".equals(drawable.getClass().getName());
    }
}
