package kotlin.jvm.internal;

import kotlin.c;
import kotlin.jvm.b.a;
import kotlin.jvm.b.b;
import kotlin.jvm.b.d;
import kotlin.jvm.b.e;
import kotlin.jvm.b.f;
import kotlin.jvm.b.g;
import kotlin.jvm.b.h;
import kotlin.jvm.b.i;
import kotlin.jvm.b.j;
import kotlin.jvm.b.k;
import kotlin.jvm.b.l;
import kotlin.jvm.b.m;
import kotlin.jvm.b.o;
import kotlin.jvm.b.p;
import kotlin.jvm.b.q;
import kotlin.jvm.b.r;
import kotlin.jvm.b.s;
import kotlin.jvm.b.t;
import kotlin.jvm.b.u;
import kotlin.jvm.b.v;
import kotlin.jvm.b.w;

/* compiled from: TypeIntrinsics */
public class n {
    private static <T extends Throwable> T a(T t) {
        i.a(t, n.class.getName());
        return t;
    }

    public static boolean b(Object obj, int i2) {
        return (obj instanceof c) && a(obj) == i2;
    }

    public static void a(Object obj, String str) {
        String name = obj == null ? "null" : obj.getClass().getName();
        a(name + " cannot be cast to " + str);
        throw null;
    }

    public static void a(String str) {
        a(new ClassCastException(str));
        throw null;
    }

    public static ClassCastException a(ClassCastException classCastException) {
        a(classCastException);
        throw classCastException;
    }

    public static int a(Object obj) {
        if (obj instanceof h) {
            return ((h) obj).getArity();
        }
        if (obj instanceof a) {
            return 0;
        }
        if (obj instanceof l) {
            return 1;
        }
        if (obj instanceof p) {
            return 2;
        }
        if (obj instanceof q) {
            return 3;
        }
        if (obj instanceof r) {
            return 4;
        }
        if (obj instanceof s) {
            return 5;
        }
        if (obj instanceof t) {
            return 6;
        }
        if (obj instanceof u) {
            return 7;
        }
        if (obj instanceof v) {
            return 8;
        }
        if (obj instanceof w) {
            return 9;
        }
        if (obj instanceof b) {
            return 10;
        }
        if (obj instanceof kotlin.jvm.b.c) {
            return 11;
        }
        if (obj instanceof d) {
            return 12;
        }
        if (obj instanceof e) {
            return 13;
        }
        if (obj instanceof f) {
            return 14;
        }
        if (obj instanceof g) {
            return 15;
        }
        if (obj instanceof h) {
            return 16;
        }
        if (obj instanceof i) {
            return 17;
        }
        if (obj instanceof j) {
            return 18;
        }
        if (obj instanceof k) {
            return 19;
        }
        if (obj instanceof m) {
            return 20;
        }
        if (obj instanceof kotlin.jvm.b.n) {
            return 21;
        }
        return obj instanceof o ? 22 : -1;
    }

    public static Object a(Object obj, int i2) {
        if (obj == null || b(obj, i2)) {
            return obj;
        }
        a(obj, "kotlin.jvm.functions.Function" + i2);
        throw null;
    }
}
