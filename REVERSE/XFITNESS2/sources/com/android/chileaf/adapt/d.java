package com.android.chileaf.adapt;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.android.chileaf.adapt.j.c;
import j.a.a;
import java.lang.reflect.Field;

/* compiled from: AutoSizeConfig */
public final class d {
    private static volatile d x;
    private Application a;
    private com.android.chileaf.adapt.external.a b = new com.android.chileaf.adapt.external.a();
    private com.android.chileaf.adapt.unit.a c = new com.android.chileaf.adapt.unit.a();
    private float d = -1.0f;
    private int e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public float f1012f;

    /* renamed from: g  reason: collision with root package name */
    private float f1013g;

    /* renamed from: h  reason: collision with root package name */
    private int f1014h;

    /* renamed from: i  reason: collision with root package name */
    private int f1015i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public int f1016j;
    /* access modifiers changed from: private */
    public int k;
    /* access modifiers changed from: private */
    public int l;
    /* access modifiers changed from: private */
    public int m;
    private int n;
    private boolean o = true;
    private boolean p = true;
    private a q;
    private boolean r;
    /* access modifiers changed from: private */
    public boolean s;
    private boolean t;
    private boolean u;
    private Field v;
    private i w;

    /* compiled from: AutoSizeConfig */
    class a implements ComponentCallbacks {
        final /* synthetic */ Application e;

        a(Application application) {
            this.e = application;
        }

        public void onConfigurationChanged(Configuration configuration) {
            if (configuration != null) {
                if (configuration.fontScale > 0.0f) {
                    float unused = d.this.f1012f = Resources.getSystem().getDisplayMetrics().scaledDensity;
                    j.a.a.a("initScaledDensity = " + d.this.f1012f + " on ConfigurationChanged", new Object[0]);
                }
                boolean unused2 = d.this.s = configuration.orientation == 1;
                int[] a = c.a(this.e);
                int unused3 = d.this.l = a[0];
                int unused4 = d.this.m = a[1];
            }
        }

        public void onLowMemory() {
        }
    }

    /* compiled from: AutoSizeConfig */
    class b implements Runnable {
        final /* synthetic */ Context e;

        b(Context context) {
            this.e = context;
        }

        public void run() {
            try {
                ApplicationInfo applicationInfo = this.e.getPackageManager().getApplicationInfo(this.e.getPackageName(), 128);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    if (applicationInfo.metaData.containsKey("design_width_in_dp")) {
                        int unused = d.this.f1016j = ((Integer) applicationInfo.metaData.get("design_width_in_dp")).intValue();
                    }
                    if (applicationInfo.metaData.containsKey("design_height_in_dp")) {
                        int unused2 = d.this.k = ((Integer) applicationInfo.metaData.get("design_height_in_dp")).intValue();
                    }
                }
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
            }
        }
    }

    private d() {
    }

    public static d u() {
        if (x == null) {
            synchronized (d.class) {
                if (x == null) {
                    x = new d();
                }
            }
        }
        return x;
    }

    public float e() {
        return this.d;
    }

    public int f() {
        return this.e;
    }

    public float g() {
        return this.f1012f;
    }

    public int h() {
        return this.f1015i;
    }

    public int i() {
        return this.f1014h;
    }

    public float j() {
        return this.f1013g;
    }

    public i k() {
        return this.w;
    }

    public int l() {
        return t() ? this.m : this.m - this.n;
    }

    public int m() {
        return this.l;
    }

    public Field n() {
        return this.v;
    }

    public com.android.chileaf.adapt.unit.a o() {
        return this.c;
    }

    public boolean p() {
        return this.o;
    }

    public boolean q() {
        return this.r;
    }

    public boolean r() {
        return this.t;
    }

    public boolean s() {
        return this.u;
    }

    public boolean t() {
        return this.p;
    }

    public d b(boolean z) {
        this.p = z;
        return this;
    }

    public int c() {
        com.android.chileaf.adapt.j.b.a(this.f1016j > 0, (Object) "you must set design_width_in_dp  in your AndroidManifest file");
        return this.f1016j;
    }

    public com.android.chileaf.adapt.external.a d() {
        return this.b;
    }

    public int b() {
        com.android.chileaf.adapt.j.b.a(this.k > 0, (Object) "you must set design_height_in_dp  in your AndroidManifest file");
        return this.k;
    }

    public Application a() {
        com.android.chileaf.adapt.j.b.a(this.a, (Object) "Please call the AutoSizeConfig#init() first");
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public d a(Application application) {
        a(application, true, (b) null);
        return this;
    }

    /* access modifiers changed from: package-private */
    public d a(Application application, boolean z, b bVar) {
        com.android.chileaf.adapt.j.b.a(this.d == -1.0f, (Object) "AutoSizeConfig#init() can only be called once");
        com.android.chileaf.adapt.j.b.a(application, (Object) "application == null");
        this.a = application;
        this.o = z;
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        Configuration configuration = Resources.getSystem().getConfiguration();
        a((Context) application);
        int i2 = application.getResources().getConfiguration().orientation;
        int[] a2 = c.a(application);
        this.l = a2[0];
        this.m = a2[1];
        this.n = c.a();
        j.a.a.a("designWidthInDp = " + this.f1016j + ", designHeightInDp = " + this.k + ", screenWidth = " + this.l + ", screenHeight = " + this.m, new Object[0]);
        this.d = displayMetrics.density;
        this.e = displayMetrics.densityDpi;
        this.f1012f = displayMetrics.scaledDensity;
        this.f1013g = displayMetrics.xdpi;
        this.f1014h = configuration.screenWidthDp;
        this.f1015i = configuration.screenHeightDp;
        application.registerComponentCallbacks(new a(application));
        j.a.a.a("initDensity = " + this.d + ", initScaledDensity = " + this.f1012f, new Object[0]);
        if (bVar == null) {
            bVar = new e();
        }
        a aVar = new a(new g(bVar));
        this.q = aVar;
        application.registerActivityLifecycleCallbacks(aVar);
        if ("MiuiResources".equals(application.getResources().getClass().getSimpleName()) || "XResources".equals(application.getResources().getClass().getSimpleName())) {
            this.u = true;
            try {
                Field declaredField = Resources.class.getDeclaredField("mTmpMetrics");
                this.v = declaredField;
                declaredField.setAccessible(true);
            } catch (Exception unused) {
                this.v = null;
            }
        }
        return this;
    }

    public d a(boolean z) {
        if (z) {
            j.a.a.a((a.c) new a.b());
        }
        return this;
    }

    private void a(Context context) {
        new Thread(new b(context)).start();
    }
}
