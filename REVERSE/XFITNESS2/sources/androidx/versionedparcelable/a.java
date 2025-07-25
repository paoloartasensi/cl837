package androidx.versionedparcelable;

import android.os.Parcelable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: VersionedParcel */
public abstract class a {
    protected final g.a.a<String, Method> a;
    protected final g.a.a<String, Method> b;
    protected final g.a.a<String, Class> c;

    public a(g.a.a<String, Method> aVar, g.a.a<String, Method> aVar2, g.a.a<String, Class> aVar3) {
        this.a = aVar;
        this.b = aVar2;
        this.c = aVar3;
    }

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void a(Parcelable parcelable);

    /* access modifiers changed from: protected */
    public abstract void a(CharSequence charSequence);

    /* access modifiers changed from: protected */
    public abstract void a(String str);

    /* access modifiers changed from: protected */
    public abstract void a(boolean z);

    public void a(boolean z, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public abstract void a(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract boolean a(int i2);

    public boolean a(boolean z, int i2) {
        if (!a(i2)) {
            return z;
        }
        return d();
    }

    /* access modifiers changed from: protected */
    public abstract a b();

    /* access modifiers changed from: protected */
    public abstract void b(int i2);

    public void b(boolean z, int i2) {
        b(i2);
        a(z);
    }

    /* access modifiers changed from: protected */
    public abstract void c(int i2);

    public boolean c() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract boolean d();

    /* access modifiers changed from: protected */
    public abstract byte[] e();

    /* access modifiers changed from: protected */
    public abstract CharSequence f();

    /* access modifiers changed from: protected */
    public abstract int g();

    /* access modifiers changed from: protected */
    public abstract <T extends Parcelable> T h();

    /* access modifiers changed from: protected */
    public abstract String i();

    /* access modifiers changed from: protected */
    public <T extends c> T j() {
        String i2 = i();
        if (i2 == null) {
            return null;
        }
        return a(i2, b());
    }

    public int a(int i2, int i3) {
        if (!a(i3)) {
            return i2;
        }
        return g();
    }

    public void b(byte[] bArr, int i2) {
        b(i2);
        a(bArr);
    }

    public String a(String str, int i2) {
        if (!a(i2)) {
            return str;
        }
        return i();
    }

    public void b(CharSequence charSequence, int i2) {
        b(i2);
        a(charSequence);
    }

    public byte[] a(byte[] bArr, int i2) {
        if (!a(i2)) {
            return bArr;
        }
        return e();
    }

    public void b(int i2, int i3) {
        b(i3);
        c(i2);
    }

    public <T extends Parcelable> T a(T t, int i2) {
        if (!a(i2)) {
            return t;
        }
        return h();
    }

    public void b(String str, int i2) {
        b(i2);
        a(str);
    }

    public CharSequence a(CharSequence charSequence, int i2) {
        if (!a(i2)) {
            return charSequence;
        }
        return f();
    }

    public void b(Parcelable parcelable, int i2) {
        b(i2);
        a(parcelable);
    }

    /* access modifiers changed from: protected */
    public void a(c cVar) {
        if (cVar == null) {
            a((String) null);
            return;
        }
        b(cVar);
        a b2 = b();
        a(cVar, b2);
        b2.a();
    }

    public void b(c cVar, int i2) {
        b(i2);
        a(cVar);
    }

    private void b(c cVar) {
        try {
            a(a((Class<? extends c>) cVar.getClass()).getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(cVar.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    private Method b(String str) {
        Class<a> cls = a.class;
        Method method = this.a.get(str);
        if (method != null) {
            return method;
        }
        System.currentTimeMillis();
        Method declaredMethod = Class.forName(str, true, cls.getClassLoader()).getDeclaredMethod("read", new Class[]{cls});
        this.a.put(str, declaredMethod);
        return declaredMethod;
    }

    public <T extends c> T a(T t, int i2) {
        if (!a(i2)) {
            return t;
        }
        return j();
    }

    /* access modifiers changed from: protected */
    public <T extends c> T a(String str, a aVar) {
        try {
            return (c) b(str).invoke((Object) null, new Object[]{aVar});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private Method b(Class cls) {
        Method method = this.b.get(cls.getName());
        if (method != null) {
            return method;
        }
        Class a2 = a((Class<? extends c>) cls);
        System.currentTimeMillis();
        Method declaredMethod = a2.getDeclaredMethod("write", new Class[]{cls, a.class});
        this.b.put(cls.getName(), declaredMethod);
        return declaredMethod;
    }

    /* access modifiers changed from: protected */
    public <T extends c> void a(T t, a aVar) {
        try {
            b((Class) t.getClass()).invoke((Object) null, new Object[]{t, aVar});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private Class a(Class<? extends c> cls) {
        Class cls2 = this.c.get(cls.getName());
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(String.format("%s.%sParcelizer", new Object[]{cls.getPackage().getName(), cls.getSimpleName()}), false, cls.getClassLoader());
        this.c.put(cls.getName(), cls3);
        return cls3;
    }
}
