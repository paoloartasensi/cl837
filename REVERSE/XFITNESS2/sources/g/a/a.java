package g.a;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* compiled from: ArrayMap */
public class a<K, V> extends g<K, V> implements Map<K, V> {
    f<K, V> l;

    /* renamed from: g.a.a$a  reason: collision with other inner class name */
    /* compiled from: ArrayMap */
    class C0086a extends f<K, V> {
        C0086a() {
        }

        /* access modifiers changed from: protected */
        public Object a(int i2, int i3) {
            return a.this.f1659f[(i2 << 1) + i3];
        }

        /* access modifiers changed from: protected */
        public int b(Object obj) {
            return a.this.b(obj);
        }

        /* access modifiers changed from: protected */
        public int c() {
            return a.this.f1660g;
        }

        /* access modifiers changed from: protected */
        public int a(Object obj) {
            return a.this.a(obj);
        }

        /* access modifiers changed from: protected */
        public Map<K, V> b() {
            return a.this;
        }

        /* access modifiers changed from: protected */
        public void a(K k, V v) {
            a.this.put(k, v);
        }

        /* access modifiers changed from: protected */
        public V a(int i2, V v) {
            return a.this.a(i2, v);
        }

        /* access modifiers changed from: protected */
        public void a(int i2) {
            a.this.c(i2);
        }

        /* access modifiers changed from: protected */
        public void a() {
            a.this.clear();
        }
    }

    public a() {
    }

    private f<K, V> b() {
        if (this.l == null) {
            this.l = new C0086a();
        }
        return this.l;
    }

    public boolean a(Collection<?> collection) {
        return f.c(this, collection);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return b().d();
    }

    public Set<K> keySet() {
        return b().e();
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        a(this.f1660g + map.size());
        for (Map.Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public Collection<V> values() {
        return b().f();
    }

    public a(int i2) {
        super(i2);
    }

    public a(g gVar) {
        super(gVar);
    }
}
