package g.a;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: ArraySet */
public final class b<E> implements Collection<E>, Set<E> {

    /* renamed from: i  reason: collision with root package name */
    private static final int[] f1637i = new int[0];

    /* renamed from: j  reason: collision with root package name */
    private static final Object[] f1638j = new Object[0];
    private static Object[] k;
    private static int l;
    private static Object[] m;
    private static int n;
    private int[] e;

    /* renamed from: f  reason: collision with root package name */
    Object[] f1639f;

    /* renamed from: g  reason: collision with root package name */
    int f1640g;

    /* renamed from: h  reason: collision with root package name */
    private f<E, E> f1641h;

    /* compiled from: ArraySet */
    class a extends f<E, E> {
        a() {
        }

        /* access modifiers changed from: protected */
        public Object a(int i2, int i3) {
            return b.this.f1639f[i2];
        }

        /* access modifiers changed from: protected */
        public int b(Object obj) {
            return b.this.indexOf(obj);
        }

        /* access modifiers changed from: protected */
        public int c() {
            return b.this.f1640g;
        }

        /* access modifiers changed from: protected */
        public int a(Object obj) {
            return b.this.indexOf(obj);
        }

        /* access modifiers changed from: protected */
        public Map<E, E> b() {
            throw new UnsupportedOperationException("not a map");
        }

        /* access modifiers changed from: protected */
        public void a(E e, E e2) {
            b.this.add(e);
        }

        /* access modifiers changed from: protected */
        public E a(int i2, E e) {
            throw new UnsupportedOperationException("not a map");
        }

        /* access modifiers changed from: protected */
        public void a(int i2) {
            b.this.b(i2);
        }

        /* access modifiers changed from: protected */
        public void a() {
            b.this.clear();
        }
    }

    public b() {
        this(0);
    }

    private int a(Object obj, int i2) {
        int i3 = this.f1640g;
        if (i3 == 0) {
            return -1;
        }
        int a2 = c.a(this.e, i3, i2);
        if (a2 < 0 || obj.equals(this.f1639f[a2])) {
            return a2;
        }
        int i4 = a2 + 1;
        while (i4 < i3 && this.e[i4] == i2) {
            if (obj.equals(this.f1639f[i4])) {
                return i4;
            }
            i4++;
        }
        int i5 = a2 - 1;
        while (i5 >= 0 && this.e[i5] == i2) {
            if (obj.equals(this.f1639f[i5])) {
                return i5;
            }
            i5--;
        }
        return i4 ^ -1;
    }

    private int b() {
        int i2 = this.f1640g;
        if (i2 == 0) {
            return -1;
        }
        int a2 = c.a(this.e, i2, 0);
        if (a2 < 0 || this.f1639f[a2] == null) {
            return a2;
        }
        int i3 = a2 + 1;
        while (i3 < i2 && this.e[i3] == 0) {
            if (this.f1639f[i3] == null) {
                return i3;
            }
            i3++;
        }
        int i4 = a2 - 1;
        while (i4 >= 0 && this.e[i4] == 0) {
            if (this.f1639f[i4] == null) {
                return i4;
            }
            i4--;
        }
        return i3 ^ -1;
    }

    private void d(int i2) {
        if (i2 == 8) {
            synchronized (b.class) {
                if (m != null) {
                    Object[] objArr = m;
                    this.f1639f = objArr;
                    m = (Object[]) objArr[0];
                    this.e = (int[]) objArr[1];
                    objArr[1] = null;
                    objArr[0] = null;
                    n--;
                    return;
                }
            }
        } else if (i2 == 4) {
            synchronized (b.class) {
                if (k != null) {
                    Object[] objArr2 = k;
                    this.f1639f = objArr2;
                    k = (Object[]) objArr2[0];
                    this.e = (int[]) objArr2[1];
                    objArr2[1] = null;
                    objArr2[0] = null;
                    l--;
                    return;
                }
            }
        }
        this.e = new int[i2];
        this.f1639f = new Object[i2];
    }

    public boolean add(E e2) {
        int i2;
        int i3;
        if (e2 == null) {
            i3 = b();
            i2 = 0;
        } else {
            int hashCode = e2.hashCode();
            i2 = hashCode;
            i3 = a(e2, hashCode);
        }
        if (i3 >= 0) {
            return false;
        }
        int i4 = i3 ^ -1;
        int i5 = this.f1640g;
        if (i5 >= this.e.length) {
            int i6 = 4;
            if (i5 >= 8) {
                i6 = (i5 >> 1) + i5;
            } else if (i5 >= 4) {
                i6 = 8;
            }
            int[] iArr = this.e;
            Object[] objArr = this.f1639f;
            d(i6);
            int[] iArr2 = this.e;
            if (iArr2.length > 0) {
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                System.arraycopy(objArr, 0, this.f1639f, 0, objArr.length);
            }
            a(iArr, objArr, this.f1640g);
        }
        int i7 = this.f1640g;
        if (i4 < i7) {
            int[] iArr3 = this.e;
            int i8 = i4 + 1;
            System.arraycopy(iArr3, i4, iArr3, i8, i7 - i4);
            Object[] objArr2 = this.f1639f;
            System.arraycopy(objArr2, i4, objArr2, i8, this.f1640g - i4);
        }
        this.e[i4] = i2;
        this.f1639f[i4] = e2;
        this.f1640g++;
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        a(this.f1640g + collection.size());
        boolean z = false;
        for (Object add : collection) {
            z |= add(add);
        }
        return z;
    }

    public E c(int i2) {
        return this.f1639f[i2];
    }

    public void clear() {
        int i2 = this.f1640g;
        if (i2 != 0) {
            a(this.e, this.f1639f, i2);
            this.e = f1637i;
            this.f1639f = f1638j;
            this.f1640g = 0;
        }
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            if (size() != set.size()) {
                return false;
            }
            int i2 = 0;
            while (i2 < this.f1640g) {
                try {
                    if (!set.contains(c(i2))) {
                        return false;
                    }
                    i2++;
                } catch (ClassCastException | NullPointerException unused) {
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int[] iArr = this.e;
        int i2 = this.f1640g;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += iArr[i4];
        }
        return i3;
    }

    public int indexOf(Object obj) {
        return obj == null ? b() : a(obj, obj.hashCode());
    }

    public boolean isEmpty() {
        return this.f1640g <= 0;
    }

    public Iterator<E> iterator() {
        return a().e().iterator();
    }

    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf < 0) {
            return false;
        }
        b(indexOf);
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean z = false;
        for (Object remove : collection) {
            z |= remove(remove);
        }
        return z;
    }

    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (int i2 = this.f1640g - 1; i2 >= 0; i2--) {
            if (!collection.contains(this.f1639f[i2])) {
                b(i2);
                z = true;
            }
        }
        return z;
    }

    public int size() {
        return this.f1640g;
    }

    public Object[] toArray() {
        int i2 = this.f1640g;
        Object[] objArr = new Object[i2];
        System.arraycopy(this.f1639f, 0, objArr, 0, i2);
        return objArr;
    }

    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.f1640g * 14);
        sb.append('{');
        for (int i2 = 0; i2 < this.f1640g; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            Object c = c(i2);
            if (c != this) {
                sb.append(c);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public b(int i2) {
        if (i2 == 0) {
            this.e = f1637i;
            this.f1639f = f1638j;
        } else {
            d(i2);
        }
        this.f1640g = 0;
    }

    public <T> T[] toArray(T[] tArr) {
        if (tArr.length < this.f1640g) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), this.f1640g);
        }
        System.arraycopy(this.f1639f, 0, tArr, 0, this.f1640g);
        int length = tArr.length;
        int i2 = this.f1640g;
        if (length > i2) {
            tArr[i2] = null;
        }
        return tArr;
    }

    private static void a(int[] iArr, Object[] objArr, int i2) {
        if (iArr.length == 8) {
            synchronized (b.class) {
                if (n < 10) {
                    objArr[0] = m;
                    objArr[1] = iArr;
                    for (int i3 = i2 - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    m = objArr;
                    n++;
                }
            }
        } else if (iArr.length == 4) {
            synchronized (b.class) {
                if (l < 10) {
                    objArr[0] = k;
                    objArr[1] = iArr;
                    for (int i4 = i2 - 1; i4 >= 2; i4--) {
                        objArr[i4] = null;
                    }
                    k = objArr;
                    l++;
                }
            }
        }
    }

    public E b(int i2) {
        E[] eArr = this.f1639f;
        E e2 = eArr[i2];
        int i3 = this.f1640g;
        if (i3 <= 1) {
            a(this.e, eArr, i3);
            this.e = f1637i;
            this.f1639f = f1638j;
            this.f1640g = 0;
        } else {
            int[] iArr = this.e;
            int i4 = 8;
            if (iArr.length <= 8 || i3 >= iArr.length / 3) {
                int i5 = this.f1640g - 1;
                this.f1640g = i5;
                if (i2 < i5) {
                    int[] iArr2 = this.e;
                    int i6 = i2 + 1;
                    System.arraycopy(iArr2, i6, iArr2, i2, i5 - i2);
                    Object[] objArr = this.f1639f;
                    System.arraycopy(objArr, i6, objArr, i2, this.f1640g - i2);
                }
                this.f1639f[this.f1640g] = null;
            } else {
                if (i3 > 8) {
                    i4 = i3 + (i3 >> 1);
                }
                int[] iArr3 = this.e;
                Object[] objArr2 = this.f1639f;
                d(i4);
                this.f1640g--;
                if (i2 > 0) {
                    System.arraycopy(iArr3, 0, this.e, 0, i2);
                    System.arraycopy(objArr2, 0, this.f1639f, 0, i2);
                }
                int i7 = this.f1640g;
                if (i2 < i7) {
                    int i8 = i2 + 1;
                    System.arraycopy(iArr3, i8, this.e, i2, i7 - i2);
                    System.arraycopy(objArr2, i8, this.f1639f, i2, this.f1640g - i2);
                }
            }
        }
        return e2;
    }

    public void a(int i2) {
        int[] iArr = this.e;
        if (iArr.length < i2) {
            Object[] objArr = this.f1639f;
            d(i2);
            int i3 = this.f1640g;
            if (i3 > 0) {
                System.arraycopy(iArr, 0, this.e, 0, i3);
                System.arraycopy(objArr, 0, this.f1639f, 0, this.f1640g);
            }
            a(iArr, objArr, this.f1640g);
        }
    }

    private f<E, E> a() {
        if (this.f1641h == null) {
            this.f1641h = new a();
        }
        return this.f1641h;
    }
}
