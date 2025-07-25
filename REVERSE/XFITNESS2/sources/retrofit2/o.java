package retrofit2;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import retrofit2.e;
import retrofit2.h;

/* compiled from: Platform */
class o {
    private static final o c = d();
    private final boolean a;
    private final Constructor<MethodHandles.Lookup> b;

    /* compiled from: Platform */
    static final class a extends o {

        /* renamed from: retrofit2.o$a$a  reason: collision with other inner class name */
        /* compiled from: Platform */
        static final class C0102a implements Executor {
            private final Handler e = new Handler(Looper.getMainLooper());

            C0102a() {
            }

            public void execute(Runnable runnable) {
                this.e.post(runnable);
            }
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a() {
            super(Build.VERSION.SDK_INT >= 24);
        }

        public Executor a() {
            return new C0102a();
        }

        /* access modifiers changed from: package-private */
        public Object a(Method method, Class<?> cls, Object obj, Object... objArr) {
            if (Build.VERSION.SDK_INT >= 26) {
                return o.super.a(method, cls, obj, objArr);
            }
            throw new UnsupportedOperationException("Calling default methods on API 24 and 25 is not supported");
        }
    }

    o(boolean z) {
        this.a = z;
        Constructor<MethodHandles.Lookup> constructor = null;
        if (z) {
            Class<MethodHandles.Lookup> cls = MethodHandles.Lookup.class;
            try {
                constructor = cls.getDeclaredConstructor(new Class[]{Class.class, Integer.TYPE});
                constructor.setAccessible(true);
            } catch (NoClassDefFoundError | NoSuchMethodException unused) {
            }
        }
        this.b = constructor;
    }

    private static o d() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new a();
            }
        } catch (ClassNotFoundException unused) {
        }
        return new o(true);
    }

    static o e() {
        return c;
    }

    /* access modifiers changed from: package-private */
    public List<? extends e.a> a(Executor executor) {
        i iVar = new i(executor);
        if (!this.a) {
            return Collections.singletonList(iVar);
        }
        return Arrays.asList(new e.a[]{g.a, iVar});
    }

    /* access modifiers changed from: package-private */
    public Executor a() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public List<? extends h.a> b() {
        if (this.a) {
            return Collections.singletonList(m.a);
        }
        return Collections.emptyList();
    }

    /* access modifiers changed from: package-private */
    public int c() {
        return this.a ? 1 : 0;
    }

    /* access modifiers changed from: package-private */
    public boolean a(Method method) {
        return this.a && method.isDefault();
    }

    /* access modifiers changed from: package-private */
    public Object a(Method method, Class<?> cls, Object obj, Object... objArr) {
        MethodHandles.Lookup lookup;
        Constructor<MethodHandles.Lookup> constructor = this.b;
        if (constructor != null) {
            lookup = constructor.newInstance(new Object[]{cls, -1});
        } else {
            lookup = MethodHandles.lookup();
        }
        return lookup.unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
    }
}
