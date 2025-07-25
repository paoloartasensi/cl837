package androidx.core.a;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import androidx.core.content.c.c;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* compiled from: TypefaceCompatApi26Impl */
public class f extends d {

    /* renamed from: g  reason: collision with root package name */
    protected final Class f433g;

    /* renamed from: h  reason: collision with root package name */
    protected final Constructor f434h;

    /* renamed from: i  reason: collision with root package name */
    protected final Method f435i;

    /* renamed from: j  reason: collision with root package name */
    protected final Method f436j;
    protected final Method k;
    protected final Method l;
    protected final Method m;

    public f() {
        Method method;
        Method method2;
        Method method3;
        Method method4;
        Constructor constructor;
        Method method5;
        Class cls = null;
        try {
            Class a = a();
            constructor = e(a);
            method4 = b(a);
            method3 = c(a);
            method2 = f(a);
            method = a(a);
            Class cls2 = a;
            method5 = d(a);
            cls = cls2;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi26Impl", "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            method5 = null;
            constructor = null;
            method4 = null;
            method3 = null;
            method2 = null;
            method = null;
        }
        this.f433g = cls;
        this.f434h = constructor;
        this.f435i = method4;
        this.f436j = method3;
        this.k = method2;
        this.l = method;
        this.m = method5;
    }

    private boolean a(Context context, Object obj, String str, int i2, int i3, int i4, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.f435i.invoke(obj, new Object[]{context.getAssets(), str, 0, false, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), fontVariationAxisArr})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean b() {
        if (this.f435i == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.f435i != null;
    }

    private Object c() {
        try {
            return this.f434h.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Method d(Class cls) {
        Class cls2 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(cls, 1).getClass(), cls2, cls2});
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    /* access modifiers changed from: protected */
    public Constructor e(Class cls) {
        return cls.getConstructor(new Class[0]);
    }

    /* access modifiers changed from: protected */
    public Method f(Class cls) {
        return cls.getMethod("freeze", new Class[0]);
    }

    private boolean c(Object obj) {
        try {
            return ((Boolean) this.k.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Method c(Class cls) {
        Class cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromBuffer", new Class[]{ByteBuffer.class, cls2, FontVariationAxis[].class, cls2, cls2});
    }

    private void b(Object obj) {
        try {
            this.l.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
    }

    private boolean a(Object obj, ByteBuffer byteBuffer, int i2, int i3, int i4) {
        try {
            return ((Boolean) this.f436j.invoke(obj, new Object[]{byteBuffer, Integer.valueOf(i2), null, Integer.valueOf(i3), Integer.valueOf(i4)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public Method b(Class cls) {
        Class cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", new Class[]{AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class});
    }

    /* access modifiers changed from: protected */
    public Typeface a(Object obj) {
        try {
            Object newInstance = Array.newInstance(this.f433g, 1);
            Array.set(newInstance, 0, obj);
            return (Typeface) this.m.invoke((Object) null, new Object[]{newInstance, -1, -1});
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    public Typeface a(Context context, c.b bVar, Resources resources, int i2) {
        if (!b()) {
            return super.a(context, bVar, resources, i2);
        }
        Object c = c();
        if (c == null) {
            return null;
        }
        for (c.C0015c cVar : bVar.a()) {
            if (!a(context, c, cVar.a(), cVar.c(), cVar.e(), cVar.f() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(cVar.d()))) {
                b(c);
                return null;
            }
        }
        if (!c(c)) {
            return null;
        }
        return a(c);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004b, code lost:
        r12 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        if (r11 != null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r11.close();
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0051 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface a(android.content.Context r11, android.os.CancellationSignal r12, androidx.core.e.b.f[] r13, int r14) {
        /*
            r10 = this;
            int r0 = r13.length
            r1 = 1
            r2 = 0
            if (r0 >= r1) goto L_0x0006
            return r2
        L_0x0006:
            boolean r0 = r10.b()
            if (r0 != 0) goto L_0x0053
            androidx.core.e.b$f r13 = r10.a((androidx.core.e.b.f[]) r13, (int) r14)
            android.content.ContentResolver r11 = r11.getContentResolver()
            android.net.Uri r14 = r13.c()     // Catch:{ IOException -> 0x0052 }
            java.lang.String r0 = "r"
            android.os.ParcelFileDescriptor r11 = r11.openFileDescriptor(r14, r0, r12)     // Catch:{ IOException -> 0x0052 }
            if (r11 != 0) goto L_0x0026
            if (r11 == 0) goto L_0x0025
            r11.close()     // Catch:{ IOException -> 0x0052 }
        L_0x0025:
            return r2
        L_0x0026:
            android.graphics.Typeface$Builder r12 = new android.graphics.Typeface$Builder     // Catch:{ all -> 0x0049 }
            java.io.FileDescriptor r14 = r11.getFileDescriptor()     // Catch:{ all -> 0x0049 }
            r12.<init>(r14)     // Catch:{ all -> 0x0049 }
            int r14 = r13.d()     // Catch:{ all -> 0x0049 }
            android.graphics.Typeface$Builder r12 = r12.setWeight(r14)     // Catch:{ all -> 0x0049 }
            boolean r13 = r13.e()     // Catch:{ all -> 0x0049 }
            android.graphics.Typeface$Builder r12 = r12.setItalic(r13)     // Catch:{ all -> 0x0049 }
            android.graphics.Typeface r12 = r12.build()     // Catch:{ all -> 0x0049 }
            if (r11 == 0) goto L_0x0048
            r11.close()     // Catch:{ IOException -> 0x0052 }
        L_0x0048:
            return r12
        L_0x0049:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x004b }
        L_0x004b:
            r12 = move-exception
            if (r11 == 0) goto L_0x0051
            r11.close()     // Catch:{ all -> 0x0051 }
        L_0x0051:
            throw r12     // Catch:{ IOException -> 0x0052 }
        L_0x0052:
            return r2
        L_0x0053:
            java.util.Map r11 = androidx.core.e.b.a((android.content.Context) r11, (androidx.core.e.b.f[]) r13, (android.os.CancellationSignal) r12)
            java.lang.Object r12 = r10.c()
            if (r12 != 0) goto L_0x005e
            return r2
        L_0x005e:
            int r0 = r13.length
            r3 = 0
            r9 = 0
        L_0x0061:
            if (r9 >= r0) goto L_0x008e
            r4 = r13[r9]
            android.net.Uri r5 = r4.c()
            java.lang.Object r5 = r11.get(r5)
            java.nio.ByteBuffer r5 = (java.nio.ByteBuffer) r5
            if (r5 != 0) goto L_0x0072
            goto L_0x008b
        L_0x0072:
            int r6 = r4.b()
            int r7 = r4.d()
            boolean r8 = r4.e()
            r3 = r10
            r4 = r12
            boolean r3 = r3.a((java.lang.Object) r4, (java.nio.ByteBuffer) r5, (int) r6, (int) r7, (int) r8)
            if (r3 != 0) goto L_0x008a
            r10.b((java.lang.Object) r12)
            return r2
        L_0x008a:
            r3 = 1
        L_0x008b:
            int r9 = r9 + 1
            goto L_0x0061
        L_0x008e:
            if (r3 != 0) goto L_0x0094
            r10.b((java.lang.Object) r12)
            return r2
        L_0x0094:
            boolean r11 = r10.c((java.lang.Object) r12)
            if (r11 != 0) goto L_0x009b
            return r2
        L_0x009b:
            android.graphics.Typeface r11 = r10.a((java.lang.Object) r12)
            if (r11 != 0) goto L_0x00a2
            return r2
        L_0x00a2:
            android.graphics.Typeface r11 = android.graphics.Typeface.create(r11, r14)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.a.f.a(android.content.Context, android.os.CancellationSignal, androidx.core.e.b$f[], int):android.graphics.Typeface");
    }

    public Typeface a(Context context, Resources resources, int i2, String str, int i3) {
        if (!b()) {
            return super.a(context, resources, i2, str, i3);
        }
        Object c = c();
        if (c == null) {
            return null;
        }
        if (!a(context, c, str, 0, -1, -1, (FontVariationAxis[]) null)) {
            b(c);
            return null;
        } else if (!c(c)) {
            return null;
        } else {
            return a(c);
        }
    }

    /* access modifiers changed from: protected */
    public Class a() {
        return Class.forName("android.graphics.FontFamily");
    }

    /* access modifiers changed from: protected */
    public Method a(Class cls) {
        return cls.getMethod("abortCreation", new Class[0]);
    }
}
