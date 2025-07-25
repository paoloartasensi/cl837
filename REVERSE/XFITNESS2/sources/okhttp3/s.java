package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.e0;
import okhttp3.k0.e;

/* compiled from: Dispatcher */
public final class s {
    private int a = 64;
    private int b = 5;
    private Runnable c;
    private ExecutorService d;
    private final Deque<e0.a> e = new ArrayDeque();

    /* renamed from: f  reason: collision with root package name */
    private final Deque<e0.a> f2076f = new ArrayDeque();

    /* renamed from: g  reason: collision with root package name */
    private final Deque<e0> f2077g = new ArrayDeque();

    private boolean c() {
        int i2;
        boolean z;
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator<e0.a> it = this.e.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                e0.a next = it.next();
                if (this.f2076f.size() >= this.a) {
                    break;
                } else if (next.c().get() < this.b) {
                    it.remove();
                    next.c().incrementAndGet();
                    arrayList.add(next);
                    this.f2076f.add(next);
                }
            }
            z = b() > 0;
        }
        int size = arrayList.size();
        for (i2 = 0; i2 < size; i2++) {
            ((e0.a) arrayList.get(i2)).a(a());
        }
        return z;
    }

    public synchronized ExecutorService a() {
        if (this.d == null) {
            this.d = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), e.a("OkHttp Dispatcher", false));
        }
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public void b(e0.a aVar) {
        aVar.c().decrementAndGet();
        a(this.f2076f, aVar);
    }

    public synchronized int b() {
        return this.f2076f.size() + this.f2077g.size();
    }

    /* access modifiers changed from: package-private */
    public void a(e0.a aVar) {
        e0.a a2;
        synchronized (this) {
            this.e.add(aVar);
            if (!aVar.d().f1874h && (a2 = a(aVar.e())) != null) {
                aVar.a(a2);
            }
        }
        c();
    }

    private e0.a a(String str) {
        for (e0.a next : this.f2076f) {
            if (next.e().equals(str)) {
                return next;
            }
        }
        for (e0.a next2 : this.e) {
            if (next2.e().equals(str)) {
                return next2;
            }
        }
        return null;
    }

    private <T> void a(Deque<T> deque, T t) {
        Runnable runnable;
        synchronized (this) {
            if (deque.remove(t)) {
                runnable = this.c;
            } else {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
        if (!c() && runnable != null) {
            runnable.run();
        }
    }
}
