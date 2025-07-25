package androidx.coordinatorlayout.widget;

import androidx.core.g.e;
import androidx.core.g.f;
import g.a.g;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* compiled from: DirectedAcyclicGraph */
public final class a<T> {
    private final e<ArrayList<T>> a = new f(10);
    private final g<T, ArrayList<T>> b = new g<>();
    private final ArrayList<T> c = new ArrayList<>();
    private final HashSet<T> d = new HashSet<>();

    public void a(T t) {
        if (!this.b.containsKey(t)) {
            this.b.put(t, null);
        }
    }

    public boolean b(T t) {
        return this.b.containsKey(t);
    }

    public List c(T t) {
        return this.b.get(t);
    }

    public List<T> d(T t) {
        int size = this.b.size();
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            ArrayList d2 = this.b.d(i2);
            if (d2 != null && d2.contains(t)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(this.b.b(i2));
            }
        }
        return arrayList;
    }

    public boolean e(T t) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayList d2 = this.b.d(i2);
            if (d2 != null && d2.contains(t)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<T> c() {
        ArrayList<T> a2 = this.a.a();
        return a2 == null ? new ArrayList<>() : a2;
    }

    public ArrayList<T> b() {
        this.c.clear();
        this.d.clear();
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(this.b.b(i2), this.c, this.d);
        }
        return this.c;
    }

    public void a(T t, T t2) {
        if (!this.b.containsKey(t) || !this.b.containsKey(t2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList arrayList = this.b.get(t);
        if (arrayList == null) {
            arrayList = c();
            this.b.put(t, arrayList);
        }
        arrayList.add(t2);
    }

    public void a() {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayList d2 = this.b.d(i2);
            if (d2 != null) {
                a(d2);
            }
        }
        this.b.clear();
    }

    private void a(T t, ArrayList<T> arrayList, HashSet<T> hashSet) {
        if (!arrayList.contains(t)) {
            if (!hashSet.contains(t)) {
                hashSet.add(t);
                ArrayList arrayList2 = this.b.get(t);
                if (arrayList2 != null) {
                    int size = arrayList2.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        a(arrayList2.get(i2), arrayList, hashSet);
                    }
                }
                hashSet.remove(t);
                arrayList.add(t);
                return;
            }
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
    }

    private void a(ArrayList<T> arrayList) {
        arrayList.clear();
        this.a.a(arrayList);
    }
}
