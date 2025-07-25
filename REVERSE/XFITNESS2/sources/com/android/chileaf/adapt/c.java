package com.android.chileaf.adapt;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.chileaf.adapt.external.ExternalAdaptInfo;
import com.android.chileaf.adapt.h.b;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: AutoSize */
public final class c {
    private static Map<String, DisplayMetricsInfo> a = new ConcurrentHashMap();

    /* compiled from: AutoSize */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.android.chileaf.adapt.unit.Subunits[] r0 = com.android.chileaf.adapt.unit.Subunits.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.android.chileaf.adapt.unit.Subunits r1 = com.android.chileaf.adapt.unit.Subunits.PT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.android.chileaf.adapt.unit.Subunits r1 = com.android.chileaf.adapt.unit.Subunits.MM     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.android.chileaf.adapt.unit.Subunits r1 = com.android.chileaf.adapt.unit.Subunits.NONE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.android.chileaf.adapt.unit.Subunits r1 = com.android.chileaf.adapt.unit.Subunits.IN     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.adapt.c.a.<clinit>():void");
        }
    }

    public static void a(Activity activity) {
        if (d.u().p()) {
            b(activity, (float) d.u().c());
        } else {
            a(activity, (float) d.u().b());
        }
    }

    public static void b(Activity activity, float f2) {
        a(activity, f2, true);
    }

    public static void b(Activity activity) {
        float f2;
        float j2 = d.u().j();
        int i2 = a.a[d.u().o().c().ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                f2 = 25.4f;
            }
            a(activity, d.u().e(), d.u().f(), d.u().g(), j2);
            a(activity, d.u().i(), d.u().h());
        }
        f2 = 72.0f;
        j2 /= f2;
        a(activity, d.u().e(), d.u().f(), d.u().g(), j2);
        a(activity, d.u().i(), d.u().h());
    }

    public static void a(Activity activity, b bVar) {
        int i2;
        com.android.chileaf.adapt.j.b.a(bVar, (Object) "customAdapt == null");
        float a2 = bVar.a();
        if (a2 <= 0.0f) {
            if (bVar.b()) {
                i2 = d.u().c();
            } else {
                i2 = d.u().b();
            }
            a2 = (float) i2;
        }
        a(activity, a2, bVar.b());
    }

    public static void a(Activity activity, ExternalAdaptInfo externalAdaptInfo) {
        int i2;
        com.android.chileaf.adapt.j.b.a(externalAdaptInfo, (Object) "externalAdaptInfo == null");
        float a2 = externalAdaptInfo.a();
        if (a2 <= 0.0f) {
            if (externalAdaptInfo.b()) {
                i2 = d.u().c();
            } else {
                i2 = d.u().b();
            }
            a2 = (float) i2;
        }
        a(activity, a2, externalAdaptInfo.b());
    }

    public static void a(Activity activity, float f2) {
        a(activity, f2, false);
    }

    public static void a(Activity activity, float f2, boolean z) {
        float f3;
        int i2;
        int i3;
        float f4;
        int i4;
        int i5;
        float f5;
        float f6;
        int i6;
        float f7;
        int i7;
        Activity activity2 = activity;
        float f8 = f2;
        boolean z2 = z;
        com.android.chileaf.adapt.j.b.a(activity2, (Object) "activity == null");
        if (z2) {
            f3 = d.u().o().b();
        } else {
            f3 = d.u().o().a();
        }
        if (f3 <= 0.0f) {
            f3 = f8;
        }
        if (z2) {
            i2 = d.u().m();
        } else {
            i2 = d.u().l();
        }
        String str = f8 + "|" + f3 + "|" + z2 + "|" + d.u().t() + "|" + d.u().g() + "|" + i2;
        DisplayMetricsInfo displayMetricsInfo = a.get(str);
        if (displayMetricsInfo == null) {
            if (z2) {
                i6 = d.u().m();
            } else {
                i6 = d.u().l();
            }
            float f9 = (((float) i6) * 1.0f) / f8;
            if (d.u().r()) {
                f7 = 1.0f;
            } else {
                f7 = (d.u().g() * 1.0f) / d.u().e();
            }
            f4 = f9 * f7;
            i3 = (int) (160.0f * f9);
            int m = (int) (((float) d.u().m()) / f9);
            int l = (int) (((float) d.u().l()) / f9);
            if (z2) {
                i7 = d.u().m();
            } else {
                i7 = d.u().l();
            }
            f6 = (((float) i7) * 1.0f) / f3;
            DisplayMetricsInfo displayMetricsInfo2 = r7;
            float f10 = f9;
            Map<String, DisplayMetricsInfo> map = a;
            int i8 = l;
            DisplayMetricsInfo displayMetricsInfo3 = new DisplayMetricsInfo(f9, i3, f4, f6, m, i8);
            map.put(str, displayMetricsInfo2);
            f5 = f10;
            i5 = i8;
            i4 = m;
        } else {
            f5 = displayMetricsInfo.a();
            i3 = displayMetricsInfo.b();
            f4 = displayMetricsInfo.c();
            float f11 = displayMetricsInfo.f();
            i4 = displayMetricsInfo.e();
            i5 = displayMetricsInfo.d();
            f6 = f11;
        }
        a(activity2, f5, i3, f4, f6);
        a(activity2, i4, i5);
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[13];
        objArr[0] = activity.getClass().getName();
        objArr[1] = activity.getClass().getSimpleName();
        objArr[2] = Boolean.valueOf(z);
        objArr[3] = z2 ? "designWidthInDp" : "designHeightInDp";
        objArr[4] = Float.valueOf(f2);
        objArr[5] = z2 ? "designWidthInSubunits" : "designHeightInSubunits";
        objArr[6] = Float.valueOf(f3);
        objArr[7] = Float.valueOf(f5);
        objArr[8] = Float.valueOf(f4);
        objArr[9] = Integer.valueOf(i3);
        objArr[10] = Float.valueOf(f6);
        objArr[11] = Integer.valueOf(i4);
        objArr[12] = Integer.valueOf(i5);
        Log.d(BuildConfig.FLAVOR, String.format(locale, "The %s has been adapted! \n%s Info: isBaseOnWidth = %s, %s = %f, %s = %f, targetDensity = %f, targetScaledDensity = %f, targetDensityDpi = %d, targetXdpi = %f, targetScreenWidthDp = %d, targetScreenHeightDp = %d", objArr));
    }

    private static void a(Activity activity, float f2, int i2, float f3, float f4) {
        DisplayMetrics a2 = a(activity.getResources());
        DisplayMetrics a3 = a(d.u().a().getResources());
        if (a2 != null) {
            a(a2, f2, i2, f3, f4);
        } else {
            a(activity.getResources().getDisplayMetrics(), f2, i2, f3, f4);
        }
        if (a3 != null) {
            a(a3, f2, i2, f3, f4);
        } else {
            a(d.u().a().getResources().getDisplayMetrics(), f2, i2, f3, f4);
        }
    }

    private static void a(DisplayMetrics displayMetrics, float f2, int i2, float f3, float f4) {
        if (d.u().o().d()) {
            displayMetrics.density = f2;
            displayMetrics.densityDpi = i2;
        }
        if (d.u().o().e()) {
            displayMetrics.scaledDensity = f3;
        }
        int i3 = a.a[d.u().o().c().ordinal()];
        if (i3 == 1) {
            displayMetrics.xdpi = f4 * 72.0f;
        } else if (i3 == 2) {
            displayMetrics.xdpi = f4 * 25.4f;
        } else if (i3 == 4) {
            displayMetrics.xdpi = f4;
        }
    }

    private static void a(Activity activity, int i2, int i3) {
        if (d.u().o().d() && d.u().o().f()) {
            a(activity.getResources().getConfiguration(), i2, i3);
            a(d.u().a().getResources().getConfiguration(), i2, i3);
        }
    }

    private static void a(Configuration configuration, int i2, int i3) {
        configuration.screenWidthDp = i2;
        configuration.screenHeightDp = i3;
    }

    private static DisplayMetrics a(Resources resources) {
        if (d.u().s() && d.u().n() != null) {
            try {
                return (DisplayMetrics) d.u().n().get(resources);
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
