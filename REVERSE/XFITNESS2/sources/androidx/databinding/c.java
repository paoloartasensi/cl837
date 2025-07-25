package androidx.databinding;

import java.util.ArrayList;
import java.util.List;

/* compiled from: CallbackRegistry */
public class c<C, T, A> implements Cloneable {
    private List<C> e = new ArrayList();

    /* renamed from: f  reason: collision with root package name */
    private long f563f = 0;

    /* renamed from: g  reason: collision with root package name */
    private long[] f564g;

    /* renamed from: h  reason: collision with root package name */
    private int f565h;

    /* renamed from: i  reason: collision with root package name */
    private final a<C, T, A> f566i;

    /* compiled from: CallbackRegistry */
    public static abstract class a<C, T, A> {
        public abstract void a(C c, T t, int i2, A a);
    }

    public c(a<C, T, A> aVar) {
        this.f566i = aVar;
    }

    private void b(T t, int i2, A a2) {
        a(t, i2, a2, 0, Math.min(64, this.e.size()), this.f563f);
    }

    private void c(T t, int i2, A a2) {
        int size = this.e.size();
        long[] jArr = this.f564g;
        int length = jArr == null ? -1 : jArr.length - 1;
        a(t, i2, a2, length);
        a(t, i2, a2, (length + 2) * 64, size, 0);
    }

    public synchronized void a(T t, int i2, A a2) {
        this.f565h++;
        c(t, i2, a2);
        int i3 = this.f565h - 1;
        this.f565h = i3;
        if (i3 == 0) {
            if (this.f564g != null) {
                for (int length = this.f564g.length - 1; length >= 0; length--) {
                    long j2 = this.f564g[length];
                    if (j2 != 0) {
                        a((length + 1) * 64, j2);
                        this.f564g[length] = 0;
                    }
                }
            }
            if (this.f563f != 0) {
                a(0, this.f563f);
                this.f563f = 0;
            }
        }
    }

    public synchronized c<C, T, A> clone() {
        c<C, T, A> cVar;
        CloneNotSupportedException e2;
        try {
            cVar = (c) super.clone();
            try {
                cVar.f563f = 0;
                cVar.f564g = null;
                cVar.f565h = 0;
                cVar.e = new ArrayList();
                int size = this.e.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (!a(i2)) {
                        cVar.e.add(this.e.get(i2));
                    }
                }
            } catch (CloneNotSupportedException e3) {
                e2 = e3;
                e2.printStackTrace();
                return cVar;
            }
        } catch (CloneNotSupportedException e4) {
            CloneNotSupportedException cloneNotSupportedException = e4;
            cVar = null;
            e2 = cloneNotSupportedException;
            e2.printStackTrace();
            return cVar;
        }
        return cVar;
    }

    public synchronized void b(C c) {
        if (this.f565h == 0) {
            this.e.remove(c);
        } else {
            int lastIndexOf = this.e.lastIndexOf(c);
            if (lastIndexOf >= 0) {
                b(lastIndexOf);
            }
        }
    }

    private void b(int i2) {
        if (i2 < 64) {
            this.f563f = (1 << i2) | this.f563f;
            return;
        }
        int i3 = (i2 / 64) - 1;
        long[] jArr = this.f564g;
        if (jArr == null) {
            this.f564g = new long[(this.e.size() / 64)];
        } else if (jArr.length <= i3) {
            long[] jArr2 = new long[(this.e.size() / 64)];
            long[] jArr3 = this.f564g;
            System.arraycopy(jArr3, 0, jArr2, 0, jArr3.length);
            this.f564g = jArr2;
        }
        long j2 = 1 << (i2 % 64);
        long[] jArr4 = this.f564g;
        jArr4[i3] = j2 | jArr4[i3];
    }

    private void a(T t, int i2, A a2, int i3) {
        if (i3 < 0) {
            b(t, i2, a2);
            return;
        }
        long j2 = this.f564g[i3];
        int i4 = (i3 + 1) * 64;
        int min = Math.min(this.e.size(), i4 + 64);
        a(t, i2, a2, i3 - 1);
        a(t, i2, a2, i4, min, j2);
    }

    private void a(T t, int i2, A a2, int i3, int i4, long j2) {
        long j3 = 1;
        while (i3 < i4) {
            if ((j2 & j3) == 0) {
                this.f566i.a(this.e.get(i3), t, i2, a2);
            }
            j3 <<= 1;
            i3++;
        }
    }

    public synchronized void a(C c) {
        if (c != null) {
            int lastIndexOf = this.e.lastIndexOf(c);
            if (lastIndexOf < 0 || a(lastIndexOf)) {
                this.e.add(c);
            }
        } else {
            throw new IllegalArgumentException("callback cannot be null");
        }
    }

    private boolean a(int i2) {
        int i3;
        if (i2 < 64) {
            return ((1 << i2) & this.f563f) != 0;
        }
        long[] jArr = this.f564g;
        if (jArr == null || (i3 = (i2 / 64) - 1) >= jArr.length) {
            return false;
        }
        if (((1 << (i2 % 64)) & jArr[i3]) != 0) {
            return true;
        }
        return false;
    }

    private void a(int i2, long j2) {
        long j3 = Long.MIN_VALUE;
        for (int i3 = (i2 + 64) - 1; i3 >= i2; i3--) {
            if ((j2 & j3) != 0) {
                this.e.remove(i3);
            }
            j3 >>>= 1;
        }
    }
}
