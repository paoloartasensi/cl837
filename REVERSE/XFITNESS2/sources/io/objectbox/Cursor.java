package io.objectbox;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.Closeable;
import java.util.List;

public abstract class Cursor<T> implements Closeable {

    /* renamed from: j  reason: collision with root package name */
    static boolean f1752j;
    protected final Transaction e;

    /* renamed from: f  reason: collision with root package name */
    protected final long f1753f;

    /* renamed from: g  reason: collision with root package name */
    protected final boolean f1754g;

    /* renamed from: h  reason: collision with root package name */
    protected boolean f1755h;

    /* renamed from: i  reason: collision with root package name */
    private final Throwable f1756i;

    static native boolean nativeDeleteEntity(long j2, long j3);

    static native Object nativeGetEntity(long j2, long j3);

    public abstract long a(T t);

    public Transaction a() {
        return this.e;
    }

    public boolean c() {
        return this.f1755h;
    }

    public synchronized void close() {
        if (!this.f1755h) {
            this.f1755h = true;
            if (this.e != null && !this.e.m().j()) {
                nativeDestroy(this.f1753f);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (!this.f1755h) {
            if (!this.f1754g || f1752j) {
                System.err.println("Cursor was not closed.");
                if (this.f1756i != null) {
                    System.err.println("Cursor was initially created here:");
                    this.f1756i.printStackTrace();
                }
                System.err.flush();
            }
            close();
            super.finalize();
        }
    }

    public boolean h(long j2) {
        return nativeDeleteEntity(this.f1753f, j2);
    }

    public T i(long j2) {
        return nativeGetEntity(this.f1753f, j2);
    }

    public void j() {
        nativeRenew(this.f1753f);
    }

    /* access modifiers changed from: package-private */
    public native void nativeDestroy(long j2);

    /* access modifiers changed from: package-private */
    public native List nativeGetBacklinkEntities(long j2, int i2, int i3, long j3);

    /* access modifiers changed from: package-private */
    public native List nativeGetRelationEntities(long j2, int i2, int i3, long j3, boolean z);

    /* access modifiers changed from: package-private */
    public native void nativeModifyRelations(long j2, int i2, long j3, long[] jArr, boolean z);

    /* access modifiers changed from: package-private */
    public native long nativeRenew(long j2);

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cursor ");
        sb.append(Long.toString(this.f1753f, 16));
        sb.append(c() ? "(closed)" : BuildConfig.FLAVOR);
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public List<T> a(int i2, Property property, long j2) {
        try {
            return nativeGetBacklinkEntities(this.f1753f, i2, property.getId(), j2);
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException("Please check if the given property belongs to a valid @Relation: " + property, e2);
        }
    }

    public List<T> a(int i2, int i3, long j2, boolean z) {
        return nativeGetRelationEntities(this.f1753f, i2, i3, j2, z);
    }

    public void a(int i2, long j2, long[] jArr, boolean z) {
        nativeModifyRelations(this.f1753f, i2, j2, jArr, z);
    }
}
