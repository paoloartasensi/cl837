package com.chileaf.fitness.config;

import com.chileaf.fitness.device.BoxingActivity;
import com.chileaf.fitness.device.WeightActivity;
import com.chileaf.fitness.device.cdn.CDNActivity;
import com.chileaf.fitness.device.wear.cl800.CL800Activity;
import com.chileaf.fitness.device.wear.cl820.CL820Activity;
import com.chileaf.fitness.device.wear.cl830.CL830Activity;
import com.chileaf.fitness.device.wear.cl831.CL831Activity;
import com.chileaf.fitness.device.wear.cl880.CL880Activity;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.j;
import kotlin.jvm.internal.i;

/* compiled from: ProductManager.kt */
public final class c {
    private static final String[] a;
    private static final String[] b = {"CL820"};
    private static final String[] c = {"CL830"};
    private static final String[] d = {"CL831", "HW702C"};
    private static final String[] e = {"CL880", "FitWay", "RTFPS", "RTFP"};

    /* renamed from: f  reason: collision with root package name */
    private static final String[] f1145f = {"CD", "CDN", "deskbike"};

    /* renamed from: g  reason: collision with root package name */
    private static final String[] f1146g = {"SWAN"};

    /* renamed from: h  reason: collision with root package name */
    private static final String[] f1147h = {"Sampling"};

    /* renamed from: i  reason: collision with root package name */
    private static final String[] f1148i = {"CL830", "CL831", "HW702C"};

    /* renamed from: j  reason: collision with root package name */
    private static final Map<String, String[]> f1149j;
    public static final c k = new c();

    static {
        String[] strArr = {"CL800", "CL813"};
        a = strArr;
        f1149j = x.a((Pair<? extends K, ? extends V>[]) new Pair[]{j.a("CL800", strArr), j.a("CL813", a), j.a("CL820W", b), j.a("CL830", f1148i), j.a("CL880", e), j.a("CDN210", f1145f), j.a("CDN220", f1145f), j.a("SWAN", f1146g), j.a("Sampling", f1147h)});
    }

    private c() {
    }

    public static final List<String> a(String str) {
        i.b(str, "name");
        String[] strArr = f1149j.get(str);
        if (strArr != null) {
            return f.d(strArr);
        }
        return null;
    }

    public static final Class<?> b(String str) {
        i.b(str, "name");
        if (k.a(str, a)) {
            return CL800Activity.class;
        }
        if (k.a(str, b)) {
            return CL820Activity.class;
        }
        if (k.a(str, c)) {
            return CL830Activity.class;
        }
        if (k.a(str, d)) {
            return CL831Activity.class;
        }
        if (k.a(str, e)) {
            return CL880Activity.class;
        }
        if (k.a(str, f1145f)) {
            return CDNActivity.class;
        }
        if (k.a(str, f1146g)) {
            return WeightActivity.class;
        }
        if (k.a(str, f1147h)) {
            return BoxingActivity.class;
        }
        return null;
    }

    private final boolean a(String str, String[] strArr) {
        for (String a2 : strArr) {
            if (l.a(str, a2, true)) {
                return true;
            }
        }
        return false;
    }
}
