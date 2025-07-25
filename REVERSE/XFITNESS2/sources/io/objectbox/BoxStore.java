package io.objectbox;

import java.io.Closeable;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class BoxStore implements Closeable {
    private static final Set<String> t = new HashSet();
    private final String e;

    /* renamed from: f  reason: collision with root package name */
    private final long f1747f;

    /* renamed from: g  reason: collision with root package name */
    private final Map<Class, String> f1748g;

    /* renamed from: h  reason: collision with root package name */
    private final Map<Class, EntityInfo> f1749h;

    /* renamed from: i  reason: collision with root package name */
    private final Map<Class, a> f1750i;

    /* renamed from: j  reason: collision with root package name */
    private final Set<Transaction> f1751j;
    private final ExecutorService k;
    private final c l;
    final boolean m;
    final boolean n;
    final boolean o;
    final ThreadLocal<Transaction> p;
    private boolean q;
    final Object r;
    volatile int s;

    private void n() {
        if (this.q) {
            throw new IllegalStateException("Store is closed");
        }
    }

    static native long nativeBeginReadTx(long j2);

    static native long nativeBeginTx(long j2);

    static native void nativeDelete(long j2);

    private void o() {
        try {
            if (!this.k.awaitTermination(1, TimeUnit.SECONDS)) {
                int activeCount = Thread.activeCount();
                System.err.println("Thread pool not terminated in time; printing stack traces...");
                Thread[] threadArr = new Thread[(activeCount + 2)];
                int enumerate = Thread.enumerate(threadArr);
                for (int i2 = 0; i2 < enumerate; i2++) {
                    PrintStream printStream = System.err;
                    printStream.println("Thread: " + threadArr[i2].getName());
                    Thread.dumpStack();
                }
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public Transaction a() {
        n();
        int i2 = this.s;
        if (this.m) {
            PrintStream printStream = System.out;
            printStream.println("Begin read TX with commit count " + i2);
        }
        Transaction transaction = new Transaction(this, nativeBeginReadTx(this.f1747f), i2);
        synchronized (this.f1751j) {
            this.f1751j.add(transaction);
        }
        return transaction;
    }

    /* access modifiers changed from: package-private */
    public EntityInfo b(Class cls) {
        return this.f1749h.get(cls);
    }

    public Transaction c() {
        n();
        int i2 = this.s;
        if (this.n) {
            PrintStream printStream = System.out;
            printStream.println("Begin TX with commit count " + i2);
        }
        Transaction transaction = new Transaction(this, nativeBeginTx(this.f1747f), i2);
        synchronized (this.f1751j) {
            this.f1751j.add(transaction);
        }
        return transaction;
    }

    public void close() {
        boolean z;
        ArrayList<Transaction> arrayList;
        synchronized (this) {
            z = this.q;
            if (!this.q) {
                this.q = true;
                synchronized (this.f1751j) {
                    arrayList = new ArrayList<>(this.f1751j);
                }
                for (Transaction close : arrayList) {
                    close.close();
                }
                if (this.f1747f != 0) {
                    nativeDelete(this.f1747f);
                }
                this.k.shutdown();
                o();
            }
        }
        if (!z) {
            synchronized (t) {
                t.remove(this.e);
                t.notifyAll();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        close();
        super.finalize();
    }

    public boolean j() {
        return this.q;
    }

    public boolean m() {
        return this.o;
    }

    public void a(Transaction transaction) {
        synchronized (this.f1751j) {
            this.f1751j.remove(transaction);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Transaction transaction, int[] iArr) {
        synchronized (this.r) {
            this.s++;
            if (this.n) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("TX committed. New commit count: ");
                sb.append(this.s);
                sb.append(", entity types affected: ");
                sb.append(iArr != null ? iArr.length : 0);
                printStream.println(sb.toString());
            }
        }
        for (a a : this.f1750i.values()) {
            a.a(transaction);
        }
        if (iArr != null) {
            this.l.a(iArr);
            throw null;
        }
    }

    public <T> a<T> a(Class<T> cls) {
        a<T> aVar;
        a<T> aVar2 = this.f1750i.get(cls);
        if (aVar2 != null) {
            return aVar2;
        }
        if (this.f1748g.containsKey(cls)) {
            synchronized (this.f1750i) {
                aVar = this.f1750i.get(cls);
                if (aVar == null) {
                    aVar = new a<>(this, cls);
                    this.f1750i.put(cls, aVar);
                }
            }
            return aVar;
        }
        throw new IllegalArgumentException(cls + " is not a known entity. Please add it and trigger generation again.");
    }

    public void a(Runnable runnable) {
        Transaction transaction = this.p.get();
        if (transaction == null) {
            Transaction c = c();
            this.p.set(c);
            try {
                runnable.run();
                c.c();
            } finally {
                this.p.remove();
                c.close();
            }
        } else if (!transaction.o()) {
            runnable.run();
        } else {
            throw new IllegalStateException("Cannot start a transaction while a read only transaction is active");
        }
    }
}
