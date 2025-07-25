package g.a;

/* compiled from: LongSparseArray */
public class d<E> implements Cloneable {

    /* renamed from: i  reason: collision with root package name */
    private static final Object f1642i = new Object();
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private long[] f1643f;

    /* renamed from: g  reason: collision with root package name */
    private Object[] f1644g;

    /* renamed from: h  reason: collision with root package name */
    private int f1645h;

    public d() {
        this(10);
    }

    private void f() {
        int i2 = this.f1645h;
        long[] jArr = this.f1643f;
        Object[] objArr = this.f1644g;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            if (obj != f1642i) {
                if (i4 != i3) {
                    jArr[i3] = jArr[i4];
                    objArr[i3] = obj;
                    objArr[i4] = null;
                }
                i3++;
            }
        }
        this.e = false;
        this.f1645h = i3;
    }

    public long a(int i2) {
        if (this.e) {
            f();
        }
        return this.f1643f[i2];
    }

    public E b(long j2, E e2) {
        int a = c.a(this.f1643f, this.f1645h, j2);
        if (a >= 0) {
            E[] eArr = this.f1644g;
            if (eArr[a] != f1642i) {
                return eArr[a];
            }
        }
        return e2;
    }

    public E c(long j2) {
        return b(j2, (Object) null);
    }

    public boolean d() {
        return e() == 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r4 = r2.f1644g;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(long r3) {
        /*
            r2 = this;
            long[] r0 = r2.f1643f
            int r1 = r2.f1645h
            int r3 = g.a.c.a((long[]) r0, (int) r1, (long) r3)
            if (r3 < 0) goto L_0x0017
            java.lang.Object[] r4 = r2.f1644g
            r0 = r4[r3]
            java.lang.Object r1 = f1642i
            if (r0 == r1) goto L_0x0017
            r4[r3] = r1
            r3 = 1
            r2.e = r3
        L_0x0017:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: g.a.d.e(long):void");
    }

    public String toString() {
        if (e() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f1645h * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f1645h; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(a(i2));
            sb.append('=');
            Object c = c(i2);
            if (c != this) {
                sb.append(c);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public d(int i2) {
        this.e = false;
        if (i2 == 0) {
            this.f1643f = c.b;
            this.f1644g = c.c;
            return;
        }
        int c = c.c(i2);
        this.f1643f = new long[c];
        this.f1644g = new Object[c];
    }

    public void c(long j2, E e2) {
        int a = c.a(this.f1643f, this.f1645h, j2);
        if (a >= 0) {
            this.f1644g[a] = e2;
            return;
        }
        int i2 = a ^ -1;
        if (i2 < this.f1645h) {
            Object[] objArr = this.f1644g;
            if (objArr[i2] == f1642i) {
                this.f1643f[i2] = j2;
                objArr[i2] = e2;
                return;
            }
        }
        if (this.e && this.f1645h >= this.f1643f.length) {
            f();
            i2 = c.a(this.f1643f, this.f1645h, j2) ^ -1;
        }
        int i3 = this.f1645h;
        if (i3 >= this.f1643f.length) {
            int c = c.c(i3 + 1);
            long[] jArr = new long[c];
            Object[] objArr2 = new Object[c];
            long[] jArr2 = this.f1643f;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.f1644g;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f1643f = jArr;
            this.f1644g = objArr2;
        }
        int i4 = this.f1645h;
        if (i4 - i2 != 0) {
            long[] jArr3 = this.f1643f;
            int i5 = i2 + 1;
            System.arraycopy(jArr3, i2, jArr3, i5, i4 - i2);
            Object[] objArr4 = this.f1644g;
            System.arraycopy(objArr4, i2, objArr4, i5, this.f1645h - i2);
        }
        this.f1643f[i2] = j2;
        this.f1644g[i2] = e2;
        this.f1645h++;
    }

    public d<E> clone() {
        try {
            d<E> dVar = (d) super.clone();
            dVar.f1643f = (long[]) this.f1643f.clone();
            dVar.f1644g = (Object[]) this.f1644g.clone();
            return dVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public int d(long j2) {
        if (this.e) {
            f();
        }
        return c.a(this.f1643f, this.f1645h, j2);
    }

    public boolean a(long j2) {
        return d(j2) >= 0;
    }

    @Deprecated
    public void b(long j2) {
        e(j2);
    }

    public void a(long j2, E e2) {
        int i2 = this.f1645h;
        if (i2 == 0 || j2 > this.f1643f[i2 - 1]) {
            if (this.e && this.f1645h >= this.f1643f.length) {
                f();
            }
            int i3 = this.f1645h;
            if (i3 >= this.f1643f.length) {
                int c = c.c(i3 + 1);
                long[] jArr = new long[c];
                Object[] objArr = new Object[c];
                long[] jArr2 = this.f1643f;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                Object[] objArr2 = this.f1644g;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.f1643f = jArr;
                this.f1644g = objArr;
            }
            this.f1643f[i3] = j2;
            this.f1644g[i3] = e2;
            this.f1645h = i3 + 1;
            return;
        }
        c(j2, e2);
    }

    public void b(int i2) {
        Object[] objArr = this.f1644g;
        Object obj = objArr[i2];
        Object obj2 = f1642i;
        if (obj != obj2) {
            objArr[i2] = obj2;
            this.e = true;
        }
    }

    public int e() {
        if (this.e) {
            f();
        }
        return this.f1645h;
    }

    public void b() {
        int i2 = this.f1645h;
        Object[] objArr = this.f1644g;
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = null;
        }
        this.f1645h = 0;
        this.e = false;
    }

    public E c(int i2) {
        if (this.e) {
            f();
        }
        return this.f1644g[i2];
    }
}
