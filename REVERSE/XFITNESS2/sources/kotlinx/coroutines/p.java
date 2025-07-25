package kotlinx.coroutines;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: CommonPool.kt */
public final class p extends d1 {
    private static final int e;

    /* renamed from: f  reason: collision with root package name */
    private static boolean f1810f;

    /* renamed from: g  reason: collision with root package name */
    public static final p f1811g = new p();
    private static volatile Executor pool;

    /* compiled from: CommonPool.kt */
    static final class a implements ThreadFactory {
        final /* synthetic */ AtomicInteger a;

        a(AtomicInteger atomicInteger) {
            this.a = atomicInteger;
        }

        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "CommonPool-worker-" + this.a.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        }
    }

    /* compiled from: CommonPool.kt */
    static final class b implements Runnable {
        public static final b e = new b();

        b() {
        }

        public final void run() {
        }
    }

    static {
        String str;
        int i2;
        try {
            str = System.getProperty("kotlinx.coroutines.default.parallelism");
        } catch (Throwable unused) {
            str = null;
        }
        if (str != null) {
            Integer a2 = k.a(str);
            if (a2 == null || a2.intValue() < 1) {
                throw new IllegalStateException(("Expected positive number in kotlinx.coroutines.default.parallelism, but has " + str).toString());
            }
            i2 = a2.intValue();
        } else {
            i2 = -1;
        }
        e = i2;
    }

    private p() {
    }

    private final ExecutorService n() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(q(), new a(new AtomicInteger()));
        i.a((Object) newFixedThreadPool, "Executors.newFixedThread…Daemon = true }\n        }");
        return newFixedThreadPool;
    }

    private final ExecutorService o() {
        Class<?> cls;
        ExecutorService executorService;
        if (System.getSecurityManager() != null) {
            return n();
        }
        ExecutorService executorService2 = null;
        try {
            cls = Class.forName("java.util.concurrent.ForkJoinPool");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls == null) {
            return n();
        }
        if (!f1810f && e < 0) {
            try {
                Method method = cls.getMethod("commonPool", new Class[0]);
                Object invoke = method != null ? method.invoke((Object) null, new Object[0]) : null;
                if (!(invoke instanceof ExecutorService)) {
                    invoke = null;
                }
                executorService = (ExecutorService) invoke;
            } catch (Throwable unused2) {
                executorService = null;
            }
            if (executorService != null) {
                if (!f1811g.a(cls, executorService)) {
                    executorService = null;
                }
                if (executorService != null) {
                    return executorService;
                }
            }
        }
        try {
            Object newInstance = cls.getConstructor(new Class[]{Integer.TYPE}).newInstance(new Object[]{Integer.valueOf(f1811g.q())});
            if (!(newInstance instanceof ExecutorService)) {
                newInstance = null;
            }
            executorService2 = (ExecutorService) newInstance;
        } catch (Throwable unused3) {
        }
        if (executorService2 != null) {
            return executorService2;
        }
        return n();
    }

    private final synchronized Executor p() {
        Executor executor;
        executor = pool;
        if (executor == null) {
            executor = o();
            pool = executor;
        }
        return executor;
    }

    private final int q() {
        Integer valueOf = Integer.valueOf(e);
        if (!(valueOf.intValue() > 0)) {
            valueOf = null;
        }
        if (valueOf != null) {
            return valueOf.intValue();
        }
        return f.a(Runtime.getRuntime().availableProcessors() - 1, 1);
    }

    public final boolean a(Class<?> cls, ExecutorService executorService) {
        i.b(cls, "fjpClass");
        i.b(executorService, "executor");
        executorService.submit(b.e);
        Integer num = null;
        try {
            Object invoke = cls.getMethod("getPoolSize", new Class[0]).invoke(executorService, new Object[0]);
            if (!(invoke instanceof Integer)) {
                invoke = null;
            }
            num = (Integer) invoke;
        } catch (Throwable unused) {
        }
        if (num == null || num.intValue() < 1) {
            return false;
        }
        return true;
    }

    public void close() {
        throw new IllegalStateException("Close cannot be invoked on CommonPool".toString());
    }

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        Runnable runnable2;
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        try {
            Executor executor = pool;
            if (executor == null) {
                executor = p();
            }
            e2 a2 = f2.a();
            if (a2 == null || (runnable2 = a2.a(runnable)) == null) {
                runnable2 = runnable;
            }
            executor.execute(runnable2);
        } catch (RejectedExecutionException unused) {
            e2 a3 = f2.a();
            if (a3 != null) {
                a3.c();
            }
            l0.k.a(runnable);
        }
    }

    public String toString() {
        return "CommonPool";
    }
}
