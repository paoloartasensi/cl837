package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* compiled from: UnsafeAllocator */
public abstract class i {

    /* compiled from: UnsafeAllocator */
    class a extends i {
        final /* synthetic */ Method a;
        final /* synthetic */ Object b;

        a(Method method, Object obj) {
            this.a = method;
            this.b = obj;
        }

        public <T> T a(Class<T> cls) {
            i.b(cls);
            return this.a.invoke(this.b, new Object[]{cls});
        }
    }

    /* compiled from: UnsafeAllocator */
    class b extends i {
        final /* synthetic */ Method a;
        final /* synthetic */ int b;

        b(Method method, int i2) {
            this.a = method;
            this.b = i2;
        }

        public <T> T a(Class<T> cls) {
            i.b(cls);
            return this.a.invoke((Object) null, new Object[]{cls, Integer.valueOf(this.b)});
        }
    }

    /* compiled from: UnsafeAllocator */
    class c extends i {
        final /* synthetic */ Method a;

        c(Method method) {
            this.a = method;
        }

        public <T> T a(Class<T> cls) {
            i.b(cls);
            return this.a.invoke((Object) null, new Object[]{cls, Object.class});
        }
    }

    /* compiled from: UnsafeAllocator */
    class d extends i {
        d() {
        }

        public <T> T a(Class<T> cls) {
            throw new UnsupportedOperationException("Cannot allocate " + cls);
        }
    }

    public static i a() {
        try {
            Class<?> cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            return new a(cls.getMethod("allocateInstance", new Class[]{Class.class}), declaredField.get((Object) null));
        } catch (Exception unused) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                int intValue = ((Integer) declaredMethod.invoke((Object) null, new Object[]{Object.class})).intValue();
                Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new b(declaredMethod2, intValue);
            } catch (Exception unused2) {
                try {
                    Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod3.setAccessible(true);
                    return new c(declaredMethod3);
                } catch (Exception unused3) {
                    return new d();
                }
            }
        }
    }

    static void b(Class<?> cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            throw new UnsupportedOperationException("Interface can't be instantiated! Interface name: " + cls.getName());
        } else if (Modifier.isAbstract(modifiers)) {
            throw new UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + cls.getName());
        }
    }

    public abstract <T> T a(Class<T> cls);
}
