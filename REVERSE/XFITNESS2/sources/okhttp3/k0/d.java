package okhttp3.k0;

/* compiled from: NamedRunnable */
public abstract class d implements Runnable {
    protected final String e;

    public d(String str, Object... objArr) {
        this.e = e.a(str, objArr);
    }

    /* access modifiers changed from: protected */
    public abstract void b();

    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.e);
        try {
            b();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
