package com.google.gson.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Comparator<Comparable> e = new a();
    Comparator<? super K> comparator;
    private LinkedTreeMap<K, V>.defpackage.b entrySet;
    final e<K, V> header;
    private LinkedTreeMap<K, V>.defpackage.c keySet;
    int modCount;
    e<K, V> root;
    int size;

    class a implements Comparator<Comparable> {
        a() {
        }

        /* renamed from: a */
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    }

    class b extends AbstractSet<Map.Entry<K, V>> {

        class a extends LinkedTreeMap<K, V>.defpackage.d<Map.Entry<K, V>> {
            a(b bVar) {
                super();
            }

            public Map.Entry<K, V> next() {
                return a();
            }
        }

        b() {
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }

        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && LinkedTreeMap.this.findByEntry((Map.Entry) obj) != null;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new a(this);
        }

        public boolean remove(Object obj) {
            e findByEntry;
            if (!(obj instanceof Map.Entry) || (findByEntry = LinkedTreeMap.this.findByEntry((Map.Entry) obj)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(findByEntry, true);
            return true;
        }

        public int size() {
            return LinkedTreeMap.this.size;
        }
    }

    final class c extends AbstractSet<K> {

        class a extends LinkedTreeMap<K, V>.defpackage.d<K> {
            a(c cVar) {
                super();
            }

            public K next() {
                return a().f1590j;
            }
        }

        c() {
        }

        public void clear() {
            LinkedTreeMap.this.clear();
        }

        public boolean contains(Object obj) {
            return LinkedTreeMap.this.containsKey(obj);
        }

        public Iterator<K> iterator() {
            return new a(this);
        }

        public boolean remove(Object obj) {
            return LinkedTreeMap.this.removeInternalByKey(obj) != null;
        }

        public int size() {
            return LinkedTreeMap.this.size;
        }
    }

    private abstract class d<T> implements Iterator<T> {
        e<K, V> e;

        /* renamed from: f  reason: collision with root package name */
        e<K, V> f1583f = null;

        /* renamed from: g  reason: collision with root package name */
        int f1584g;

        d() {
            LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
            this.e = linkedTreeMap.header.f1588h;
            this.f1584g = linkedTreeMap.modCount;
        }

        /* access modifiers changed from: package-private */
        public final e<K, V> a() {
            e<K, V> eVar = this.e;
            LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
            if (eVar == linkedTreeMap.header) {
                throw new NoSuchElementException();
            } else if (linkedTreeMap.modCount == this.f1584g) {
                this.e = eVar.f1588h;
                this.f1583f = eVar;
                return eVar;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final boolean hasNext() {
            return this.e != LinkedTreeMap.this.header;
        }

        public final void remove() {
            e<K, V> eVar = this.f1583f;
            if (eVar != null) {
                LinkedTreeMap.this.removeInternal(eVar, true);
                this.f1583f = null;
                this.f1584g = LinkedTreeMap.this.modCount;
                return;
            }
            throw new IllegalStateException();
        }
    }

    static {
        Class<LinkedTreeMap> cls = LinkedTreeMap.class;
    }

    public LinkedTreeMap() {
        this(e);
    }

    private boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void b(e<K, V> eVar) {
        e<K, V> eVar2 = eVar.f1586f;
        e<K, V> eVar3 = eVar.f1587g;
        e<K, V> eVar4 = eVar2.f1586f;
        e<K, V> eVar5 = eVar2.f1587g;
        eVar.f1586f = eVar5;
        if (eVar5 != null) {
            eVar5.e = eVar;
        }
        a(eVar, eVar2);
        eVar2.f1587g = eVar;
        eVar.e = eVar2;
        int i2 = 0;
        int max = Math.max(eVar3 != null ? eVar3.l : 0, eVar5 != null ? eVar5.l : 0) + 1;
        eVar.l = max;
        if (eVar4 != null) {
            i2 = eVar4.l;
        }
        eVar2.l = Math.max(max, i2) + 1;
    }

    private Object writeReplace() {
        return new LinkedHashMap(this);
    }

    public void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        e<K, V> eVar = this.header;
        eVar.f1589i = eVar;
        eVar.f1588h = eVar;
    }

    public boolean containsKey(Object obj) {
        return findByObject(obj) != null;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        LinkedTreeMap<K, V>.defpackage.b bVar = this.entrySet;
        if (bVar != null) {
            return bVar;
        }
        LinkedTreeMap<K, V>.defpackage.b bVar2 = new b();
        this.entrySet = bVar2;
        return bVar2;
    }

    /* access modifiers changed from: package-private */
    public e<K, V> find(K k, boolean z) {
        int i2;
        e<K, V> eVar;
        Comparator<? super K> comparator2 = this.comparator;
        e<K, V> eVar2 = this.root;
        if (eVar2 != null) {
            Comparable comparable = comparator2 == e ? (Comparable) k : null;
            while (true) {
                if (comparable != null) {
                    i2 = comparable.compareTo(eVar2.f1590j);
                } else {
                    i2 = comparator2.compare(k, eVar2.f1590j);
                }
                if (i2 == 0) {
                    return eVar2;
                }
                e<K, V> eVar3 = i2 < 0 ? eVar2.f1586f : eVar2.f1587g;
                if (eVar3 == null) {
                    break;
                }
                eVar2 = eVar3;
            }
        } else {
            i2 = 0;
        }
        if (!z) {
            return null;
        }
        e<K, V> eVar4 = this.header;
        if (eVar2 != null) {
            eVar = new e<>(eVar2, k, eVar4, eVar4.f1589i);
            if (i2 < 0) {
                eVar2.f1586f = eVar;
            } else {
                eVar2.f1587g = eVar;
            }
            a(eVar2, true);
        } else if (comparator2 != e || (k instanceof Comparable)) {
            eVar = new e<>(eVar2, k, eVar4, eVar4.f1589i);
            this.root = eVar;
        } else {
            throw new ClassCastException(k.getClass().getName() + " is not Comparable");
        }
        this.size++;
        this.modCount++;
        return eVar;
    }

    /* access modifiers changed from: package-private */
    public e<K, V> findByEntry(Map.Entry<?, ?> entry) {
        e<K, V> findByObject = findByObject(entry.getKey());
        if (findByObject != null && a((Object) findByObject.k, (Object) entry.getValue())) {
            return findByObject;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public e<K, V> findByObject(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return find(obj, false);
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public V get(Object obj) {
        e findByObject = findByObject(obj);
        if (findByObject != null) {
            return findByObject.k;
        }
        return null;
    }

    public Set<K> keySet() {
        LinkedTreeMap<K, V>.defpackage.c cVar = this.keySet;
        if (cVar != null) {
            return cVar;
        }
        LinkedTreeMap<K, V>.defpackage.c cVar2 = new c();
        this.keySet = cVar2;
        return cVar2;
    }

    public V put(K k, V v) {
        if (k != null) {
            e find = find(k, true);
            V v2 = find.k;
            find.k = v;
            return v2;
        }
        throw new NullPointerException("key == null");
    }

    public V remove(Object obj) {
        e removeInternalByKey = removeInternalByKey(obj);
        if (removeInternalByKey != null) {
            return removeInternalByKey.k;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void removeInternal(e<K, V> eVar, boolean z) {
        int i2;
        if (z) {
            e<K, V> eVar2 = eVar.f1589i;
            eVar2.f1588h = eVar.f1588h;
            eVar.f1588h.f1589i = eVar2;
        }
        e<K, V> eVar3 = eVar.f1586f;
        e<K, V> eVar4 = eVar.f1587g;
        e<K, V> eVar5 = eVar.e;
        int i3 = 0;
        if (eVar3 == null || eVar4 == null) {
            if (eVar3 != null) {
                a(eVar, eVar3);
                eVar.f1586f = null;
            } else if (eVar4 != null) {
                a(eVar, eVar4);
                eVar.f1587g = null;
            } else {
                a(eVar, (e<K, V>) null);
            }
            a(eVar5, false);
            this.size--;
            this.modCount++;
            return;
        }
        e<K, V> b2 = eVar3.l > eVar4.l ? eVar3.b() : eVar4.a();
        removeInternal(b2, false);
        e<K, V> eVar6 = eVar.f1586f;
        if (eVar6 != null) {
            i2 = eVar6.l;
            b2.f1586f = eVar6;
            eVar6.e = b2;
            eVar.f1586f = null;
        } else {
            i2 = 0;
        }
        e<K, V> eVar7 = eVar.f1587g;
        if (eVar7 != null) {
            i3 = eVar7.l;
            b2.f1587g = eVar7;
            eVar7.e = b2;
            eVar.f1587g = null;
        }
        b2.l = Math.max(i2, i3) + 1;
        a(eVar, b2);
    }

    /* access modifiers changed from: package-private */
    public e<K, V> removeInternalByKey(Object obj) {
        e<K, V> findByObject = findByObject(obj);
        if (findByObject != null) {
            removeInternal(findByObject, true);
        }
        return findByObject;
    }

    public int size() {
        return this.size;
    }

    public LinkedTreeMap(Comparator<? super K> comparator2) {
        this.size = 0;
        this.modCount = 0;
        this.header = new e<>();
        this.comparator = comparator2 == null ? e : comparator2;
    }

    private void a(e<K, V> eVar, e<K, V> eVar2) {
        e<K, V> eVar3 = eVar.e;
        eVar.e = null;
        if (eVar2 != null) {
            eVar2.e = eVar3;
        }
        if (eVar3 == null) {
            this.root = eVar2;
        } else if (eVar3.f1586f == eVar) {
            eVar3.f1586f = eVar2;
        } else {
            eVar3.f1587g = eVar2;
        }
    }

    static final class e<K, V> implements Map.Entry<K, V> {
        e<K, V> e;

        /* renamed from: f  reason: collision with root package name */
        e<K, V> f1586f;

        /* renamed from: g  reason: collision with root package name */
        e<K, V> f1587g;

        /* renamed from: h  reason: collision with root package name */
        e<K, V> f1588h;

        /* renamed from: i  reason: collision with root package name */
        e<K, V> f1589i;

        /* renamed from: j  reason: collision with root package name */
        final K f1590j;
        V k;
        int l;

        e() {
            this.f1590j = null;
            this.f1589i = this;
            this.f1588h = this;
        }

        public e<K, V> a() {
            e<K, V> eVar = this;
            for (e<K, V> eVar2 = this.f1586f; eVar2 != null; eVar2 = eVar2.f1586f) {
                eVar = eVar2;
            }
            return eVar;
        }

        public e<K, V> b() {
            e<K, V> eVar = this;
            for (e<K, V> eVar2 = this.f1587g; eVar2 != null; eVar2 = eVar2.f1587g) {
                eVar = eVar2;
            }
            return eVar;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0031 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 == 0) goto L_0x0032
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                K r0 = r3.f1590j
                if (r0 != 0) goto L_0x0012
                java.lang.Object r0 = r4.getKey()
                if (r0 != 0) goto L_0x0032
                goto L_0x001c
            L_0x0012:
                java.lang.Object r2 = r4.getKey()
                boolean r0 = r0.equals(r2)
                if (r0 == 0) goto L_0x0032
            L_0x001c:
                V r0 = r3.k
                if (r0 != 0) goto L_0x0027
                java.lang.Object r4 = r4.getValue()
                if (r4 != 0) goto L_0x0032
                goto L_0x0031
            L_0x0027:
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r0.equals(r4)
                if (r4 == 0) goto L_0x0032
            L_0x0031:
                r1 = 1
            L_0x0032:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.LinkedTreeMap.e.equals(java.lang.Object):boolean");
        }

        public K getKey() {
            return this.f1590j;
        }

        public V getValue() {
            return this.k;
        }

        public int hashCode() {
            K k2 = this.f1590j;
            int i2 = 0;
            int hashCode = k2 == null ? 0 : k2.hashCode();
            V v = this.k;
            if (v != null) {
                i2 = v.hashCode();
            }
            return hashCode ^ i2;
        }

        public V setValue(V v) {
            V v2 = this.k;
            this.k = v;
            return v2;
        }

        public String toString() {
            return this.f1590j + "=" + this.k;
        }

        e(e<K, V> eVar, K k2, e<K, V> eVar2, e<K, V> eVar3) {
            this.e = eVar;
            this.f1590j = k2;
            this.l = 1;
            this.f1588h = eVar2;
            this.f1589i = eVar3;
            eVar3.f1588h = this;
            eVar2.f1589i = this;
        }
    }

    private void a(e<K, V> eVar, boolean z) {
        while (eVar != null) {
            e<K, V> eVar2 = eVar.f1586f;
            e<K, V> eVar3 = eVar.f1587g;
            int i2 = 0;
            int i3 = eVar2 != null ? eVar2.l : 0;
            int i4 = eVar3 != null ? eVar3.l : 0;
            int i5 = i3 - i4;
            if (i5 == -2) {
                e<K, V> eVar4 = eVar3.f1586f;
                e<K, V> eVar5 = eVar3.f1587g;
                int i6 = eVar5 != null ? eVar5.l : 0;
                if (eVar4 != null) {
                    i2 = eVar4.l;
                }
                int i7 = i2 - i6;
                if (i7 == -1 || (i7 == 0 && !z)) {
                    a(eVar);
                } else {
                    b(eVar3);
                    a(eVar);
                }
                if (z) {
                    return;
                }
            } else if (i5 == 2) {
                e<K, V> eVar6 = eVar2.f1586f;
                e<K, V> eVar7 = eVar2.f1587g;
                int i8 = eVar7 != null ? eVar7.l : 0;
                if (eVar6 != null) {
                    i2 = eVar6.l;
                }
                int i9 = i2 - i8;
                if (i9 == 1 || (i9 == 0 && !z)) {
                    b(eVar);
                } else {
                    a(eVar2);
                    b(eVar);
                }
                if (z) {
                    return;
                }
            } else if (i5 == 0) {
                eVar.l = i3 + 1;
                if (z) {
                    return;
                }
            } else {
                eVar.l = Math.max(i3, i4) + 1;
                if (!z) {
                    return;
                }
            }
            eVar = eVar.e;
        }
    }

    private void a(e<K, V> eVar) {
        e<K, V> eVar2 = eVar.f1586f;
        e<K, V> eVar3 = eVar.f1587g;
        e<K, V> eVar4 = eVar3.f1586f;
        e<K, V> eVar5 = eVar3.f1587g;
        eVar.f1587g = eVar4;
        if (eVar4 != null) {
            eVar4.e = eVar;
        }
        a(eVar, eVar3);
        eVar3.f1586f = eVar;
        eVar.e = eVar3;
        int i2 = 0;
        int max = Math.max(eVar2 != null ? eVar2.l : 0, eVar4 != null ? eVar4.l : 0) + 1;
        eVar.l = max;
        if (eVar5 != null) {
            i2 = eVar5.l;
        }
        eVar3.l = Math.max(max, i2) + 1;
    }
}
