package io.objectbox;

import java.io.Closeable;

public class Transaction implements Closeable {
    static boolean k;
    private final long e;

    /* renamed from: f  reason: collision with root package name */
    private final BoxStore f1757f;

    /* renamed from: g  reason: collision with root package name */
    private final boolean f1758g;

    /* renamed from: h  reason: collision with root package name */
    private final Throwable f1759h;

    /* renamed from: i  reason: collision with root package name */
    private int f1760i;

    /* renamed from: j  reason: collision with root package name */
    private volatile boolean f1761j;

    public Transaction(BoxStore boxStore, long j2, int i2) {
        this.f1757f = boxStore;
        this.e = j2;
        this.f1760i = i2;
        this.f1758g = nativeIsReadOnly(j2);
        this.f1759h = k ? new Throwable() : null;
    }

    private void s() {
        if (this.f1761j) {
            throw new IllegalStateException("Transaction is closed");
        }
    }

    public void a() {
        s();
        nativeAbort(this.e);
    }

    public void c() {
        s();
        this.f1757f.a(this, nativeCommit(this.e));
    }

    public synchronized void close() {
        if (!this.f1761j) {
            this.f1761j = true;
            this.f1757f.a(this);
            if (!nativeIsOwnerThread(this.e)) {
                boolean nativeIsActive = nativeIsActive(this.e);
                boolean nativeIsRecycled = nativeIsRecycled(this.e);
                if (nativeIsActive || nativeIsRecycled) {
                    String str = " (initial commit count: " + this.f1760i + ").";
                    if (nativeIsActive) {
                        System.err.println("Transaction is still active" + str);
                    } else {
                        System.out.println("Hint: use closeThreadResources() to avoid finalizing recycled transactions" + str);
                        System.out.flush();
                    }
                    if (this.f1759h != null) {
                        System.err.println("Transaction was initially created here:");
                        this.f1759h.printStackTrace();
                    }
                    System.err.flush();
                }
            }
            if (!this.f1757f.j()) {
                nativeDestroy(this.e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        close();
        super.finalize();
    }

    public void j() {
        c();
        close();
    }

    public BoxStore m() {
        return this.f1757f;
    }

    public boolean n() {
        return this.f1761j;
    }

    /* access modifiers changed from: package-private */
    public native void nativeAbort(long j2);

    /* access modifiers changed from: package-private */
    public native int[] nativeCommit(long j2);

    /* access modifiers changed from: package-private */
    public native long nativeCreateCursor(long j2, String str, Class cls);

    /* access modifiers changed from: package-private */
    public native void nativeDestroy(long j2);

    /* access modifiers changed from: package-private */
    public native boolean nativeIsActive(long j2);

    /* access modifiers changed from: package-private */
    public native boolean nativeIsOwnerThread(long j2);

    /* access modifiers changed from: package-private */
    public native boolean nativeIsReadOnly(long j2);

    /* access modifiers changed from: package-private */
    public native boolean nativeIsRecycled(long j2);

    /* access modifiers changed from: package-private */
    public native void nativeRecycle(long j2);

    /* access modifiers changed from: package-private */
    public native void nativeRenew(long j2);

    public boolean o() {
        return this.f1758g;
    }

    public boolean p() {
        s();
        return nativeIsRecycled(this.e);
    }

    public void q() {
        s();
        nativeRecycle(this.e);
    }

    public void r() {
        s();
        this.f1760i = this.f1757f.s;
        nativeRenew(this.e);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TX ");
        sb.append(Long.toString(this.e, 16));
        sb.append(" (");
        sb.append(this.f1758g ? "read-only" : "write");
        sb.append(", initialCommitCount=");
        sb.append(this.f1760i);
        sb.append(")");
        return sb.toString();
    }

    public <T> Cursor<T> a(Class<T> cls) {
        s();
        EntityInfo b = this.f1757f.b(cls);
        return b.getCursorFactory().a(this, nativeCreateCursor(this.e, b.getDbName(), cls), this.f1757f);
    }
}
