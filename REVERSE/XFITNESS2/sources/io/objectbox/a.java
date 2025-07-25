package io.objectbox;

import io.objectbox.internal.b;
import java.util.List;

/* compiled from: Box */
public class a<T> {
    private final BoxStore a;
    private final Class<T> b;
    final ThreadLocal<Cursor<T>> c = new ThreadLocal<>();
    private final ThreadLocal<Cursor<T>> d = new ThreadLocal<>();
    private final b<T> e;

    a(BoxStore boxStore, Class<T> cls) {
        this.a = boxStore;
        this.b = cls;
        this.e = boxStore.b(cls).getIdGetter();
    }

    /* access modifiers changed from: package-private */
    public Cursor<T> a() {
        Transaction transaction = this.a.p.get();
        if (transaction == null) {
            return null;
        }
        if (!transaction.n()) {
            Cursor<T> cursor = this.c.get();
            if (cursor != null && !cursor.a().n()) {
                return cursor;
            }
            Cursor<T> a2 = transaction.a(this.b);
            this.c.set(a2);
            return a2;
        }
        throw new IllegalStateException("Active TX is closed");
    }

    /* access modifiers changed from: package-private */
    public Cursor<T> b() {
        Cursor<T> a2 = a();
        if (a2 != null) {
            return a2;
        }
        Cursor<T> cursor = this.d.get();
        if (cursor != null) {
            Transaction transaction = cursor.e;
            if (transaction.n() || !transaction.p()) {
                throw new IllegalStateException("Illegal reader TX state");
            }
            transaction.r();
            cursor.j();
            return cursor;
        }
        Cursor<T> a3 = this.a.a().a(this.b);
        this.d.set(a3);
        return a3;
    }

    /* access modifiers changed from: package-private */
    public Cursor<T> c() {
        Cursor<T> a2 = a();
        if (a2 != null) {
            return a2;
        }
        Transaction c2 = this.a.c();
        try {
            return c2.a(this.b);
        } catch (RuntimeException e2) {
            c2.close();
            throw e2;
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Cursor<T> cursor) {
        if (this.c.get() == null) {
            Transaction a2 = cursor.a();
            if (!a2.n()) {
                cursor.close();
                a2.a();
                a2.close();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Cursor<T> cursor) {
        if (this.c.get() == null) {
            cursor.close();
            cursor.a().j();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Cursor<T> cursor) {
        if (this.c.get() == null) {
            Transaction a2 = cursor.a();
            if (a2.n() || a2.p() || !a2.o()) {
                throw new IllegalStateException("Illegal reader TX state");
            }
            a2.q();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Transaction transaction) {
        Cursor cursor = this.c.get();
        if (cursor != null) {
            this.c.remove();
            cursor.close();
        }
    }

    public long a(T t) {
        return this.e.a(t);
    }

    public T a(long j2) {
        Cursor b2 = b();
        try {
            return b2.i(j2);
        } finally {
            b(b2);
        }
    }

    public long b(T t) {
        Cursor c2 = c();
        try {
            long a2 = c2.a(t);
            a(c2);
            return a2;
        } finally {
            c(c2);
        }
    }

    public List<T> a(int i2, Property property, long j2) {
        Cursor b2 = b();
        try {
            return b2.a(i2, property, j2);
        } finally {
            b(b2);
        }
    }

    public List<T> a(int i2, int i3, long j2, boolean z) {
        Cursor b2 = b();
        try {
            return b2.a(i2, i3, j2, z);
        } finally {
            b(b2);
        }
    }
}
