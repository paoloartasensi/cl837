package androidx.core.e;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: SelfDestructiveThread */
public class c {
    private final Object a = new Object();
    private HandlerThread b;
    private Handler c;
    private int d;
    private Handler.Callback e = new a();

    /* renamed from: f  reason: collision with root package name */
    private final int f479f;

    /* renamed from: g  reason: collision with root package name */
    private final int f480g;

    /* renamed from: h  reason: collision with root package name */
    private final String f481h;

    /* compiled from: SelfDestructiveThread */
    class a implements Handler.Callback {
        a() {
        }

        public boolean handleMessage(Message message) {
            int i2 = message.what;
            if (i2 == 0) {
                c.this.a();
                return true;
            } else if (i2 != 1) {
                return true;
            } else {
                c.this.a((Runnable) message.obj);
                return true;
            }
        }
    }

    /* compiled from: SelfDestructiveThread */
    class b implements Runnable {
        final /* synthetic */ Callable e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Handler f482f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ d f483g;

        /* compiled from: SelfDestructiveThread */
        class a implements Runnable {
            final /* synthetic */ Object e;

            a(Object obj) {
                this.e = obj;
            }

            public void run() {
                b.this.f483g.a(this.e);
            }
        }

        b(c cVar, Callable callable, Handler handler, d dVar) {
            this.e = callable;
            this.f482f = handler;
            this.f483g = dVar;
        }

        public void run() {
            Object obj;
            try {
                obj = this.e.call();
            } catch (Exception unused) {
                obj = null;
            }
            this.f482f.post(new a(obj));
        }
    }

    /* renamed from: androidx.core.e.c$c  reason: collision with other inner class name */
    /* compiled from: SelfDestructiveThread */
    class C0019c implements Runnable {
        final /* synthetic */ AtomicReference e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Callable f485f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ ReentrantLock f486g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ AtomicBoolean f487h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ Condition f488i;

        C0019c(c cVar, AtomicReference atomicReference, Callable callable, ReentrantLock reentrantLock, AtomicBoolean atomicBoolean, Condition condition) {
            this.e = atomicReference;
            this.f485f = callable;
            this.f486g = reentrantLock;
            this.f487h = atomicBoolean;
            this.f488i = condition;
        }

        public void run() {
            try {
                this.e.set(this.f485f.call());
            } catch (Exception unused) {
            }
            this.f486g.lock();
            try {
                this.f487h.set(false);
                this.f488i.signal();
            } finally {
                this.f486g.unlock();
            }
        }
    }

    /* compiled from: SelfDestructiveThread */
    public interface d<T> {
        void a(T t);
    }

    public c(String str, int i2, int i3) {
        this.f481h = str;
        this.f480g = i2;
        this.f479f = i3;
        this.d = 0;
    }

    private void b(Runnable runnable) {
        synchronized (this.a) {
            if (this.b == null) {
                HandlerThread handlerThread = new HandlerThread(this.f481h, this.f480g);
                this.b = handlerThread;
                handlerThread.start();
                this.c = new Handler(this.b.getLooper(), this.e);
                this.d++;
            }
            this.c.removeMessages(0);
            this.c.sendMessage(this.c.obtainMessage(1, runnable));
        }
    }

    public <T> void a(Callable<T> callable, d<T> dVar) {
        b(new b(this, callable, new Handler(), dVar));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:9|10|11|12|(4:25|14|15|16)(1:17)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003f */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0045 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T a(java.util.concurrent.Callable<T> r13, int r14) {
        /*
            r12 = this;
            java.util.concurrent.locks.ReentrantLock r7 = new java.util.concurrent.locks.ReentrantLock
            r7.<init>()
            java.util.concurrent.locks.Condition r8 = r7.newCondition()
            java.util.concurrent.atomic.AtomicReference r9 = new java.util.concurrent.atomic.AtomicReference
            r9.<init>()
            java.util.concurrent.atomic.AtomicBoolean r10 = new java.util.concurrent.atomic.AtomicBoolean
            r0 = 1
            r10.<init>(r0)
            androidx.core.e.c$c r11 = new androidx.core.e.c$c
            r0 = r11
            r1 = r12
            r2 = r9
            r3 = r13
            r4 = r7
            r5 = r10
            r6 = r8
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r12.b(r11)
            r7.lock()
            boolean r13 = r10.get()     // Catch:{ all -> 0x005c }
            if (r13 != 0) goto L_0x0034
            java.lang.Object r13 = r9.get()     // Catch:{ all -> 0x005c }
            r7.unlock()
            return r13
        L_0x0034:
            java.util.concurrent.TimeUnit r13 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x005c }
            long r0 = (long) r14     // Catch:{ all -> 0x005c }
            long r13 = r13.toNanos(r0)     // Catch:{ all -> 0x005c }
        L_0x003b:
            long r13 = r8.awaitNanos(r13)     // Catch:{ InterruptedException -> 0x003f }
        L_0x003f:
            boolean r0 = r10.get()     // Catch:{ all -> 0x005c }
            if (r0 != 0) goto L_0x004d
            java.lang.Object r13 = r9.get()     // Catch:{ all -> 0x005c }
            r7.unlock()
            return r13
        L_0x004d:
            r0 = 0
            int r2 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x0054
            goto L_0x003b
        L_0x0054:
            java.lang.InterruptedException r13 = new java.lang.InterruptedException     // Catch:{ all -> 0x005c }
            java.lang.String r14 = "timeout"
            r13.<init>(r14)     // Catch:{ all -> 0x005c }
            throw r13     // Catch:{ all -> 0x005c }
        L_0x005c:
            r13 = move-exception
            r7.unlock()
            goto L_0x0062
        L_0x0061:
            throw r13
        L_0x0062:
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.e.c.a(java.util.concurrent.Callable, int):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    public void a(Runnable runnable) {
        runnable.run();
        synchronized (this.a) {
            this.c.removeMessages(0);
            this.c.sendMessageDelayed(this.c.obtainMessage(0), (long) this.f479f);
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        synchronized (this.a) {
            if (!this.c.hasMessages(1)) {
                this.b.quit();
                this.b = null;
                this.c = null;
            }
        }
    }
}
