package androidx.core.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import androidx.core.content.c.c;
import androidx.core.content.c.f;
import androidx.core.e.b;
import g.a.e;

/* compiled from: TypefaceCompat */
public class c {
    private static final h a;
    private static final e<String, Typeface> b = new e<>(16);

    static {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            a = new g();
        } else if (i2 >= 26) {
            a = new f();
        } else if (i2 >= 24 && e.a()) {
            a = new e();
        } else if (Build.VERSION.SDK_INT >= 21) {
            a = new d();
        } else {
            a = new h();
        }
    }

    private static String a(Resources resources, int i2, int i3) {
        return resources.getResourcePackageName(i2) + "-" + i2 + "-" + i3;
    }

    public static Typeface b(Resources resources, int i2, int i3) {
        return b.b(a(resources, i2, i3));
    }

    public static Typeface a(Context context, c.a aVar, Resources resources, int i2, int i3, f.a aVar2, Handler handler, boolean z) {
        Typeface typeface;
        if (aVar instanceof c.d) {
            c.d dVar = (c.d) aVar;
            boolean z2 = false;
            if (!z ? aVar2 == null : dVar.a() == 0) {
                z2 = true;
            }
            typeface = b.a(context, dVar.b(), aVar2, handler, z2, z ? dVar.c() : -1, i3);
        } else {
            typeface = a.a(context, (c.b) aVar, resources, i3);
            if (aVar2 != null) {
                if (typeface != null) {
                    aVar2.a(typeface, handler);
                } else {
                    aVar2.a(-3, handler);
                }
            }
        }
        if (typeface != null) {
            b.a(a(resources, i2, i3), typeface);
        }
        return typeface;
    }

    private static Typeface b(Context context, Typeface typeface, int i2) {
        c.b a2 = a.a(typeface);
        if (a2 == null) {
            return null;
        }
        return a.a(context, a2, context.getResources(), i2);
    }

    public static Typeface a(Context context, Resources resources, int i2, String str, int i3) {
        Typeface a2 = a.a(context, resources, i2, str, i3);
        if (a2 != null) {
            b.a(a(resources, i2, i3), a2);
        }
        return a2;
    }

    public static Typeface a(Context context, CancellationSignal cancellationSignal, b.f[] fVarArr, int i2) {
        return a.a(context, cancellationSignal, fVarArr, i2);
    }

    public static Typeface a(Context context, Typeface typeface, int i2) {
        Typeface b2;
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else if (Build.VERSION.SDK_INT >= 21 || (b2 = b(context, typeface, i2)) == null) {
            return Typeface.create(typeface, i2);
        } else {
            return b2;
        }
    }
}
