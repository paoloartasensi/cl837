package androidx.core.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.core.content.c.c;
import androidx.core.e.b;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: TypefaceCompatBaseImpl */
class h {
    private ConcurrentHashMap<Long, c.b> a = new ConcurrentHashMap<>();

    /* compiled from: TypefaceCompatBaseImpl */
    class a implements c<b.f> {
        a(h hVar) {
        }

        public int a(b.f fVar) {
            return fVar.d();
        }

        public boolean b(b.f fVar) {
            return fVar.e();
        }
    }

    /* compiled from: TypefaceCompatBaseImpl */
    class b implements c<c.C0015c> {
        b(h hVar) {
        }

        public int a(c.C0015c cVar) {
            return cVar.e();
        }

        public boolean b(c.C0015c cVar) {
            return cVar.f();
        }
    }

    /* compiled from: TypefaceCompatBaseImpl */
    private interface c<T> {
        int a(T t);

        boolean b(T t);
    }

    h() {
    }

    private static <T> T a(T[] tArr, int i2, c<T> cVar) {
        int i3 = (i2 & 1) == 0 ? 400 : 700;
        boolean z = (i2 & 2) != 0;
        T t = null;
        int i4 = Integer.MAX_VALUE;
        for (T t2 : tArr) {
            int abs = (Math.abs(cVar.a(t2) - i3) * 2) + (cVar.b(t2) == z ? 0 : 1);
            if (t == null || i4 > abs) {
                t = t2;
                i4 = abs;
            }
        }
        return t;
    }

    private static long b(Typeface typeface) {
        if (typeface == null) {
            return 0;
        }
        try {
            Field declaredField = Typeface.class.getDeclaredField("native_instance");
            declaredField.setAccessible(true);
            return ((Number) declaredField.get(typeface)).longValue();
        } catch (NoSuchFieldException e) {
            Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", e);
            return 0;
        } catch (IllegalAccessException e2) {
            Log.e("TypefaceCompatBaseImpl", "Could not retrieve font from family.", e2);
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public b.f a(b.f[] fVarArr, int i2) {
        return (b.f) a(fVarArr, i2, new a(this));
    }

    /* access modifiers changed from: protected */
    public Typeface a(Context context, InputStream inputStream) {
        File a2 = i.a(context);
        if (a2 == null) {
            return null;
        }
        try {
            if (!i.a(a2, inputStream)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(a2.getPath());
            a2.delete();
            return createFromFile;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            a2.delete();
        }
    }

    public Typeface a(Context context, CancellationSignal cancellationSignal, b.f[] fVarArr, int i2) {
        InputStream inputStream;
        InputStream inputStream2 = null;
        if (fVarArr.length < 1) {
            return null;
        }
        try {
            inputStream = context.getContentResolver().openInputStream(a(fVarArr, i2).c());
            try {
                Typeface a2 = a(context, inputStream);
                i.a((Closeable) inputStream);
                return a2;
            } catch (IOException unused) {
                i.a((Closeable) inputStream);
                return null;
            } catch (Throwable th) {
                th = th;
                inputStream2 = inputStream;
                i.a((Closeable) inputStream2);
                throw th;
            }
        } catch (IOException unused2) {
            inputStream = null;
            i.a((Closeable) inputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            i.a((Closeable) inputStream2);
            throw th;
        }
    }

    private c.C0015c a(c.b bVar, int i2) {
        return (c.C0015c) a(bVar.a(), i2, new b(this));
    }

    public Typeface a(Context context, c.b bVar, Resources resources, int i2) {
        c.C0015c a2 = a(bVar, i2);
        if (a2 == null) {
            return null;
        }
        Typeface a3 = c.a(context, resources, a2.b(), a2.a(), i2);
        a(a3, bVar);
        return a3;
    }

    public Typeface a(Context context, Resources resources, int i2, String str, int i3) {
        File a2 = i.a(context);
        if (a2 == null) {
            return null;
        }
        try {
            if (!i.a(a2, resources, i2)) {
                return null;
            }
            Typeface createFromFile = Typeface.createFromFile(a2.getPath());
            a2.delete();
            return createFromFile;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            a2.delete();
        }
    }

    /* access modifiers changed from: package-private */
    public c.b a(Typeface typeface) {
        long b2 = b(typeface);
        if (b2 == 0) {
            return null;
        }
        return this.a.get(Long.valueOf(b2));
    }

    private void a(Typeface typeface, c.b bVar) {
        long b2 = b(typeface);
        if (b2 != 0) {
            this.a.put(Long.valueOf(b2), bVar);
        }
    }
}
