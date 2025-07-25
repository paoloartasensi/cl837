package g.a;

/* compiled from: SparseArrayCompat */
public class h<E> implements Cloneable {

    /* renamed from: i  reason: collision with root package name */
    private static final Object f1661i = new Object();
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private int[] f1662f;

    /* renamed from: g  reason: collision with root package name */
    private Object[] f1663g;

    /* renamed from: h  reason: collision with root package name */
    private int f1664h;

    public h() {
        this(10);
    }

    public E a(int i2) {
        return b(i2, (Object) null);
    }

    public E b(int i2, E e2) {
        int a = c.a(this.f1662f, this.f1664h, i2);
        if (a >= 0) {
            E[] eArr = this.f1663g;
            if (eArr[a] != f1661i) {
                return eArr[a];
            }
        }
        return e2;
    }

    public void c(int i2, E e2) {
        int a = c.a(this.f1662f, this.f1664h, i2);
        if (a >= 0) {
            this.f1663g[a] = e2;
            return;
        }
        int i3 = a ^ -1;
        if (i3 < this.f1664h) {
            Object[] objArr = this.f1663g;
            if (objArr[i3] == f1661i) {
                this.f1662f[i3] = i2;
                objArr[i3] = e2;
                return;
            }
        }
        if (this.e && this.f1664h >= this.f1662f.length) {
            e();
            i3 = c.a(this.f1662f, this.f1664h, i2) ^ -1;
        }
        int i4 = this.f1664h;
        if (i4 >= this.f1662f.length) {
            int b = c.b(i4 + 1);
            int[] iArr = new int[b];
            Object[] objArr2 = new Object[b];
            int[] iArr2 = this.f1662f;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.f1663g;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.f1662f = iArr;
            this.f1663g = objArr2;
        }
        int i5 = this.f1664h;
        if (i5 - i3 != 0) {
            int[] iArr3 = this.f1662f;
            int i6 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i6, i5 - i3);
            Object[] objArr4 = this.f1663g;
            System.arraycopy(objArr4, i3, objArr4, i6, this.f1664h - i3);
        }
        this.f1662f[i3] = i2;
        this.f1663g[i3] = e2;
        this.f1664h++;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r0 = r3.f1663g;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d(int r4) {
        /*
            r3 = this;
            int[] r0 = r3.f1662f
            int r1 = r3.f1664h
            int r4 = g.a.c.a((int[]) r0, (int) r1, (int) r4)
            if (r4 < 0) goto L_0x0017
            java.lang.Object[] r0 = r3.f1663g
            r1 = r0[r4]
            java.lang.Object r2 = f1661i
            if (r1 == r2) goto L_0x0017
            r0[r4] = r2
            r4 = 1
            r3.e = r4
        L_0x0017:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: g.a.h.d(int):void");
    }

    public void e(int i2) {
        Object[] objArr = this.f1663g;
        Object obj = objArr[i2];
        Object obj2 = f1661i;
        if (obj != obj2) {
            objArr[i2] = obj2;
            this.e = true;
        }
    }

    public E f(int i2) {
        if (this.e) {
            e();
        }
        return this.f1663g[i2];
    }

    public String toString() {
        if (d() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f1664h * 28);
        sb.append('{');
        for (int i2 = 0; i2 < this.f1664h; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append(c(i2));
            sb.append('=');
            Object f2 = f(i2);
            if (f2 != this) {
                sb.append(f2);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public h(int i2) {
        this.e = false;
        if (i2 == 0) {
            this.f1662f = c.a;
            this.f1663g = c.c;
            return;
        }
        int b = c.b(i2);
        this.f1662f = new int[b];
        this.f1663g = new Object[b];
    }

    public int a(E e2) {
        if (this.e) {
            e();
        }
        for (int i2 = 0; i2 < this.f1664h; i2++) {
            if (this.f1663g[i2] == e2) {
                return i2;
            }
        }
        return -1;
    }

    public h<E> clone() {
        try {
            h<E> hVar = (h) super.clone();
            hVar.f1662f = (int[]) this.f1662f.clone();
            hVar.f1663g = (Object[]) this.f1663g.clone();
            return hVar;
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    private void e() {
        int i2 = this.f1664h;
        int[] iArr = this.f1662f;
        Object[] objArr = this.f1663g;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            if (obj != f1661i) {
                if (i4 != i3) {
                    iArr[i3] = iArr[i4];
                    objArr[i3] = obj;
                    objArr[i4] = null;
                }
                i3++;
            }
        }
        this.e = false;
        this.f1664h = i3;
    }

    public int b(int i2) {
        if (this.e) {
            e();
        }
        return c.a(this.f1662f, this.f1664h, i2);
    }

    public int d() {
        if (this.e) {
            e();
        }
        return this.f1664h;
    }

    public void a(int i2, E e2) {
        int i3 = this.f1664h;
        if (i3 == 0 || i2 > this.f1662f[i3 - 1]) {
            if (this.e && this.f1664h >= this.f1662f.length) {
                e();
            }
            int i4 = this.f1664h;
            if (i4 >= this.f1662f.length) {
                int b = c.b(i4 + 1);
                int[] iArr = new int[b];
                Object[] objArr = new Object[b];
                int[] iArr2 = this.f1662f;
                System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
                Object[] objArr2 = this.f1663g;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.f1662f = iArr;
                this.f1663g = objArr;
            }
            this.f1662f[i4] = i2;
            this.f1663g[i4] = e2;
            this.f1664h = i4 + 1;
            return;
        }
        c(i2, e2);
    }

    public void b() {
        int i2 = this.f1664h;
        Object[] objArr = this.f1663g;
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = null;
        }
        this.f1664h = 0;
        this.e = false;
    }

    public int c(int i2) {
        if (this.e) {
            e();
        }
        return this.f1662f[i2];
    }
}
