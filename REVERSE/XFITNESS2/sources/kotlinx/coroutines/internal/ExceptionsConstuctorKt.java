package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Result;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt {
    private static final int a = b(Throwable.class, -1);
    private static final ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, l<Throwable, Throwable>> c = new WeakHashMap<>();

    /* compiled from: Comparisons.kt */
    public static final class a<T> implements Comparator<T> {
        public final int compare(T t, T t2) {
            Constructor constructor = (Constructor) t2;
            i.a((Object) constructor, "it");
            Integer valueOf = Integer.valueOf(constructor.getParameterTypes().length);
            Constructor constructor2 = (Constructor) t;
            i.a((Object) constructor2, "it");
            return b.a(valueOf, Integer.valueOf(constructor2.getParameterTypes().length));
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static final <E extends java.lang.Throwable> E a(E r9) {
        /*
            java.lang.String r0 = "exception"
            kotlin.jvm.internal.i.b(r9, r0)
            boolean r0 = r9 instanceof kotlinx.coroutines.z
            r1 = 0
            if (r0 == 0) goto L_0x002d
            kotlin.Result$a r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0017 }
            kotlinx.coroutines.z r9 = (kotlinx.coroutines.z) r9     // Catch:{ all -> 0x0017 }
            java.lang.Throwable r9 = r9.createCopy()     // Catch:{ all -> 0x0017 }
            java.lang.Object r9 = kotlin.Result.m1constructorimpl(r9)     // Catch:{ all -> 0x0017 }
            goto L_0x0022
        L_0x0017:
            r9 = move-exception
            kotlin.Result$a r0 = kotlin.Result.Companion
            java.lang.Object r9 = kotlin.i.a((java.lang.Throwable) r9)
            java.lang.Object r9 = kotlin.Result.m1constructorimpl(r9)
        L_0x0022:
            boolean r0 = kotlin.Result.m6isFailureimpl(r9)
            if (r0 == 0) goto L_0x0029
            goto L_0x002a
        L_0x0029:
            r1 = r9
        L_0x002a:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            return r1
        L_0x002d:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()
            r0.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.b.l<java.lang.Throwable, java.lang.Throwable>> r2 = c     // Catch:{ all -> 0x012a }
            java.lang.Class r3 = r9.getClass()     // Catch:{ all -> 0x012a }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x012a }
            kotlin.jvm.b.l r2 = (kotlin.jvm.b.l) r2     // Catch:{ all -> 0x012a }
            r0.unlock()
            if (r2 == 0) goto L_0x004e
            java.lang.Object r9 = r2.invoke(r9)
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            return r9
        L_0x004e:
            int r0 = a
            java.lang.Class r2 = r9.getClass()
            r3 = 0
            int r2 = b(r2, r3)
            if (r0 == r2) goto L_0x00a3
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            int r4 = r0.getWriteHoldCount()
            if (r4 != 0) goto L_0x006c
            int r4 = r0.getReadHoldCount()
            goto L_0x006d
        L_0x006c:
            r4 = 0
        L_0x006d:
            r5 = 0
        L_0x006e:
            if (r5 >= r4) goto L_0x0076
            r2.unlock()
            int r5 = r5 + 1
            goto L_0x006e
        L_0x0076:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.b.l<java.lang.Throwable, java.lang.Throwable>> r5 = c     // Catch:{ all -> 0x0096 }
            java.lang.Class r9 = r9.getClass()     // Catch:{ all -> 0x0096 }
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1 r6 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1.INSTANCE     // Catch:{ all -> 0x0096 }
            r5.put(r9, r6)     // Catch:{ all -> 0x0096 }
            kotlin.l r9 = kotlin.l.a     // Catch:{ all -> 0x0096 }
        L_0x008a:
            if (r3 >= r4) goto L_0x0092
            r2.lock()
            int r3 = r3 + 1
            goto L_0x008a
        L_0x0092:
            r0.unlock()
            return r1
        L_0x0096:
            r9 = move-exception
        L_0x0097:
            if (r3 >= r4) goto L_0x009f
            r2.lock()
            int r3 = r3 + 1
            goto L_0x0097
        L_0x009f:
            r0.unlock()
            throw r9
        L_0x00a3:
            java.lang.Class r0 = r9.getClass()
            java.lang.reflect.Constructor[] r0 = r0.getConstructors()
            java.lang.String r2 = "exception.javaClass.constructors"
            kotlin.jvm.internal.i.a((java.lang.Object) r0, (java.lang.String) r2)
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$a r2 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$a
            r2.<init>()
            java.util.List r0 = kotlin.collections.f.c(r0, r2)
            java.util.Iterator r0 = r0.iterator()
            r2 = r1
        L_0x00be:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x00d5
            java.lang.Object r2 = r0.next()
            java.lang.reflect.Constructor r2 = (java.lang.reflect.Constructor) r2
            java.lang.String r4 = "constructor"
            kotlin.jvm.internal.i.a((java.lang.Object) r2, (java.lang.String) r4)
            kotlin.jvm.b.l r2 = a((java.lang.reflect.Constructor<?>) r2)
            if (r2 == 0) goto L_0x00be
        L_0x00d5:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r4 = r0.readLock()
            int r5 = r0.getWriteHoldCount()
            if (r5 != 0) goto L_0x00e6
            int r5 = r0.getReadHoldCount()
            goto L_0x00e7
        L_0x00e6:
            r5 = 0
        L_0x00e7:
            r6 = 0
        L_0x00e8:
            if (r6 >= r5) goto L_0x00f0
            r4.unlock()
            int r6 = r6 + 1
            goto L_0x00e8
        L_0x00f0:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.b.l<java.lang.Throwable, java.lang.Throwable>> r6 = c     // Catch:{ all -> 0x011d }
            java.lang.Class r7 = r9.getClass()     // Catch:{ all -> 0x011d }
            if (r2 == 0) goto L_0x0101
            r8 = r2
            goto L_0x0103
        L_0x0101:
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1 r8 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1.INSTANCE     // Catch:{ all -> 0x011d }
        L_0x0103:
            r6.put(r7, r8)     // Catch:{ all -> 0x011d }
            kotlin.l r6 = kotlin.l.a     // Catch:{ all -> 0x011d }
        L_0x0108:
            if (r3 >= r5) goto L_0x0110
            r4.lock()
            int r3 = r3 + 1
            goto L_0x0108
        L_0x0110:
            r0.unlock()
            if (r2 == 0) goto L_0x011c
            java.lang.Object r9 = r2.invoke(r9)
            r1 = r9
            java.lang.Throwable r1 = (java.lang.Throwable) r1
        L_0x011c:
            return r1
        L_0x011d:
            r9 = move-exception
        L_0x011e:
            if (r3 >= r5) goto L_0x0126
            r4.lock()
            int r3 = r3 + 1
            goto L_0x011e
        L_0x0126:
            r0.unlock()
            throw r9
        L_0x012a:
            r9 = move-exception
            r0.unlock()
            goto L_0x0130
        L_0x012f:
            throw r9
        L_0x0130:
            goto L_0x012f
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ExceptionsConstuctorKt.a(java.lang.Throwable):java.lang.Throwable");
    }

    private static final int b(Class<?> cls, int i2) {
        Integer num;
        kotlin.jvm.a.a(cls);
        try {
            Result.a aVar = Result.Companion;
            num = Result.m1constructorimpl(Integer.valueOf(a(cls, 0, 1, (Object) null)));
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            num = Result.m1constructorimpl(kotlin.i.a(th));
        }
        Integer valueOf = Integer.valueOf(i2);
        if (Result.m6isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    private static final l<Throwable, Throwable> a(Constructor<?> constructor) {
        Class<String> cls = String.class;
        Class[] parameterTypes = constructor.getParameterTypes();
        int length = parameterTypes.length;
        if (length == 0) {
            return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4(constructor);
        }
        if (length == 1) {
            Class cls2 = parameterTypes[0];
            if (i.a((Object) cls2, (Object) Throwable.class)) {
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2(constructor);
            }
            if (i.a((Object) cls2, (Object) cls)) {
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(constructor);
            }
            return null;
        } else if (length == 2 && i.a((Object) parameterTypes[0], (Object) cls) && i.a((Object) parameterTypes[1], (Object) Throwable.class)) {
            return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1(constructor);
        } else {
            return null;
        }
    }

    static /* synthetic */ int a(Class cls, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = 0;
        }
        return a(cls, i2);
    }

    private static final int a(Class<?> cls, int i2) {
        Class<? super Object> superclass;
        do {
            Field[] declaredFields = r6.getDeclaredFields();
            i.a((Object) declaredFields, "declaredFields");
            int i3 = 0;
            Class<? super Object> cls2 = cls;
            for (Field field : declaredFields) {
                i.a((Object) field, "it");
                if (!Modifier.isStatic(field.getModifiers())) {
                    i3++;
                }
            }
            i2 += i3;
            superclass = cls2.getSuperclass();
            cls2 = superclass;
        } while (superclass != null);
        return i2;
    }
}
