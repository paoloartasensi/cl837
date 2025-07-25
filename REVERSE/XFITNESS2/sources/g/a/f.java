package g.a;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: MapCollections */
abstract class f<K, V> {
    f<K, V>.defpackage.b a;
    f<K, V>.defpackage.c b;
    f<K, V>.defpackage.e c;

    /* compiled from: MapCollections */
    final class a<T> implements Iterator<T> {
        final int e;

        /* renamed from: f  reason: collision with root package name */
        int f1649f;

        /* renamed from: g  reason: collision with root package name */
        int f1650g;

        /* renamed from: h  reason: collision with root package name */
        boolean f1651h = false;

        a(int i2) {
            this.e = i2;
            this.f1649f = f.this.c();
        }

        public boolean hasNext() {
            return this.f1650g < this.f1649f;
        }

        public T next() {
            if (hasNext()) {
                T a = f.this.a(this.f1650g, this.e);
                this.f1650g++;
                this.f1651h = true;
                return a;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            if (this.f1651h) {
                int i2 = this.f1650g - 1;
                this.f1650g = i2;
                this.f1649f--;
                this.f1651h = false;
                f.this.a(i2);
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* compiled from: MapCollections */
    final class b implements Set<Map.Entry<K, V>> {
        b() {
        }

        public boolean a(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public /* bridge */ /* synthetic */ boolean add(Object obj) {
            a((Map.Entry) obj);
            throw null;
        }

        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int c = f.this.c();
            for (Map.Entry entry : collection) {
                f.this.a(entry.getKey(), entry.getValue());
            }
            return c != f.this.c();
        }

        public void clear() {
            f.this.a();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int a = f.this.a(entry.getKey());
            if (a < 0) {
                return false;
            }
            return c.a(f.this.a(a, 1), entry.getValue());
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
            return f.a(this, obj);
        }

        public int hashCode() {
            int i2;
            int i3;
            int i4 = 0;
            for (int c = f.this.c() - 1; c >= 0; c--) {
                Object a = f.this.a(c, 0);
                Object a2 = f.this.a(c, 1);
                if (a == null) {
                    i2 = 0;
                } else {
                    i2 = a.hashCode();
                }
                if (a2 == null) {
                    i3 = 0;
                } else {
                    i3 = a2.hashCode();
                }
                i4 += i2 ^ i3;
            }
            return i4;
        }

        public boolean isEmpty() {
            return f.this.c() == 0;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new d();
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return f.this.c();
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: MapCollections */
    final class c implements Set<K> {
        c() {
        }

        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            f.this.a();
        }

        public boolean contains(Object obj) {
            return f.this.a(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            return f.a(f.this.b(), collection);
        }

        public boolean equals(Object obj) {
            return f.a(this, obj);
        }

        public int hashCode() {
            int i2;
            int i3 = 0;
            for (int c = f.this.c() - 1; c >= 0; c--) {
                Object a = f.this.a(c, 0);
                if (a == null) {
                    i2 = 0;
                } else {
                    i2 = a.hashCode();
                }
                i3 += i2;
            }
            return i3;
        }

        public boolean isEmpty() {
            return f.this.c() == 0;
        }

        public Iterator<K> iterator() {
            return new a(0);
        }

        public boolean remove(Object obj) {
            int a = f.this.a(obj);
            if (a < 0) {
                return false;
            }
            f.this.a(a);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return f.b(f.this.b(), collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return f.c(f.this.b(), collection);
        }

        public int size() {
            return f.this.c();
        }

        public Object[] toArray() {
            return f.this.b(0);
        }

        public <T> T[] toArray(T[] tArr) {
            return f.this.a(tArr, 0);
        }
    }

    /* compiled from: MapCollections */
    final class d implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f1653f;

        /* renamed from: g  reason: collision with root package name */
        boolean f1654g = false;

        d() {
            this.e = f.this.c() - 1;
            this.f1653f = -1;
        }

        public boolean equals(Object obj) {
            if (!this.f1654g) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!(obj instanceof Map.Entry)) {
                return false;
            } else {
                Map.Entry entry = (Map.Entry) obj;
                if (!c.a(entry.getKey(), f.this.a(this.f1653f, 0)) || !c.a(entry.getValue(), f.this.a(this.f1653f, 1))) {
                    return false;
                }
                return true;
            }
        }

        public K getKey() {
            if (this.f1654g) {
                return f.this.a(this.f1653f, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() {
            if (this.f1654g) {
                return f.this.a(this.f1653f, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public boolean hasNext() {
            return this.f1653f < this.e;
        }

        public int hashCode() {
            int i2;
            if (this.f1654g) {
                int i3 = 0;
                Object a = f.this.a(this.f1653f, 0);
                Object a2 = f.this.a(this.f1653f, 1);
                if (a == null) {
                    i2 = 0;
                } else {
                    i2 = a.hashCode();
                }
                if (a2 != null) {
                    i3 = a2.hashCode();
                }
                return i2 ^ i3;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public void remove() {
            if (this.f1654g) {
                f.this.a(this.f1653f);
                this.f1653f--;
                this.e--;
                this.f1654g = false;
                return;
            }
            throw new IllegalStateException();
        }

        public V setValue(V v) {
            if (this.f1654g) {
                return f.this.a(this.f1653f, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }

        public Map.Entry<K, V> next() {
            if (hasNext()) {
                this.f1653f++;
                this.f1654g = true;
                return this;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapCollections */
    final class e implements Collection<V> {
        e() {
        }

        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            f.this.a();
        }

        public boolean contains(Object obj) {
            return f.this.b(obj) >= 0;
        }

        public boolean containsAll(Collection<?> collection) {
            for (Object contains : collection) {
                if (!contains(contains)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() {
            return f.this.c() == 0;
        }

        public Iterator<V> iterator() {
            return new a(1);
        }

        public boolean remove(Object obj) {
            int b = f.this.b(obj);
            if (b < 0) {
                return false;
            }
            f.this.a(b);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            int c = f.this.c();
            int i2 = 0;
            boolean z = false;
            while (i2 < c) {
                if (collection.contains(f.this.a(i2, 1))) {
                    f.this.a(i2);
                    i2--;
                    c--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            int c = f.this.c();
            int i2 = 0;
            boolean z = false;
            while (i2 < c) {
                if (!collection.contains(f.this.a(i2, 1))) {
                    f.this.a(i2);
                    i2--;
                    c--;
                    z = true;
                }
                i2++;
            }
            return z;
        }

        public int size() {
            return f.this.c();
        }

        public Object[] toArray() {
            return f.this.b(1);
        }

        public <T> T[] toArray(T[] tArr) {
            return f.this.a(tArr, 1);
        }
    }

    f() {
    }

    public static <K, V> boolean a(Map<K, V> map, Collection<?> collection) {
        for (Object containsKey : collection) {
            if (!map.containsKey(containsKey)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean b(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        for (Object remove : collection) {
            map.remove(remove);
        }
        return size != map.size();
    }

    public static <K, V> boolean c(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    /* access modifiers changed from: protected */
    public abstract int a(Object obj);

    /* access modifiers changed from: protected */
    public abstract Object a(int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract V a(int i2, V v);

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void a(int i2);

    /* access modifiers changed from: protected */
    public abstract void a(K k, V v);

    /* access modifiers changed from: protected */
    public abstract int b(Object obj);

    /* access modifiers changed from: protected */
    public abstract Map<K, V> b();

    /* access modifiers changed from: protected */
    public abstract int c();

    public Set<Map.Entry<K, V>> d() {
        if (this.a == null) {
            this.a = new b();
        }
        return this.a;
    }

    public Set<K> e() {
        if (this.b == null) {
            this.b = new c();
        }
        return this.b;
    }

    public Collection<V> f() {
        if (this.c == null) {
            this.c = new e();
        }
        return this.c;
    }

    public <T> T[] a(T[] tArr, int i2) {
        int c2 = c();
        if (tArr.length < c2) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), c2);
        }
        for (int i3 = 0; i3 < c2; i3++) {
            tArr[i3] = a(i3, i2);
        }
        if (tArr.length > c2) {
            tArr[c2] = null;
        }
        return tArr;
    }

    public Object[] b(int i2) {
        int c2 = c();
        Object[] objArr = new Object[c2];
        for (int i3 = 0; i3 < c2; i3++) {
            objArr[i3] = a(i3, i2);
        }
        return objArr;
    }

    public static <T> boolean a(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() != set2.size() || !set.containsAll(set2)) {
                    return false;
                }
                return true;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }
}
