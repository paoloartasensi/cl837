package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.util.Property;
import android.view.View;
import androidx.core.h.v;
import java.lang.reflect.Field;

/* compiled from: ViewUtils */
class c0 {
    private static final g0 a;
    private static Field b;
    private static boolean c;
    static final Property<View, Float> d = new a(Float.class, "translationAlpha");

    /* compiled from: ViewUtils */
    static class a extends Property<View, Float> {
        a(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public Float get(View view) {
            return Float.valueOf(c0.c(view));
        }

        /* renamed from: a */
        public void set(View view, Float f2) {
            c0.a(view, f2.floatValue());
        }
    }

    /* compiled from: ViewUtils */
    static class b extends Property<View, Rect> {
        b(Class cls, String str) {
            super(cls, str);
        }

        /* renamed from: a */
        public Rect get(View view) {
            return v.i(view);
        }

        /* renamed from: a */
        public void set(View view, Rect rect) {
            v.a(view, rect);
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 22) {
            a = new f0();
        } else if (i2 >= 21) {
            a = new e0();
        } else if (i2 >= 19) {
            a = new d0();
        } else {
            a = new g0();
        }
        new b(Rect.class, "clipBounds");
    }

    static void a(View view, float f2) {
        a.a(view, f2);
    }

    static b0 b(View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new a0(view);
        }
        return z.c(view);
    }

    static float c(View view) {
        return a.b(view);
    }

    static k0 d(View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new j0(view);
        }
        return new i0(view.getWindowToken());
    }

    static void e(View view) {
        a.c(view);
    }

    static void a(View view) {
        a.a(view);
    }

    static void a(View view, int i2) {
        a();
        Field field = b;
        if (field != null) {
            try {
                b.setInt(view, i2 | (field.getInt(view) & -13));
            } catch (IllegalAccessException unused) {
            }
        }
    }

    static void b(View view, Matrix matrix) {
        a.b(view, matrix);
    }

    static void a(View view, Matrix matrix) {
        a.a(view, matrix);
    }

    static void a(View view, int i2, int i3, int i4, int i5) {
        a.a(view, i2, i3, i4, i5);
    }

    private static void a() {
        if (!c) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                b = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtils", "fetchViewFlagsField: ");
            }
            c = true;
        }
    }
}
